package it.pietrociarmatori.ControllerAppl;

import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Exceptions.IAServiceException;
import it.pietrociarmatori.Model.DAO.*;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Helpers.IA.HuggingFaceClient;
import it.pietrociarmatori.Model.Helpers.IA.IAFitness;
import it.pietrociarmatori.Model.Helpers.IA.IAParseCV;
import it.pietrociarmatori.Model.Helpers.Params;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// Viene chiamato orchestrator perché orchestra una funzione automatica del sistema. A livello pratico fa quello che fa un controller
// infatti coordina le classi per arrivare a un obiettivo. Non è però un vero e proprio controller applicativo (ragion per cui mi sono
// permesso degli attributi) perché non rappresenta un caso d'uso che può essere inizializzato da un tipo di utente
// per cui il sistema è stato pensato.
public class AddCandidateOrchestrator {
    private String application;
    private Map<String, String> applicationDetails;
    private HuggingFaceClient hfc;
    private final String skills_param = "skills";
    private final String idoneita_param = "idoneita";

    public AddCandidateOrchestrator(String param){
        this.application = param;
    }
    public void handleApplication(){
        try {
            String idoneita;
            parseCV();
            String anni_esperienza_param = "anni_esperienza";
            String skills = applicationDetails.get(skills_param).concat(".Anni di esperienza: " + applicationDetails.get(anni_esperienza_param));
            applicationDetails.remove(skills_param);
            applicationDetails.remove(anni_esperienza_param);
            applicationDetails.put(skills_param, skills);

            idoneita = getIdoneita();
            applicationDetails.put(idoneita_param, idoneita);

            addCandidato();
        }catch(DAOException | IAServiceException e){
            // Append log entry to ./resources/dao.log
            try (FileWriter fw = new FileWriter("src/main/resources/dao.log", true);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println("[" + java.time.LocalDateTime.now() + "] DAOException caught: " + e.getMessage());
                e.printStackTrace(pw); // full stack trace
            } catch (IOException ioEx) {
                // suppress
            }
        }
    }
    private void parseCV() throws IAServiceException {

            Params param = new Params();
            List<String> list = new ArrayList<>(0);
            list.add(application);
            param.setParams(list);

            hfc = new HuggingFaceClient(param);
            hfc.setStrategy(new IAParseCV());
            hfc.executeIAService();
            applicationDetails = hfc.getResultIA();

    }
    private String getIdoneita() throws IAServiceException, DAOException {
        String idoneita = null;

            String posizione = applicationDetails.get("posizione");
            PosizioniAperteDAOJDBC dao = new PosizioniAperteDAOJDBC();
            String requisitiPosizione = dao.getRequisiti(posizione);

            applicationDetails.put("requisiti", requisitiPosizione);
            String skills = applicationDetails.get(skills_param);
            Params param = new Params();
            List<String> list = new ArrayList<>(0);

            list.add(requisitiPosizione);
            list.add(skills);
            param.setParams(list);

            hfc.setParams(param);
            hfc.setStrategy(new IAFitness());
            hfc.executeIAService();
            idoneita = hfc.getResultIA().get(idoneita_param);

        return idoneita;
    }

    private void addCandidato() throws DAOException {

            CandidatoBean candidato = new CandidatoBean();
            candidato.setNome(applicationDetails.get("nome"));
            candidato.setCognome(applicationDetails.get("cognome"));
            candidato.setPosizione(applicationDetails.get("posizione"));
            candidato.setRequisitiPosizione(applicationDetails.get("requisiti"));
            candidato.setSkillCandidato(applicationDetails.get(skills_param));
            candidato.setIndirizzoMailCandidato(applicationDetails.get("mail"));
            candidato.setMailDiRisposta(null);
            candidato.setNomeDipartimento(applicationDetails.get("dipartimento"));
            candidato.setIdoneita(applicationDetails.get(idoneita_param));

            CandidatiDAO dao = new CandidatiDAO();
            dao.addCandidato(candidato);

    }
}
