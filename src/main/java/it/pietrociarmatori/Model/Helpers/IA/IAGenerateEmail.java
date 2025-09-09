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

public class IAGenerateEmail implements IAStrategy{
    public Map<String,String> execute(Params params) throws IOException, InterruptedException {
        List<String> parametri = params.getParams();
        String nomeCandidato = parametri.get(0);
        String cognomeCandidato = parametri.get(1);
        String posizione = parametri.get(2);
        String dipartimento = parametri.get(3);
        String idoneita = parametri.get(4);

        // inserisce i parametri dentro il prompt
        Gson gson = new Gson();
        String deepPromptMAIL = "Sei un assistente HR esperto. Scrivi una email formale, chiara e professionale (massimo 120 parole) per comunicare l’esito di una candidatura. I dati del candidato e l’esito sono forniti di seguito. La mail deve ringraziare il candidato per l’interesse mostrato, indicare chiaramente il ruolo e il dipartimento per cui si è candidato, e comunicare l’esito della selezione (esito = idoneo / davalutare / nonidoneo).\\n\\nLa mail deve adattarsi al valore di esito:\\n- Se idoneo: esprimi apprezzamento e informa che sarà contattato per i prossimi passi.\\n- Se davalutare: spiega che la candidatura è in fase di valutazione e che seguiranno aggiornamenti.\\n- Se nonidoneo: ringrazia e comunica che non è stato selezionato, in modo rispettoso.\\n\\nNon includere firme, date o dettagli inventati.\\n\\nDati del candidato:\\n- Nome: "+nomeCandidato+"\\n- Cognome: "+cognomeCandidato+"\\n- Posizione: "+posizione+"\\n- Dipartimento: "+dipartimento+"\\n- Esito: "+idoneita;
        String jsonPayloadEMAIL = "{\"text\": "+gson.toJson(deepPromptMAIL)+"}";

        // chiama python
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/deep-instruct"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayloadEMAIL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //parsa
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        String content = jsonObject.get("content").getAsString();
        Map<String, String> result = new HashMap<>();
        result.put("email", content);

        // ritorna una mappa con: email:valore
        return result;
    }
}
