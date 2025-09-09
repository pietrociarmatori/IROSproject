package it.pietrociarmatori.Model.Helpers.IA;

import it.pietrociarmatori.Model.Helpers.Params;

import java.io.IOException;
import java.util.Map;

public interface IAStrategy {
    Map<String, String> execute(Params params) throws IOException, InterruptedException;
}
