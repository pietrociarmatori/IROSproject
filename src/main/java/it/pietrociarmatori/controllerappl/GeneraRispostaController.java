package it.pietrociarmatori.controllerappl;

import it.pietrociarmatori.exceptions.IAServiceException;
import it.pietrociarmatori.exceptions.TaskException;
import it.pietrociarmatori.model.beans.CredentialsHRBean;
import it.pietrociarmatori.model.beans.EmailBean;
import it.pietrociarmatori.model.beans.CandidatoBean;
import it.pietrociarmatori.model.helpers.AuthService;
import it.pietrociarmatori.model.helpers.HROnly;
import it.pietrociarmatori.model.helpers.ia.HuggingFaceClient;
import it.pietrociarmatori.model.helpers.ia.IAGenerateEmail;
import it.pietrociarmatori.model.helpers.Params;

import java.util.ArrayList;
import java.util.List;

public class GeneraRispostaController {

    // HR può decidere di generare una mail automatica per rispondere a una candidatura
    // La mail viene generata da un LLM che a seconda del contesto e il livello di idoneità
    // del candidato propone ad HR una possibile risposta
    @HROnly
    public EmailBean generaRisposta(CredentialsHRBean cred, CandidatoBean cand) throws TaskException {
        AuthService auth = null;
        HuggingFaceClient hfc = null;
        EmailBean mail = null;
        try {
            auth = AuthService.getInstance();
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
        }catch(IAServiceException e){
            throw new TaskException("Errore durante la pubblicazione dell'osservazione, servizio IA non disponibile", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }
        return mail;
    }
}
