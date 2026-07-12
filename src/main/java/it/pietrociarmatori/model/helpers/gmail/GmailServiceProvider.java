package it.pietrociarmatori.model.helpers.gmail;

import com.google.api.services.gmail.Gmail;
import it.pietrociarmatori.exceptions.GmailServiceException;

// Questa classe verrà interfacciata da chi vuole usare gmail
public class GmailServiceProvider {
    private static Gmail gmailService;
    public static Gmail getGmailService() throws GmailServiceException {

        if(gmailService == null){
            gmailService = GmailQuickStart.getGmailService();
        }
        return gmailService;
    }
    private GmailServiceProvider(){}
}
