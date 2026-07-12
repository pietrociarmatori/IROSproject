package it.pietrociarmatori.controllerappl;

import com.google.api.services.gmail.Gmail;
import it.pietrociarmatori.exceptions.GmailServiceException;
import it.pietrociarmatori.exceptions.LoginException;
import it.pietrociarmatori.model.beans.CredentialsHRBean;
import it.pietrociarmatori.model.beans.LoginCredentialsBean;
import it.pietrociarmatori.model.dao.LoginDAO;
import it.pietrociarmatori.model.entity.Credentials;
import it.pietrociarmatori.exceptions.DAOException;
import it.pietrociarmatori.model.helpers.AuthService;
import it.pietrociarmatori.model.helpers.gmail.GmailServiceProvider;
import it.pietrociarmatori.model.helpers.gmail.GmailWatcher;
import it.pietrociarmatori.model.helpers.ListenerThread;
import it.pietrociarmatori.model.helpers.UvicornLauncher;

// questo controller regola il login: crea una login dao, e chiede alla factory di creare una bean contente
// i dettagli dell'utente che si è loggato. Questa bean viene ritrnata dal suo metodo e consegnata all'esterno
// del sistema
// poi sebbene il logout sia l'opposto del login, reputo che dare a questo controller il compito del logout
// rispetti comunque il principio di high cohesion in quanto reputo siano due azioni attinenti allo stesso campo

// QUESTO CONTROLLER DEVE LANCIARE ANCHE IL SINGLETON GMAIL LISTENER E LO SCRIPT BASH CHE AVVIA IL SERVER FAST API IN PYTHON
public class LoginController {

    // prima di chiamare il metodo login, può essere settato un listener (sfruttato da un'eventuale view) che serve ad ascoltare
    // il lancio di eccezioni da parte dei thread: Uvicorn e GmailWatcher

    public Credentials login(LoginCredentialsBean lcb, ListenerThread listener) throws LoginException {
        Credentials cred = null;
        LoginDAO loginDao = null;
        UvicornLauncher ul = null;

        try{
            loginDao = new LoginDAO();

            cred = loginDao.execute(lcb);
            if(cred == null){
                throw new LoginException("Credenziali errate o non esistenti");
            }

            if(cred instanceof CredentialsHRBean){
                Gmail service = GmailServiceProvider.getGmailService();
                GmailWatcher.startWatching(service, listener);
            }

            ul = new UvicornLauncher("resources/launcher.properties",
                    "DIR");

            ul.launch(listener);

            AuthService auth = AuthService.getInstance();
            auth.addActive(cred);

        } catch(DAOException | GmailServiceException e){
            throw new LoginException(e.getMessage());
        }
        return cred;
    }

    public void logout(Credentials cred){
        AuthService auth = AuthService.getInstance();
        auth.removeActive(cred);
    }
}