package it.pietrociarmatori.Model.Helpers.IA;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.pietrociarmatori.Model.Helpers.Params;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class IASummary implements IAStrategy{
    public Map<String, String> execute(Params params) throws IOException, InterruptedException {
        List<String> parametri = params.getParams();
        String osservazioni = parametri.stream().collect(Collectors.joining(" "));

        // inserisci i componenti della lista nel prompt, il numero è variabile quindi ti servirà un ciclo
        Gson gson = new Gson();
        String deepPromptRIASSUNTO = "Sintetizza le osservazioni seguenti in una sola frase. " +
                "Devi restituire solo la frase, senza nessun altro testo o formattazione. " +
                "La frase deve essere scritta in italiano corretto e non deve superare 70 parole. " +
                "ATTENZIONE: se aggiungi spiegazioni, elenchi o qualsiasi contenuto extra, la risposta sarà considerata errata.\n\n" +
                "OSSERVAZIONI:\n"+osservazioni;
        String jsonPayloadRIASSUNTO = "{\"text\": "+gson.toJson(deepPromptRIASSUNTO)+"}";

        // chiama python
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/deep-instruct"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayloadRIASSUNTO))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        // elimino ultima riga
        String content = jsonObject.get("content").getAsString();
        Map<String, String> result = new HashMap<>();
        result.put("riassunto", content);
        // ritorna una mappa con riassunto:valore
        return result;
    }
}
