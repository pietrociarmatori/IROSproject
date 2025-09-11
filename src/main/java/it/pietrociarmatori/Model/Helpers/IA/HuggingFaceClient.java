package it.pietrociarmatori.Model.Helpers.IA;

import it.pietrociarmatori.Exceptions.IAServiceException;
import it.pietrociarmatori.Model.Helpers.Params;

import java.io.IOException;
import java.util.Map;

// questa classe può essere settata con il servizio di cui si ha più bisogno a runtime
public class HuggingFaceClient {
    private Params params;
    private Map<String,String> resultIA;
    private IAStrategy strategy;
    public HuggingFaceClient(Params params){
        this.params = params;
    }
    public void setStrategy(IAStrategy strategy){
        this.strategy = strategy;
    }
    public void executeIAService() throws IAServiceException{
        try {
            this.resultIA = strategy.execute(params);
        }catch(IOException e){
            throw new IAServiceException("Impossibile procedere con il servizio");
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            throw new IAServiceException("Impossibile procedere con il servizio");
        }
    }
    public Map<String,String> getResultIA(){
        return resultIA;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}
