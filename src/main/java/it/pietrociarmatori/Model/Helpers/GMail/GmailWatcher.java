package it.pietrociarmatori.Model.Helpers.GMail;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;
import it.pietrociarmatori.ControllerAppl.AddCandidateOrchestrator;
import it.pietrociarmatori.Exceptions.GmailServiceException;
import it.pietrociarmatori.Model.Helpers.ListenerThread;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

// Questa classe serve a sentire la casella postale ogni tot tempo.
// Ad ogni nuova mail, fa partire orchestrator che provvederà a parsare e poi a categorizzare il curriculum
// La classe può, se settato in precedenza nel controller chiamante, notificare un listener di eventuali errori.
// Siccome particolarmente importante, prevede una logica di riavvio se si verificano più problemi. Con il riavvio
// si fa attenzione a mantenere il riferimento al listener.
public class GmailWatcher {

    private static final long POLLING_INTERVAL = 40; // secondi, realisticamente si potrebbe fare anche ogni ora->meno query
    private static final String USER = "me";
    private static final Set<String> processedMessageIds = ConcurrentHashMap.newKeySet();

    public static void startWatching(Gmail gmailService, ListenerThread listener){
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            int retryCount = 0;
            final int maxRetries = 3;

            try {
                ListMessagesResponse response = gmailService.users().messages().list(USER)
                        .setQ("is:unread label:inbox")
                        .setMaxResults(20L)
                        .execute();

                List<Message> messages = response.getMessages();
                if (messages == null || messages.isEmpty()) return;

                for (Message msg : messages) {
                    String msgId = msg.getId();
                    if (processedMessageIds.contains(msgId)) continue;

                    Message fullMessage = gmailService.users().messages().get(USER, msgId)
                            .setFormat("full")
                            .execute();

                    String from = "";
                    String body = "";

                    for (MessagePartHeader header : fullMessage.getPayload().getHeaders()) {
                        if ("From".equalsIgnoreCase(header.getName())) {
                            from = header.getValue();
                            break;
                        }
                    }

                    body = extractPlainText(fullMessage.getPayload());

                    // Marca la mail come letta
                    ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(List.of("UNREAD"));
                    gmailService.users().messages().modify(USER, msgId, mods).execute();

                    processedMessageIds.add(msgId);

                    // ORA AGGIUNGI IL FROM AL BODY E MANDA TUTTO ALL'ORCHESTRATOR
                    String toOrchestrator = body.concat("\n"+from);
                    AddCandidateOrchestrator aco = new AddCandidateOrchestrator(toOrchestrator);
                    aco.handleApplication();
                }

                retryCount = 0; // si effettua il reset ad ogni poll avvenuta con successo
            } catch (Throwable e){
                // poi crea ErrorListener implementabile da qualche classe fuori dal sistema al fine di essere notificato tramite Observer,
                // di errori nei thread asincroni
                if(listener != null) {
                    listener.notify(new Throwable("Errore nel watcher, se errore persiste, il servizio riprenderà dopo piccolo reboot. Causa: "+e.getMessage()));
                }else{
                    System.err.println("Listener non settato, throwable non gestito, errore nel watcherGmail");
                    e.printStackTrace();
                }
                retryCount++; // si incrementa ad ogni errore ricevuto
                if(retryCount >= maxRetries){ // qui l'ide mi dice che "retryCount >= maxRetries" non accadrà mai, penso il contrario
                    System.err.println("Watcher in crisi, riavvio");

                    scheduler.shutdown();

                    // schedulo tra 5 secondi un nuovo watcher mantenendo lo stesso listener
                    Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                        startWatching(gmailService, listener);
                    }, 5, TimeUnit.SECONDS);
                }
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, POLLING_INTERVAL, TimeUnit.SECONDS);
    }

    private static String extractPlainText(MessagePart part) {
        if (part == null) return "";
        if (part.getMimeType() != null && "text/plain".equals(part.getMimeType()) && part.getBody() != null) {
            byte[] bodyBytes = Base64.getUrlDecoder().decode(part.getBody().getData());
            return new String(bodyBytes);
        }
        if (part.getParts() != null) {
            for (MessagePart subPart : part.getParts()) {
                String result = extractPlainText(subPart);
                if (!result.isEmpty()) return result;
            }
        }
        return "";
    }
}