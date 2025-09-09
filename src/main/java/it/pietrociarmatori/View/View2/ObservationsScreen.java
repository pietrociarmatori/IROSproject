package it.pietrociarmatori.View.View2;

import it.pietrociarmatori.ControllerAppl.AggiungiProvvedimentoController;
import it.pietrociarmatori.ControllerAppl.GestisciOsservazioniDipendentiController;
import it.pietrociarmatori.ControllerAppl.GestisciPosizioniAperteController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.OsservazioneBean;
import it.pietrociarmatori.Model.Beans.PosizioneBean;
import it.pietrociarmatori.Model.Beans.ProvvedimentoBean;
import it.pietrociarmatori.Model.Beans.TabellaOsservazioniBean;
import it.pietrociarmatori.Model.Entity.OsservazioniDipartimento;
import it.pietrociarmatori.Model.Entity.PosizioniDipartimento;
import it.pietrociarmatori.View.SessionHR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ObservationsScreen implements CliScreen{
    private SessionHR sessionHR;
    private TabellaOsservazioniBean tabella;
    private List<OsservazioneBean> flatList = new ArrayList<>();
    public ObservationsScreen(SessionHR sessionHR){
        try {
            this.sessionHR = sessionHR;
            GestisciOsservazioniDipendentiController godc = new GestisciOsservazioniDipendentiController();
            tabella = godc.getTabellaOsservazioni(sessionHR.getCred());

            for (OsservazioniDipartimento osservazioniDipartimento : tabella.getTabella().values()) {
                flatList.addAll(osservazioniDipartimento.getListaOsservazioni());
            }
        } catch (TaskException e) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Errore, si prega di ritornare alla home e riprovare. Premere ENTER. Causa: "+e.getMessage());
            scanner.nextLine();
        }
    }

    @Override
    public void render() {
        System.out.println("-------------------------------------------------------                                                                                            Dati profilo:");
        System.out.println("-------------------------------------------------------");
        System.out.println("-------  --------------  --------------  --------------                                                                                            -)Nome: "+sessionHR.getCred().getNome());
        System.out.println("|     |  |            |  |            |  |            |                                                                                            -)Cognome: "+sessionHR.getCred().getCognome());
        System.out.println("|     |  |     ---    |  |      |     |  |            |                                                                                            -)Ruolo: "+sessionHR.getCred().getRuolo());
        System.out.println("|     |  |            |  |      |     |  |       -----|                                                                                            -)Dipartimento: "+sessionHR.getCred().getDipartimento());
        System.out.println("|     |  |       -----|  |      |     |  |            |                                                                                            -)Matricola: "+sessionHR.getCred().getMatricola());
        System.out.println("|     |  |            |  |      |     |  |-----       |                                                                                            -)Password: "+sessionHR.getCred().getPassword());
        System.out.println("|     |  |     |      |  |      |     |  |            |");
        System.out.println("|     |  |     |      |  |            |  |            |");
        System.out.println("-------  --------------  --------------  --------------");
        System.out.println("-------------------------------------------------------");
        System.out.println("-------------------------------------------------------");
        System.out.println();
        System.out.println("OSSERVAZIONI: ");

        int counter = 1;
        for (Map.Entry<String, OsservazioniDipartimento> entry : tabella.getTabella().entrySet()) {
            String deptName = entry.getKey();
            OsservazioniDipartimento osservazioniDipartimento = entry.getValue();

            System.out.println();
            System.out.println("Dipartimento: " + deptName);
            System.out.println("Situazione generale: "+osservazioniDipartimento.getRiassuntoOsservazioni());
            System.out.println("Sentiment generale: "+osservazioniDipartimento.getSentimentGenerale());
            System.out.println("Provvedimenti presi: "+ osservazioniDipartimento.getProvvedimenti());
            System.out.println("--------------------------------");

            for (OsservazioneBean oss : osservazioniDipartimento.getListaOsservazioni()) {
                System.out.printf("%d) Nome: %s | Matricola: %s | Osservazione: %s | Sentiment: %s\n",
                        counter,
                        oss.getNome() + " " + oss.getCognome(),
                        oss.getMatricola(),
                        oss.getOsservazione(),
                        oss.getSentiment()
                );
                counter++;
            }
        }

        System.out.println();
        System.out.println("Selezionare una delle seguenti azioni digitando il numero corrispondente:");
        System.out.println("1) Elimina osservazione");
        System.out.println("2) Aggiungi provvedimento");
        System.out.println("3) Back to home (digita 'b')");
    }

    @Override
    public CliScreen handleInput(Scanner scanner) {
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("b")) {
            return new HomeScreen(sessionHR); // back alla home
        }

        try {
            int choice = Integer.parseInt(input);
            if(choice < 1 || choice > 2){
                System.out.println("Opzione inesistente, premere ENTER per riprovare...");
                scanner.nextLine();
                return this;
            }

            switch(choice){
                case 1:
                    try {
                        // richiedi il numero dell'osservazione, valla a prendere in lista, comunica dell'avvenuta eliminazione e refresh
                        System.out.println("Selezionare la posizione da eliminare, digitando il numero corrispondente:");
                        int posizione = Integer.parseInt(scanner.nextLine());
                        OsservazioneBean bean = flatList.get(posizione - 1);

                        GestisciOsservazioniDipendentiController godc = new GestisciOsservazioniDipendentiController();
                        godc.deleteOsservazione(sessionHR.getCred(), bean);
                        aggiornaTabella();

                        System.out.println("Eliminazione avvenuta con successo, premere ENTER per selezionare un'altra opzione");
                        scanner.nextLine();
                        return this;
                    }catch(TaskException e){
                        System.out.println("Errore durante l'eliminazione della posizione, premere ENTER e riprovare...");
                        scanner.nextLine();
                        return this;
                    }
                case 2:
                    try {
                        // prendi da schermo i dati del nuovo provvedimento, aggiungila, comunica dell'avvenuta aggiunta e refresh
                        ProvvedimentoBean provv = new ProvvedimentoBean();
                        System.out.println("Indicare il dipartimento:");
                        provv.setDipartimento(scanner.nextLine());
                        System.out.println("Scrivere il provvedimento:");
                        provv.setProvvedimento(scanner.nextLine());

                        AggiungiProvvedimentoController apc = new AggiungiProvvedimentoController();
                        apc.addProvvedimento(sessionHR.getCred(), provv);
                        aggiornaTabella();

                        System.out.println("Posizione aggiunta con successo, premere ENTER per selezionare un'altra opzione");
                        scanner.nextLine();
                        return this;
                    } catch (TaskException e) {
                        System.out.println("Errore durante l'aggiunta della posizione, premere ENTER e riprovare...");
                        scanner.nextLine();
                        return this;
                    }
                default:
                    System.out.println("Scelta non possibile, premere ENTER e riprovare");
                    scanner.nextLine();
                    return this;

            }

        } catch (NumberFormatException e) {
            System.out.println("Formato input non valido, premere ENTER per riprovare...");
            scanner.nextLine();
            return this;
        }
    }
    private void aggiornaTabella(){
        try {
            GestisciOsservazioniDipendentiController godc = new GestisciOsservazioniDipendentiController();
            tabella = godc.getTabellaOsservazioni(sessionHR.getCred());

            for (OsservazioniDipartimento osservazioniDipartimento : tabella.getTabella().values()) {
                flatList.addAll(osservazioniDipartimento.getListaOsservazioni());
            }
        } catch (TaskException e) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Errore, si prega di ritornare alla home e riprovare. Premere ENTER. Causa: "+e.getMessage());
            scanner.nextLine();
        }
    }
}
