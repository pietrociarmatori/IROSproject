package it.pietrociarmatori.Model.Helpers.IA;

import com.google.gson.*;
import it.pietrociarmatori.Model.Helpers.Params;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IAParseCV implements IAStrategy{
    public Map<String, String> execute(Params params) throws IOException, InterruptedException {
        List<String> parametri = params.getParams();
        String mail = parametri.get(0);

        // inserisce mail nel prompt
        String deepPromptPARSECV = "Sei un assistente HR intelligente. Ti fornirò il testo di una email ricevuta da un candidato che si sta candidando per una posizione lavorativa. "
                + "Il tuo compito è estrarre in formato JSON i seguenti dati: "
                + "- nome: solo il nome del candidato - cognome: solo il cognome del candidato - mail: l'indirizzo email del candidato "
                + "- skills: elenco delle competenze tecniche dichiarate (es. \"Java\", \"SQL\", \"React\") "
                + "- anni_esperienza: numero approssimativo di anni di esperienza dichiarati "
                + "- posizione: il nome della posizione per cui si candida - dipartimento: il nome del dipartimento a cui appartiene la posizione "
                + "### Formato di output (JSON): { \"nome\": \"...\", \"cognome\": \"...\", \"mail\": \"...\", \"skills\": \"...\", \"anni_esperienza\": \"...\", \"posizione\": \"...\", \"dipartimento\": \"...\" } "
                + "Restituisci solo il JSON. Non aggiungere commenti, descrizioni o testo extra. ### Testo email: " + mail;

        // usa Gson per escapare correttamente tutto
        Gson gson = new Gson();
        String jsonPayloadCV = "{ \"text\": " + gson.toJson(deepPromptPARSECV) + " }";

        // chiama python
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/deep-instruct"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayloadCV))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // ora parso la risposta
        JsonObject obj = gson.fromJson(response.body(), JsonObject.class);

        // estrai la stringa contenente il codice JSON
        String content = obj.get("content").getAsString();

        // rimuovi il blocco di codice markdown e ripulisci
        String cleaned = content
                .replace("```json", "")
                .replace("```", "")
                .trim();

        // ora puoi parsare questo cleaned JSON
        JsonObject jsonData = gson.fromJson(cleaned, JsonObject.class);

        // crea la mappa
        JsonObject jsonObject = JsonParser.parseString(gson.toJson(jsonData)).getAsJsonObject();

        Map<String, String> result = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement valueElement = entry.getValue();

            // Se è un array, unisci gli elementi con virgole
            if (valueElement.isJsonArray()) {
                JsonArray array = valueElement.getAsJsonArray();
                List<String> items = new ArrayList<>();
                for (JsonElement item : array) {
                    items.add(item.getAsString());
                }
                result.put(key, String.join(", ", items));
            }
            // Altrimenti basta convertirlo in stringa
            else {
                result.put(key, valueElement.getAsString());
            }
        }

        // restituisce una mappa con: nome:valore,cognome:valore,mail:valore,skills:valore,annidiesperienza:valore,posizione:valore,dipartimento:valore
        return result;
    }
}
