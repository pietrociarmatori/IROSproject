package it.pietrociarmatori.View.View2;

import it.pietrociarmatori.ControllerAppl.GeneraMailDiRispostaController;
import it.pietrociarmatori.ControllerAppl.GestisciCandidatiController;
import it.pietrociarmatori.ControllerAppl.InviaEmailController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Beans.EmailBean;
import it.pietrociarmatori.View.SessionHR;

import java.util.Scanner;

public class CandidateDetailScreen implements CliScreen {
    private SessionHR sessionHR;
    private CandidatoBean candidato;
    private EmailBean mail;

    public CandidateDetailScreen(SessionHR sessionHR, CandidatoBean candidato) {
        this.sessionHR = sessionHR;
        this.candidato = candidato;
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
        System.out.println("CANDIDATO: ");
        System.out.println("Nome:          " + candidato.getNome() + " " + candidato.getCognome());
        System.out.println("Posizione:     " + candidato.getPosizione());
        System.out.println("Dipartimento:  " + candidato.getNomeDipartimento());
        System.out.println("Skills:        " + candidato.getSkillCandidato());
        System.out.println("Requisiti:     " + candidato.getRequisitiPosizione());
        System.out.println("Email:         " + candidato.getIndirizzoMailCandidato());
        System.out.println("Idoneità:      " + candidato.getIdoneita());
        System.out.println();
        System.out.println("1) Genera risposta");
        System.out.println("2) Invia risposta");
        System.out.println("3) Elimina candidato");
        System.out.println("4) Sposta candidato in gruppo idonei");
        System.out.println("5) Sposta candidato in gruppo non idonei");
        System.out.println("6) Sposta candidato in gruppo da valutare");
        System.out.println("7) Indietro");
        System.out.print("Scegli un'opzione digitando il numero corrispondente: ");
    }

    //TODO: mettere le funzionalità direttamente dentro i case, finisci prima di arrivare a roma
    @Override
    public CliScreen handleInput(Scanner scanner) {
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                try {
                    System.out.println("Generazione mail in corso, attendere...");
                    GeneraMailDiRispostaController gmdr = new GeneraMailDiRispostaController();
                    this.mail = gmdr.generaRisposta(sessionHR.getCred(), candidato);

                    System.out.println("La mail generata: ");
                    System.out.println(mail.getEmail());

                    System.out.println("");
                    System.out.println("Se soddisfatti, premere ENTER e procedere con l'invio, altrimenti premere ENTER e generare una nuova mail");
                    scanner.nextLine();

                    return this;
                } catch (TaskException e) {
                    System.out.println("Errore nella generazione mail, premere ENTER e riprovare...");
                    scanner.nextLine();
                    return this;
                }
            case "2":
                try {
                    // invia mail, comunica dell'avvenuto invio e ricarica la pagina
                    InviaEmailController iec = new InviaEmailController();
                    iec.inviaMail(sessionHR.getCred(), candidato, mail);

                    System.out.println("Mail inviata, premere ENTER per selezionare un'altra azione");
                    scanner.nextLine();
                    return this;
                }catch(TaskException e){
                    System.out.println("Errore durante invio mail, premere ENTER e riprovare...");
                    scanner.nextLine();
                    return this;
                }
            case "3":
                try {
                    // elimina candidato, comunica dell'avvenuta eliminazione e ricarica la pagina
                    GestisciCandidatiController gcc = new GestisciCandidatiController();
                    gcc.deleteCandidato(sessionHR.getCred(), candidato);

                    System.out.println("Candidato eliminato, premere ENTER per selezionare un'altra opzione");
                    scanner.nextLine();
                    return this;
                } catch (TaskException e) {
                    System.out.println("Errore durante eliminazione candidato, premere ENTER  e riprovare...");
                    scanner.nextLine();
                    return this;
                }
            case "4":
                try {
                    // controlla se il candidato è già in idonei, dopo di che sposta e comunica
                    if (candidato.getIdoneita().equals("Idoneo")) {
                        System.out.println("Candidato già idoneo, premere ENTER e selezionare un'altra opzione");
                        scanner.nextLine();
                        return this;
                    }
                    GestisciCandidatiController gcc = new GestisciCandidatiController();
                    gcc.spostaCandidato(sessionHR.getCred(), candidato, "Idoneo");

                    System.out.println("Candidato spostato in 'Idonei', premere ENTER per selezionare un'altra opzione");
                    scanner.nextLine();
                    return this;
                } catch (TaskException e) {
                    System.out.println("Errore durante la ricollocazione del candidato, premere ENTER e riprovare...");
                    scanner.nextLine();
                    return this;
                }
            case "5":
                //inNonIdonei
                try {
                    // controlla se il candidato è già in idonei, dopo di che sposta e comunica
                    if (candidato.getIdoneita().equals("NonIdoneo")) {
                        System.out.println("Candidato già non idoneo, premere ENTER e selezionare un'altra opzione");
                        scanner.nextLine();
                        return this;
                    }
                    GestisciCandidatiController gcc = new GestisciCandidatiController();
                    gcc.spostaCandidato(sessionHR.getCred(), candidato, "NonIdoneo");

                    System.out.println("Candidato spostato in 'NonIdonei', premere ENTER per selezionare un'altra opzione");
                    scanner.nextLine();
                    return this;
                } catch (TaskException e) {
                    System.out.println("Errore durante la ricollocazione del candidato, premere ENTER e riprovare...");
                    scanner.nextLine();
                    return this;
                }
            case "6":
                //inDaValutare
                try {
                    // controlla se il candidato è già in idonei, dopo di che sposta e comunica
                    if (candidato.getIdoneita().equals("DaValutare")) {
                        System.out.println("Candidato già da valutare, premere ENTER e selezionare un'altra opzione");
                        scanner.nextLine();
                        return this;
                    }
                    GestisciCandidatiController gcc = new GestisciCandidatiController();
                    gcc.spostaCandidato(sessionHR.getCred(), candidato, "DaValutare");

                    System.out.println("Candidato spostato in 'DaValutare', premere ENTER per selezionare un'altra opzione");
                    scanner.nextLine();
                    return this;
                } catch (TaskException e) {
                    System.out.println("Errore durante la ricollocazione del candidato, premere ENTER e riprovare...");
                    scanner.nextLine();
                    return this;
                }
            case "7":
                return new CandidatesScreen(sessionHR);
            default:
                System.out.println("Scelta non possibile, premere ENTER e riprovare");
                scanner.nextLine();
                return this;
        }
    }
}
