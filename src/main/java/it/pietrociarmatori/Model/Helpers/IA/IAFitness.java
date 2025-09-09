package it.pietrociarmatori.Model.Helpers.IA;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.pietrociarmatori.Model.Helpers.Params;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IAFitness implements IAStrategy{
    public Map<String, String> execute(Params params) throws IOException, InterruptedException {
        List<String> parametri = params.getParams();
        String requisiti = parametri.get(0);
        String skills = parametri.get(1);

        // inserisce i parametri dentro il prompt
        Gson gson = new Gson();
        String deepPromptIDONEITA = "Devi valutare se un candidato è adatto per una posizione lavorativa. Ti fornirò i requisiti richiesti per la posizione e le competenze del candidato. Restituisci una sola parola tra queste tre: idoneo, nonidoneo, davalutare. Non fornire spiegazioni, motivazioni o testo aggiuntivo.\\n\\nREQUISITI POSIZIONE:\\n"+requisiti+"\\n\\nSKILLS CANDIDATO: "+skills;
        String jsonPayloadIDONEITA = "{\"text\": "+gson.toJson(deepPromptIDONEITA)+"}";

        // chiama python
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/deep-instruct"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayloadIDONEITA))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // parsa e metti in mappa
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        String idoneitaValue = jsonObject.get("content").getAsString();

        Map<String, String> result = new HashMap<>();
        result.put("idoneita", idoneitaValue);

        // ritorna una mappa con idoneita:valore
        return result;
    }
}
