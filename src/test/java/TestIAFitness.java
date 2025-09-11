import it.pietrociarmatori.Model.Helpers.IA.IAFitness;
import it.pietrociarmatori.Model.Helpers.IA.IASentiment;
import it.pietrociarmatori.Model.Helpers.Params;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestIAFitness {
    @Test
    public void testExecuteIdoneo() throws IOException, InterruptedException {
        Params params = new Params();
        List<String> phrase = new ArrayList<>();
        phrase.add("Esperienza richiesta: 2+ anni. Competenze in analisi dei rischi, vulnerabilità e strumenti SIEM");
        phrase.add("SIEM, risk assessment, vulnerability scanning, Python");
        params.setParams(phrase);

        IAFitness ia = new IAFitness();
        Map<String, String> result = ia.execute(params);

        Assertions.assertEquals("idoneo",result.get("idoneita"));
    }
    @Test
    public void testExecuteNonIdoneo() throws IOException, InterruptedException {
        Params params = new Params();
        List<String> phrase = new ArrayList<>();
        phrase.add("Esperienza richiesta: 5+ anni. Esperienza con RESTful API, Spring Boot, Hibernate e architettura a microservizi");
        phrase.add("3 anni esperienza, Spring Boot, REST API, ma nessuna conoscenza di Hibernate né architetture a microservizi");
        params.setParams(phrase);

        IAFitness ia = new IAFitness();
        Map<String, String> result = ia.execute(params);

        Assertions.assertEquals("nonidoneo",result.get("idoneita"));
    }
    @Test
    public void testExecuteDaValutare() throws IOException, InterruptedException {
        Params params = new Params();
        List<String> phrase = new ArrayList<>();
        phrase.add("Esperienza richiesta: 3+ anni. Conoscenze di penetration testing, Kali Linux, Metasploit, OWASP");
        phrase.add("2 anni e 10 mesi di esperienza. Conosce bene OWASP e Metasploit. Kali Linux usato in laboratorio ma non in ambiente produttivo.");
        params.setParams(phrase);

        IAFitness ia = new IAFitness();
        Map<String, String> result = ia.execute(params);

        Assertions.assertEquals("davalutare",result.get("idoneita"));
    }
}
