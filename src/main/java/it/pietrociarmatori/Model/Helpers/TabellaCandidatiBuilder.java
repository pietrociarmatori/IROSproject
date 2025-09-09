package it.pietrociarmatori.Model.Helpers;

import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Beans.TabellaCandidatiBean;
import it.pietrociarmatori.Model.Entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabellaCandidatiBuilder {
    private final String[] DIPARTIMENTI = {"SoftwareDevelopment", "DataEngineering", "Security", "Sales", "HumanResources"};
    private List<CandidatoBean> candidati;
    private TabellaCandidatiBean tc;
    public void buildTabella(){
        Map<String, CandidatiDipartimento> tabella = new HashMap<>();

        for(int i = 0; i < DIPARTIMENTI.length; i++){
            CandidatiDipartimento candidatiDipartimento = new CandidatiDipartimento();
            candidatiDipartimento.setNomeDipartimento(DIPARTIMENTI[i]);
            List<CandidatoBean> provv = new ArrayList<>(0);
            for(int j = 0; j < candidati.size(); j++){
                if(DIPARTIMENTI[i].equals(candidati.get(j).getNomeDipartimento())){
                    provv.add(candidati.get(j));
                }
            }
            candidatiDipartimento.setListaCandidati(provv);
            tabella.put(DIPARTIMENTI[i], candidatiDipartimento);
        }
        tc.setTabella(tabella);
    }
    public TabellaCandidatiBuilder(List<CandidatoBean> candidati){
        this.candidati = candidati;
        this.tc = new TabellaCandidatiBean();
    }

    public TabellaCandidatiBean getTabellaCandidati(){
        return tc;
    }
}
