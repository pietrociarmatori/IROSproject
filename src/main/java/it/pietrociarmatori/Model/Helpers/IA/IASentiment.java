package it.pietrociarmatori.Model.Helpers.IA;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.pietrociarmatori.Model.Helpers.Params;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IASentiment implements IAStrategy{
    public Map<String,String> execute(Params params) throws IOException, InterruptedException {
        List<String> parametri = params.getParams();
        String osservazione = parametri.get(0);
        // riempie il prompt con l'osservazione

        Gson gson = new Gson();
        String jsonPayloadBERT = "{\"texts\":["+gson.toJson(osservazione)+"]}";
        // chiama python prende solo il primo valore di sentiment, in particolare la prima label(è quello con più peso)
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/bert-sentiment"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayloadBERT))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // QUESTO BLOCCO SERVE COME PROVA PER IL PARSING DI BERT
        Map<String, String> result = new HashMap<>();

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("results");

        if (results != null && results.size() > 0) {
            JsonObject firstResult = results.get(0).getAsJsonObject();

            // Estrai frase
            String text = firstResult.get("text").getAsString();
            result.put("frase", text);

            // Estrai primo sentiment label
            JsonArray sentimentOuter = firstResult.getAsJsonArray("sentiment");
            if (sentimentOuter != null && sentimentOuter.size() > 0) {
                JsonArray innerSentiment = sentimentOuter.get(0).getAsJsonArray();
                if (innerSentiment.size() > 0) {
                    String label = innerSentiment.get(0).getAsJsonObject().get("label").getAsString();
                    result.put("sentiment", label);
                }
            }
        }
        // ritorna una mappa con: frase:valore, sentiment:valore
        return result;
    }
}