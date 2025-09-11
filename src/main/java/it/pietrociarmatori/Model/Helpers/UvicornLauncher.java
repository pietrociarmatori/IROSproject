package it.pietrociarmatori.Model.Helpers;

import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.Beans.CredentialsEmployeeBean;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.Entity.Credentials;

import java.io.*;
import java.util.Properties;

// avevo pensato di rendere il launcher un singleton ma,
// a questo punto penso abbia più senso avere un thread come metodo separato per ogni istanza dell'app
public class UvicornLauncher {
    private String resources;
    private String param4;
    private volatile boolean running = true; // thread safe con volatile
    protected UvicornLauncher(){}
    public UvicornLauncher(String resources,
                           String param4){
        this.resources = resources;
        this.param4 = param4;
    }
    public void launch(ListenerThread listener) {
        new Thread(() -> {
                    while (running) {
                        Process process = null;
                        try {
                            ProcessBuilder pb = getProcessBuilder();
                            process = pb.start();

                            Process finalTemp = process;

                            Runtime.getRuntime().addShutdownHook(new Thread(() -> { // Chiudi il thread quando si chiude l'app
                                if (finalTemp != null && finalTemp.isAlive()) {
                                    finalTemp.destroy();
                                    System.out.println("Uvicorn terminato tramite shutdownHook()");
                                }
                            }));

                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }

                            int exitCode = process.waitFor();
                            System.err.println("Uvicorn crash, exit code: " + exitCode);

                        } catch (Throwable e) {
                            if (listener != null) {
                                listener.notify(new Throwable("Errore nel launcher API. Causa: " + e.getMessage()));
                            } else {
                                System.err.println("Listener non settato, throwable non gestito, errore nel launcher di Uvicorn");
                                e.printStackTrace();
                            }
                        }
                        if (running) { // se running attivo ma mi trovo qui allora vuoldire che è crashato
                            try {
                                Thread.sleep(3000); // aspetto tre secondi e rientro nel while, dove verrà rilanciato uvicorn
                            } catch (InterruptedException e) {
                            } // ignoro exception di thread
                        }
                    }
        }).start();
    }
    private ProcessBuilder getProcessBuilder() throws IOException {
        InputStream input = new FileInputStream(resources);
        Properties properties = new Properties();
        properties.load(input);

        // uso un file .bat per evitare di dover installare python nell'ambiente
        // in pratica in questo modo, (creando una console e chiamando python da li)
        // rendo sufficiente avere python sulla macchina dove l'app gira.
        // raffinazione successiva potrebbe essere quella del setup di un venv
                        /*
                        ProcessBuilder pb = new ProcessBuilder(
                            properties.getProperty(param1)
                            , properties.getProperty(param2)
                            , properties.getProperty(param3));
                        pb.directory(new File(properties.getProperty(param4)));
                        pb.redirectErrorStream(true);

                        */
        // mi evito il pippone di cui sopra, tra l'altro, mantenendo collegato il processo con java
        // (perché evito di metterci una console in mezzo), non mi rimane un processo python in background.
        // che non è per niente male considerando che ne ho dovuti chiudere almeno due mila questa settimana
        ProcessBuilder pb = new ProcessBuilder(
                "python", "-m", "uvicorn", "IAendpoint:app", "--host", "127.0.0.1", "--port", "8000", "--log-level", "critical"
        );
        pb.directory(new File(properties.getProperty(param4)));
        return pb;
    }

    public void stop() { // in caso servisse
        running = false;
    }
}