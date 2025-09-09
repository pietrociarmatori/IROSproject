package it.pietrociarmatori.Model.Helpers;

import it.pietrociarmatori.Model.Beans.PosizioneBean;
import it.pietrociarmatori.Model.Entity.PosizioniDipartimento;
import it.pietrociarmatori.Model.Beans.TabellaPosizioniAperteBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// itera tutte le posizioni e quando trova una posizione con ruolo uguale a quella del dipartimento che si vuole
// riempire durante l'iterazione corrente, allora la posizione viene aggiunta nel dipartimento.
// il ciclo più esterno invece si occupa di riempire la tabella.
// non è un "algoritmo" con complessità spaziale e temporale minima ma va bene.
public class TabellaPosizioniAperteBuilder{
    private final String[] DIPARTIMENTI = {"SoftwareDevelopment", "DataEngineering", "Security", "Sales", "HumanResources"};
    private List<PosizioneBean> posizioni;
    private TabellaPosizioniAperteBean tpa;
    public void buildTabella(){

        Map<String, PosizioniDipartimento> tabella = new HashMap<>();

        for(int i = 0; i < DIPARTIMENTI.length; i++){
            PosizioniDipartimento posizioniDipartimento = new PosizioniDipartimento();
            posizioniDipartimento.setNomeDipartimento(DIPARTIMENTI[i]);
            List<PosizioneBean> provv = new ArrayList<>(0);
            for(int j = 0; j < posizioni.size(); j++){
                if(DIPARTIMENTI[i].equals(posizioni.get(j).getDipartimento())){
                    provv.add(posizioni.get(j));
                }
            }
            posizioniDipartimento.setListaPosizioni(provv);
            tabella.put(DIPARTIMENTI[i],posizioniDipartimento);
        }
        tpa.setTabella(tabella);
    }

    public TabellaPosizioniAperteBuilder(List<PosizioneBean> posizioni){
        this.posizioni = posizioni;
        this.tpa = new TabellaPosizioniAperteBean();
    }
    public TabellaPosizioniAperteBean getTabellaPosizioniAperte(){
        return tpa;
    }
}
