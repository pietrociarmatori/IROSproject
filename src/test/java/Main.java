import it.pietrociarmatori.Model.Helpers.UvicornLauncher;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        UvicornLauncher ul = new UvicornLauncher("resources/launcher.properties",
                "DIR");

        ul.launch(null);
        Thread.sleep(1000);

        TestIASentiment sentiment = new TestIASentiment();
        sentiment.testExecutePositiveSentiment();
        sentiment.testExecuteNegativeSentiment();
        sentiment.testExecuteNeutralSentiment();

        TestIAFitness fitness = new TestIAFitness();
        fitness.testExecuteIdoneo();
        fitness.testExecuteNonIdoneo();
        fitness.testExecuteDaValutare();

        TestIAParseCV parse = new TestIAParseCV();
        parse.testExecuteCompleteParse();
        parse.testExecuteEmptyEmail();
        System.out.println("TestPassato");
    }
}
