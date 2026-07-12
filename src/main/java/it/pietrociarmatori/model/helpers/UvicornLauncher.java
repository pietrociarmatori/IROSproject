package it.pietrociarmatori.model.helpers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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

        // controllo se uvicorn è già attivo
        if(isUnicornAlive()){
            return;
        }
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
                                    if(listener!=null) {
                                        listener.notify(new Throwable("Uvicorn terminato tramite shutdownHook()"));
                                    }
                                }
                            }));

                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }

                            int exitCode = process.waitFor();
                            if(listener!=null){
                                listener.notify(new Throwable("Uvicorn crash, exit code: " + exitCode));
                            }


                        } catch (Throwable e) {
                            if (listener != null) {
                                listener.notify(new Throwable("Errore nel launcher API. Causa: " + e.getMessage()));
                            } else {
                                System.err.println("Listener non settato, throwable non gestito, errore nel launcher di Uvicorn");
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
        ProcessBuilder pb = new ProcessBuilder(
                "python", "-m", "uvicorn", "IAendpoint:app", "--host", "127.0.0.1", "--port", "8000", "--log-level", "critical"
        );
        pb.directory(new File(properties.getProperty(param4))); // dove bisogna lanciare il processo
        return pb;
    }

    private boolean isUnicornAlive(){
        try{
            URL url = new URL("http://127.0.0.1:8000/");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setConnectTimeout(500);
            con.setReadTimeout(500);
            con.connect();
            return con.getResponseCode() == 200;
        }catch(Exception e){
            return false;
        }
    }

    public void stop() { // in caso servisse
        running = false;
    }
}