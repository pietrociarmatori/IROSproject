package it.pietrociarmatori.ControllerAppl;

import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Exceptions.IAServiceException;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.Beans.EmailBean;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Helpers.AuthService;
import it.pietrociarmatori.Model.Helpers.HROnly;
import it.pietrociarmatori.Model.Helpers.IA.HuggingFaceClient;
import it.pietrociarmatori.Model.Helpers.IA.IAGenerateEmail;
import it.pietrociarmatori.Model.Helpers.Params;

import java.util.ArrayList;
import java.util.List;

public class GeneraMailDiRispostaController {

    // HR può decidere di generare una mail automatica per rispondere ad una candidatura
    // La mail viene generata da un LLM che a seconda del contesto e il livello di idoneità
    // del candidato propone ad HR una possibile risposta
    @HROnly
    public EmailBean generaRisposta(CredentialsHRBean cred, CandidatoBean cand) throws TaskException {
        AuthService auth = null;
        HuggingFaceClient hfc = null;
        EmailBean mail = null;
        try {
            auth = new AuthService();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            Params param = new Params();
            List<String> list = new ArrayList<>(0);
            list.add(cand.getNome());
            list.add(cand.getCognome());
            list.add(cand.getPosizione());
            list.add(cand.getNomeDipartimento());
            list.add(cand.getIdoneita());
            param.setParams(list);

            mail = new EmailBean();

            hfc = new HuggingFaceClient(param);
            hfc.setStrategy(new IAGenerateEmail());
            hfc.executeIAService();
            mail.setEmail(hfc.getResultIA().get("email"));
        }catch(DAOException | IAServiceException e){
            throw new TaskException("Errore durante la pubblicazione dell'osservazione", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }
        return mail;
    }
}
