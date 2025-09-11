import it.pietrociarmatori.Model.Helpers.IA.IAFitness;
import it.pietrociarmatori.Model.Helpers.IA.IAParseCV;
import it.pietrociarmatori.Model.Helpers.Params;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestIAParseCV {
    @Test
    public void testExecuteCompleteParse() throws IOException, InterruptedException {
        Params params = new Params();
        List<String> phrase = new ArrayList<>();
        phrase.add("Gentile IROS,\n" +
                "\n" +
                "mi chiamo Pietro Ciarmatori e desidero sottoporre la mia candidatura per la posizione di Junior Fullstack Developer presso il reparto SoftwareDevelopment.\n" +
                "\n" +
                "Ho recentemente completato un percorso di formazione in ambito sviluppo web, durante il quale ho acquisito solide conoscenze di HTML, CSS e JavaScript, oltre a nozioni di backend con Python. Anche se la mia esperienza lavorativa è ancora agli inizi (0–1 anno), sono fortemente motivato ad apprendere e a crescere professionalmente all’interno di un contesto stimolante e collaborativo come il vostro.\n" +
                "\n" +
                "Mi contraddistinguono entusiasmo, capacità di lavorare in team e una spiccata attitudine al problem solving. Credo che queste qualità, unite alla mia voglia di mettermi alla prova, possano contribuire in modo positivo ai vostri progetti.\n" +
                "\n" +
                "Ringrazio per l’attenzione e porgo cordiali saluti.\n" +
                "\n" +
                "Pietro Ciarmatori\n" +
                "\n" +
                "Email: pietro.ciarmatori@gmail.com\n" +
                "\n" +
                "Telefono: 3333333333");
        params.setParams(phrase);

        IAParseCV ia = new IAParseCV();
        Map<String, String> result = ia.execute(params);

        Assertions.assertEquals("Pietro",result.get("nome"));
        Assertions.assertEquals("Ciarmatori",result.get("cognome"));
        Assertions.assertEquals("pietro.ciarmatori@gmail.com",result.get("mail"));
        Assertions.assertEquals("HTML, CSS, JavaScript, Python",result.get("skills"));
        Assertions.assertEquals("Junior Fullstack Developer",result.get("posizione"));
        Assertions.assertEquals("SoftwareDevelopment",result.get("dipartimento"));
    }
    @Test
    public void testExecuteEmptyEmail() throws IOException, InterruptedException {
        Params params = new Params();
        List<String> phrase = new ArrayList<>();
        phrase.add("");
        params.setParams(phrase);

        IAParseCV ia = new IAParseCV();
        Map<String, String> result = ia.execute(params);

        Assertions.assertEquals("mail vuota",result.get("skills"));
    }
}
