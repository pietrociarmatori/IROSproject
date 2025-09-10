package it.pietrociarmatori.ControllerAppl;

import com.google.api.services.gmail.Gmail;
import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Exceptions.GmailServiceException;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.Beans.EmailBean;
import it.pietrociarmatori.Model.DAO.CandidatiDAO;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Helpers.AuthService;
import it.pietrociarmatori.Model.Helpers.GMail.GmailSender;
import it.pietrociarmatori.Model.Helpers.GMail.GmailServiceProvider;
import it.pietrociarmatori.Model.Helpers.HROnly;

public class InviaEmailController {
    // Se HR è soddisfatto della mail generata dal sistema, la invia al candidato
    @HROnly
    public void inviaMail(CredentialsHRBean cred, CandidatoBean cand, EmailBean mail) throws TaskException {
        AuthService auth;
        CandidatiDAO dao;
        try {
            auth = new AuthService();
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
