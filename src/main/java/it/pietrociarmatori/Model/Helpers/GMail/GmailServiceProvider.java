package it.pietrociarmatori.Model.Helpers.GMail;

import com.google.api.services.gmail.Gmail;
import it.pietrociarmatori.Exceptions.GmailServiceException;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.Helpers.AuthService;

import java.io.IOException;
import java.security.GeneralSecurityException;

// Questa classe verrà interfacciata da chi vuole usare gmail
public class GmailServiceProvider {
    private static Gmail gmailService;
    public static Gmail getGmailService() throws GmailServiceException {

        if(gmailService == null){
            gmailService = GmailQuickStart.getGmailService();
        }
        return gmailService;
    }
}
