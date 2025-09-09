package it.pietrociarmatori.Model.Helpers.GMail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import it.pietrociarmatori.Exceptions.GmailServiceException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

// QUESTO HELPER CREA UNA PRIMA CONNESSIONE CON GMAIL E RESTITUISCE LE UN OGGETTO GMAIL CONTENENTE LE AUTORIZZAZIONI
// L'OGGETTO GMAIL DOVRA' ESSERE USATO PER CONFIGUARARE GLI OGGETTI: GMAILWATCHER E GMAILSENDER
public class GmailQuickStart {
    private static final String APPLICATION_NAME = "projectIROS";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = List.of(GmailScopes.GMAIL_READONLY, GmailScopes.GMAIL_SEND, GmailScopes.GMAIL_MODIFY);
    private static final String CREDENTIALS_FILE_PATH = "/credentialsIROS.json";

    public static Credential getCredentials() throws IOException, GeneralSecurityException{
        InputStream in = GmailQuickStart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if(in == null){
            throw new IOException("File non trovato: "+CREDENTIALS_FILE_PATH);
        }

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in)),
                SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static Gmail getGmailService() throws GmailServiceException {
        Gmail service = null;
        try {
            Credential credential = getCredentials();
            service = new Gmail.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }catch(IOException | GeneralSecurityException e){
            throw new GmailServiceException("Impossibile generare credenziali servizio gmail");
        }
        return service;
    }
}
