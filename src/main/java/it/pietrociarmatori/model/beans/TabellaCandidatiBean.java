package it.pietrociarmatori.model.beans;

import it.pietrociarmatori.model.entity.CandidatiDipartimento;

import java.util.Map;

public class TabellaCandidatiBean {
    private Map<String, CandidatiDipartimento> tabella;

    public Map<String, CandidatiDipartimento> getTabella() {
        return tabella;
    }

    public void setTabella(Map<String, CandidatiDipartimento> tabella) {
        this.tabella = tabella;
    }
}

