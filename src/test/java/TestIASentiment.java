import it.pietrociarmatori.Model.Helpers.IA.IASentiment;
import it.pietrociarmatori.Model.Helpers.Params;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestIASentiment {
    @Test
    public void testExecutePositiveSentiment() throws IOException, InterruptedException {
        Params params = new Params();
        List<String> phrase = new ArrayList<>();
        phrase.add("Sono soddisfatto dell'ambiente lavorativo");
        params.setParams(phrase);

        IASentiment ia = new IASentiment();
        Map<String, String> result = ia.execute(params);

        Assertions.assertEquals("positive", result.get("sentiment"));
    }
    @Test
    public void testExecuteNegativeSentiment() throws IOException, InterruptedException {
        Params params = new Params();
        List<String> phrase = new ArrayList<>();
        phrase.add("Carico di lavoro molto elevato, difficoltà nel rispettare i tempi di consegna");
        params.setParams(phrase);

        IASentiment ia = new IASentiment();
        Map<String, String> result = ia.execute(params);

        Assertions.assertEquals("negative", result.get("sentiment"));
    }
    @Test
    public void testExecuteNeutralSentiment() throws IOException, InterruptedException {
        Params params = new Params();
        List<String> phrase = new ArrayList<>();
        phrase.add("Mi sono spostato al bar a bere un latte macchiato");
        params.setParams(phrase);

        IASentiment ia = new IASentiment();
        Map<String, String> result = ia.execute(params);

        Assertions.assertEquals("neutral",result.get("sentiment"));
    }
}
