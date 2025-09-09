package it.pietrociarmatori.View.View2;

import it.pietrociarmatori.View.SessionHR;

import java.util.Scanner;

public class HomeScreen implements CliScreen{
    private SessionHR sessionHR;

    public HomeScreen(SessionHR sessionHR) {
        this.sessionHR = sessionHR;
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
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("HOME: ");
        System.out.println();
        System.out.println("Benvenuto, " + sessionHR.getCred().getNome()+" "+sessionHR.getCred().getCognome() + "!");
        System.out.println();
        System.out.println("1) Gestisci i candidati");
        System.out.println("2) Gestisci le posizioni aperte");
        System.out.println("3) Gestisci le osservazioni dei dipendenti");
        System.out.println("4) Logout");
        System.out.print("Scegli un'opzione digitando il numero corrispondente: ");
    }

    @Override
    public CliScreen handleInput(Scanner scanner) {
        String choice = scanner.nextLine();
        switch (choice) {
            case "1": return new CandidatesScreen(sessionHR);
            case "2": return new PositionsScreen(sessionHR);
            case "3": return new ObservationsScreen(sessionHR);
            case "4":
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
                System.exit(0);
            default:
                System.out.println("Scegliere una delle tre opzioni!");
                scanner.nextLine();
                return this;
        }
    }
}
