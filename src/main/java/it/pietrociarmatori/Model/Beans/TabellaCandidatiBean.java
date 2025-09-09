package it.pietrociarmatori.Model.Beans;

import it.pietrociarmatori.Model.Entity.CandidatiDipartimento;

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

