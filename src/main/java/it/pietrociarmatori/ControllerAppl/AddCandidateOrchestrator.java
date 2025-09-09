package it.pietrociarmatori.ControllerAppl;

import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Exceptions.IAServiceException;
import it.pietrociarmatori.Model.DAO.*;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Helpers.IA.HuggingFaceClient;
import it.pietrociarmatori.Model.Helpers.IA.IAFitness;
import it.pietrociarmatori.Model.Helpers.IA.IAParseCV;
import it.pietrociarmatori.Model.Helpers.Params;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// Viene chiamato orchestrator perché orchestra una funzione automatica del sistema. A livello pratico fa quello che fa un controller
// infatti coordina le classi per arrivare ad un obiettivo. Non è però un vero e proprio controller applicativo (ragion per cui mi sono
// permesso degli attributi) perché non rappresenta un caso d'uso che può essere inizializzato da un tipo di utente
// per cui il sistema è stato pensato.
public class AddCandidateOrchestrator {
    private String application;
    private Map<String, String> applicationDetails;
    private HuggingFaceClient hfc;
    public AddCandidateOrchestrator(String param){
        this.application = param;
    }
    public void handleApplication(){
        String idoneita;

        parseCV();
        String skills = applicationDetails.get("skills").concat(".Anni di esperienza: " + applicationDetails.get("anni_esperienza"));
        applicationDetails.remove("skills");
        applicationDetails.remove("anni_esperienza");
        applicationDetails.put("skills", skills);

        idoneita = getIdoneita();
        applicationDetails.put("idoneita", idoneita);

        addCandidato();
    }
    private void parseCV(){
        try {
            Params param = new Params();
            List<String> list = new ArrayList<>(0);
            list.add(application);
            param.setParams(list);

            hfc = new HuggingFaceClient(param);
            hfc.setStrategy(new IAParseCV());
            hfc.executeIAService();
            applicationDetails = hfc.getResultIA();
        }catch(IAServiceException e){}
    }
    private String getIdoneita(){
        String idoneita = null;
        try {
            String posizione = applicationDetails.get("posizione");
            PosizioniAperteDAOJDBC dao = new PosizioniAperteDAOJDBC();
            String requisitiPosizione = dao.getRequisiti(posizione);

            applicationDetails.put("requisiti", requisitiPosizione);
            String skills = applicationDetails.get("skills");
            Params param = new Params();
            List<String> list = new ArrayList<>(0);

            list.add(requisitiPosizione);
            list.add(skills);
            param.setParams(list);

            hfc.setParams(param);
            hfc.setStrategy(new IAFitness());
            hfc.executeIAService();
            idoneita = hfc.getResultIA().get("idoneita");
        }catch(IAServiceException | DAOException e){}
        return idoneita;
    }

    private void addCandidato(){
        try {
            CandidatoBean candidato = new CandidatoBean();
            candidato.setNome(applicationDetails.get("nome"));
            candidato.setCognome(applicationDetails.get("cognome"));
            candidato.setPosizione(applicationDetails.get("posizione"));
            candidato.setRequisitiPosizione(applicationDetails.get("requisiti"));
            candidato.setSkillCandidato(applicationDetails.get("skills"));
            candidato.setIndirizzoMailCandidato(applicationDetails.get("mail"));
            candidato.setMailDiRisposta(null);
            candidato.setNomeDipartimento(applicationDetails.get("dipartimento"));
            candidato.setIdoneita(applicationDetails.get("idoneita"));

            CandidatiDAO dao = new CandidatiDAO();
            dao.addCandidato(candidato);
        }catch(DAOException e){}
    }
}
