package it.pietrociarmatori.Model.Helpers.GMail;

import com.google.api.services.gmail.Gmail;

import it.pietrociarmatori.Exceptions.GmailServiceException;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class GmailSender {
    private final Gmail gmailService;
    private final String fromEmail;

    public GmailSender(Gmail gmailService, String fromEmail){
        this.gmailService = gmailService;
        this.fromEmail = fromEmail;
    }

    public void sendEmail(String mailCandidato, String oggettoMail, String contenutoMail) throws GmailServiceException {
        try {
            Session session = Session.getDefaultInstance(new Properties(), null);
            MimeMessage email = new MimeMessage(session);

            email.setFrom(new InternetAddress(fromEmail));
            email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(mailCandidato));
            email.setSubject(oggettoMail);
            email.setText(contenutoMail);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            String raw = Base64.getUrlEncoder().encodeToString(buffer.toByteArray());

            // sono andato a finire in un super nameclash che però siccome sono le 3 non risolverò e perderò tempo a scrivere questo
            com.google.api.services.gmail.model.Message message = new com.google.api.services.gmail.model.Message();
            message.setRaw(raw);
            gmailService.users().messages().send("me", message).execute();
        }catch(IOException | MessagingException e){
            throw new GmailServiceException("Impossibile inviare mail a: "+mailCandidato);
        }
    }
}
