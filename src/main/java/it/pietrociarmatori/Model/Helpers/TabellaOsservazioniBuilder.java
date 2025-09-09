package it.pietrociarmatori.Model.Helpers;

import it.pietrociarmatori.Model.Beans.OsservazioneBean;
import it.pietrociarmatori.Model.Entity.OsservazioniDipartimento;
import it.pietrociarmatori.Model.Beans.TabellaOsservazioniBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabellaOsservazioniBuilder {
    private final String[] DIPARTIMENTI = {"SoftwareDevelopment", "DataEngineering", "Security", "Sales"};
    private List<OsservazioneBean> osservazioni;
    private TabellaOsservazioniBean to;
    public void buildTabella(){

        Map<String, OsservazioniDipartimento> tabella = new HashMap<>();

        for(int i = 0; i < DIPARTIMENTI.length; i++){
            OsservazioniDipartimento osservazioniDipartimento = new OsservazioniDipartimento();
            osservazioniDipartimento.setNomeDipartimento(DIPARTIMENTI[i]);
            List<OsservazioneBean> provv = new ArrayList<>(0);
            for(int j = 0; j < osservazioni.size(); j++){
                if(DIPARTIMENTI[i].equals(osservazioni.get(j).getDipartimento())){
                    provv.add(osservazioni.get(j));
                }
            }
            osservazioniDipartimento.setListaOsservazioni(provv);
            tabella.put(DIPARTIMENTI[i], osservazioniDipartimento);
        }
        to.setTabella(tabella);
    }
    public TabellaOsservazioniBuilder(List<OsservazioneBean> osservazioni){
        this.osservazioni = osservazioni;
        this.to = new TabellaOsservazioniBean();
    }
    public TabellaOsservazioniBean getTabellaOsservazioni(){
        return to;
    }
}
