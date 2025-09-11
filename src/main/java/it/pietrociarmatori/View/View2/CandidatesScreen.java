package it.pietrociarmatori.View.View2;

import it.pietrociarmatori.ControllerAppl.GestisciCandidatiController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Beans.TabellaCandidatiBean;
import it.pietrociarmatori.Model.Entity.CandidatiDipartimento;
import it.pietrociarmatori.View.SessionHR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CandidatesScreen implements CliScreen {
    private TabellaCandidatiBean tabella;
    private List<CandidatoBean> flatList = new ArrayList<>();
    private SessionHR sessionHR;

    public CandidatesScreen(SessionHR sessionHR) {
        try {
            this.sessionHR = sessionHR;

            GestisciCandidatiController gcc = new GestisciCandidatiController();
            tabella = gcc.getTabellaCandidati(sessionHR.getCred());
            for (CandidatiDipartimento dept : tabella.getTabella().values()) {
                flatList.addAll(dept.getListaCandidati());
            }
        }catch(TaskException e){
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
        System.out.println("CANDIDATI: ");

        int counter = 1;
        for (Map.Entry<String, CandidatiDipartimento> entry : tabella.getTabella().entrySet()) {
            String deptName = entry.getKey();
            CandidatiDipartimento dept = entry.getValue();

            System.out.println();
            System.out.println("Dipartimento: " + deptName);
            System.out.println("--------------------------------");

            for (CandidatoBean cand : dept.getListaCandidati()) {
                System.out.printf("%d) %s %s - %s [%s]\n",
                        counter,
                        cand.getNome(),
                        cand.getCognome(),
                        cand.getPosizione(),
                        cand.getIdoneita()
                );
                counter++;
            }
        }

        System.out.println();
        System.out.println("Selezionare un candidato tramite il suo numero corrispondente, oppure digitare 'b' per tornare indietro:");
    }

    @Override
    public CliScreen handleInput(Scanner scanner) {
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("b")) {
            return new HomeScreen(sessionHR); // back alla home
        }

        try {
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > flatList.size()) {
                System.out.println("Numero candidato inesistente, premere ENTER per riprovare...");
                scanner.nextLine();
                return this;
            }

            CandidatoBean selected = flatList.get(choice - 1);
            return new CandidateDetailScreen(sessionHR, selected);

        } catch (NumberFormatException e) {
            System.out.println("Formato input non valido, premere ENTER per riprovare...");
            scanner.nextLine();
            return this;
        }
    }
}
