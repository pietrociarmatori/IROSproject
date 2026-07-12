package it.pietrociarmatori.model.helpers.ia;

import it.pietrociarmatori.model.helpers.Params;

import java.io.IOException;
import java.util.Map;

public interface IAStrategy {
    Map<String, String> execute(Params params) throws IOException, InterruptedException;
}
