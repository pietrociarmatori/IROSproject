package it.pietrociarmatori.controllerappl;

import com.google.api.services.gmail.Gmail;
import it.pietrociarmatori.exceptions.DAOException;
import it.pietrociarmatori.exceptions.GmailServiceException;
import it.pietrociarmatori.exceptions.TaskException;
import it.pietrociarmatori.model.beans.CredentialsHRBean;
import it.pietrociarmatori.model.beans.EmailBean;
import it.pietrociarmatori.model.dao.CandidatiDAO;
import it.pietrociarmatori.model.beans.CandidatoBean;
import it.pietrociarmatori.model.helpers.AuthService;
import it.pietrociarmatori.model.helpers.gmail.GmailSender;
import it.pietrociarmatori.model.helpers.gmail.GmailServiceProvider;
import it.pietrociarmatori.model.helpers.HROnly;

public class InviaEmailController {
    // Se HR è soddisfatto della mail generata dal sistema, la invia al candidato
    @HROnly
    public void inviaMail(CredentialsHRBean cred, CandidatoBean cand, EmailBean mail) throws TaskException {
        AuthService auth;
        CandidatiDAO dao;
        try {
            auth = AuthService.getInstance();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            Gmail service = GmailServiceProvider.getGmailService();
            GmailSender sender = new GmailSender(service, "iros.onboarding@gmail.com");

            sender.sendEmail(cand.getIndirizzoMailCandidato(), "Risposta candidatura IROS", mail.getEmail());

            dao = new CandidatiDAO();
            cand.setMailDiRisposta(mail.getEmail());
            dao.addMailCandidato(cand);
        }catch(DAOException | GmailServiceException e){
            throw new TaskException("Errore durante l'invio della mail", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }
    }
}
