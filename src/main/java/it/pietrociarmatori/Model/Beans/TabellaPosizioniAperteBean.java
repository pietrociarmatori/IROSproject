package it.pietrociarmatori.Model.Beans;

import it.pietrociarmatori.Model.Entity.PosizioniDipartimento;

import java.util.Map;

public class TabellaPosizioniAperteBean {
    private Map<String, PosizioniDipartimento> tabella;

    public Map<String, PosizioniDipartimento> getTabella() {
        return tabella;
    }

    public void setTabella(Map<String, PosizioniDipartimento> tabella) {
        this.tabella = tabella;
    }
}

