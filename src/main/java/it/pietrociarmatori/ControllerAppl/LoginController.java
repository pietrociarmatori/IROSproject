package it.pietrociarmatori.ControllerAppl;

import com.google.api.services.gmail.Gmail;
import it.pietrociarmatori.Exceptions.GmailServiceException;
import it.pietrociarmatori.Exceptions.LoginException;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.Beans.LoginCredentialsBean;
import it.pietrociarmatori.Model.DAO.LoginDAO;
import it.pietrociarmatori.Model.Entity.Credentials;
import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.Helpers.AuthService;
import it.pietrociarmatori.Model.Helpers.GMail.GmailServiceProvider;
import it.pietrociarmatori.Model.Helpers.GMail.GmailWatcher;
import it.pietrociarmatori.Model.Helpers.ListenerThread;
import it.pietrociarmatori.Model.Helpers.UvicornLauncher;

import java.io.IOException;
import java.security.GeneralSecurityException;

// questo controller regola il login: crea una login dao, e chiede alla factory di creare una bean contente
// i dettagli dell'utente che si è loggato. Questa bean viene ritrnata dal suo metodo e consegnata all'esterno
// del sistema

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
                    "APP",
                    "WITH",
                    "COMMAND",
                    "DIR");

            ul.launch(listener);

        } catch(DAOException | GmailServiceException e){
            throw new LoginException(e.getMessage());
        }
        return cred;
    }
}