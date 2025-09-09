package it.pietrociarmatori.View.View2;

import it.pietrociarmatori.ControllerAppl.AggiungiOsservazioneController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.OsservazioneBean;
import it.pietrociarmatori.View.SessionEmployee;
import it.pietrociarmatori.View.SessionHR;

import java.util.Scanner;

public class EmployeeFormScreen implements CliScreen{
    private SessionEmployee sessionEmployee;
    public EmployeeFormScreen(SessionEmployee sessionEmployee){
        this.sessionEmployee = sessionEmployee;
    }
    @Override
    public void render() {
        System.out.println("-------------------------------------------------------                                                                                            Dati profilo:");
        System.out.println("-------------------------------------------------------");
        System.out.println("-------  --------------  --------------  --------------                                                                                            -)Nome: "+sessionEmployee.getCred().getNome());
        System.out.println("|     |  |            |  |            |  |            |                                                                                            -)Cognome: "+sessionEmployee.getCred().getCognome());
        System.out.println("|     |  |     ---    |  |      |     |  |            |                                                                                            -)Ruolo: "+sessionEmployee.getCred().getRuolo());
        System.out.println("|     |  |            |  |      |     |  |       -----|                                                                                            -)Dipartimento: "+sessionEmployee.getCred().getDipartimento());
        System.out.println("|     |  |       -----|  |      |     |  |            |                                                                                            -)Matricola: "+sessionEmployee.getCred().getMatricola());
        System.out.println("|     |  |            |  |      |     |  |-----       |                                                                                            -)Password: "+sessionEmployee.getCred().getPassword());
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
        System.out.println("Benvenuto, " + sessionEmployee.getCred().getNome()+" "+sessionEmployee.getCred().getCognome() + "!");
        System.out.println();
        System.out.println("1) Pubblica un'osservazione sul tuo dipartimento");
        System.out.println("2) Logout");
        System.out.print("Scegli un'opzione digitando il numero corrispondente: ");
    }

    @Override
    public CliScreen handleInput(Scanner scanner) {
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                try{
                    OsservazioneBean oss = new OsservazioneBean();
                    oss.setNome(sessionEmployee.getCred().getNome());
                    oss.setCognome(sessionEmployee.getCred().getCognome());
                    oss.setMatricola(sessionEmployee.getCred().getMatricola());
                    oss.setDipartimento(sessionEmployee.getCred().getDipartimento());
                    System.out.println("Prego, inserisci la tua osservazione:");
                    oss.setOsservazione(scanner.nextLine());

                    AggiungiOsservazioneController aoc = new AggiungiOsservazioneController();
                    aoc.aggiungiOsservazione(sessionEmployee.getCred(), oss);

                    System.out.println("Osservazione aggiunta con successo, premere ENTER per tornare al menù di selezione");
                    scanner.nextLine();
                    return this;
                } catch (TaskException e) {
                    System.out.println("Errore durante l'aggiunta della posizione, premere ENTER e riprovare...");
                    scanner.nextLine();
                    return this;
                }
            case "2":
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
                System.exit(0);
                break;
            default:
                System.out.println("Scegliere una delle tre opzioni!");
                scanner.nextLine();
                return this;
        }
    }
}
