package it.pietrociarmatori.View.View2;

import it.pietrociarmatori.ControllerAppl.GestisciPosizioniAperteController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Beans.PosizioneBean;
import it.pietrociarmatori.Model.Beans.TabellaPosizioniAperteBean;
import it.pietrociarmatori.Model.Entity.CandidatiDipartimento;
import it.pietrociarmatori.Model.Entity.PosizioniDipartimento;
import it.pietrociarmatori.View.SessionHR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PositionsScreen implements CliScreen{
    private SessionHR sessionHR;
    private TabellaPosizioniAperteBean tabella;
    private String opcode = "1";
    private List<PosizioneBean> flatList = new ArrayList<>();
    public PositionsScreen(SessionHR sessionHR){
        try {
            this.sessionHR = sessionHR;
            GestisciPosizioniAperteController gpac = new GestisciPosizioniAperteController();
            tabella = gpac.getTabellaPosizioniAperte(sessionHR.getCred(), opcode);

            for (PosizioniDipartimento posizioniDipartimento : tabella.getTabella().values()) {
                flatList.addAll(posizioniDipartimento.getListaPosizioni());
            }
        }catch (TaskException e){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Errore, si prega di ritornare alla home e riprovare. Premere ENTER. Causa: "+e.getMessage());
            scanner.nextLine();
        }
    }
    @Override
    public void render() {
        String line = "-------------------------------------------------------";
        System.out.println(line+"                                                                                            Dati profilo:");
        System.out.println(line);
        System.out.println("-------  --------------  --------------  --------------                                                                                            -)Nome: "+sessionHR.getCred().getNome());
        System.out.println("|     |  |            |  |            |  |            |                                                                                            -)Cognome: "+sessionHR.getCred().getCognome());
        System.out.println("|     |  |     ---    |  |      |     |  |            |                                                                                            -)Ruolo: "+sessionHR.getCred().getRuolo());
        System.out.println("|     |  |            |  |      |     |  |       -----|                                                                                            -)Dipartimento: "+sessionHR.getCred().getDipartimento());
        System.out.println("|     |  |       -----|  |      |     |  |            |                                                                                            -)Matricola: "+sessionHR.getCred().getMatricola());
        System.out.println("|     |  |            |  |      |     |  |-----       |                                                                                            -)Password: "+sessionHR.getCred().getPassword());
        System.out.println("|     |  |     |      |  |      |     |  |            |");
        System.out.println("|     |  |     |      |  |            |  |            |");
        System.out.println("-------  --------------  --------------  --------------");
        System.out.println(line);
        System.out.println(line);
        System.out.println();
        System.out.println("POSIZIONI: ");

        int counter = 1;
        for (Map.Entry<String, PosizioniDipartimento> entry : tabella.getTabella().entrySet()) {
            String deptName = entry.getKey();
            PosizioniDipartimento posizioniDipartimento = entry.getValue();

            System.out.println();
            System.out.println("Dipartimento: " + deptName);
            System.out.println("--------------------------------");

            for (PosizioneBean pos : posizioniDipartimento.getListaPosizioni()) {
                System.out.printf("%d) Posizione: %s | Requisiti: %s\n",
                        counter,
                        pos.getNomePosizione(),
                        pos.getRequisiti()
                );
                counter++;
            }
        }

        System.out.println();
        System.out.println("Selezionare una delle seguenti azioni digitando il numero corrispondente:");
        System.out.println("1) Elimina posizione");
        System.out.println("2) Aggiungi posizione");
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
                        // richiedi il numero della posizione, valla a prendere in lista, comunica dell'avvenuta eliminazione e refresh
                        System.out.println("Selezionare la posizione da eliminare, digitando il numero corrispondente:");
                        int posizione = Integer.parseInt(scanner.nextLine());
                        PosizioneBean bean = flatList.get(posizione - 1);

                        GestisciPosizioniAperteController gpac = new GestisciPosizioniAperteController();
                        gpac.deletePosizioneAperta(sessionHR.getCred(), bean, opcode);
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
                        // prendi da schermo i dati della nuova posizione, aggiungila, comunica dell'avvenuta aggiunta e refresh
                        PosizioneBean nuovaPos = new PosizioneBean();
                        System.out.println("Dipartimento nuova posizione:");
                        nuovaPos.setDipartimento(scanner.nextLine());
                        System.out.println("Nome posizione:");
                        nuovaPos.setNomePosizione(scanner.nextLine());
                        System.out.println("Requisiti nuova posizione:");
                        nuovaPos.setRequisiti(scanner.nextLine());

                        GestisciPosizioniAperteController gpac = new GestisciPosizioniAperteController();
                        gpac.addPosizioneAperta(sessionHR.getCred(), nuovaPos, opcode);
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
            GestisciPosizioniAperteController gpac = new GestisciPosizioniAperteController();
            tabella = gpac.getTabellaPosizioniAperte(sessionHR.getCred(), opcode);

            for (PosizioniDipartimento posizioniDipartimento : tabella.getTabella().values()) {
                flatList.addAll(posizioniDipartimento.getListaPosizioni());
            }
        }catch (TaskException e){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Errore, si prega di ritornare alla home e riprovare. Premere ENTER. Causa: "+e.getMessage());
            scanner.nextLine();
        }
    }
}
