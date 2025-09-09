package it.pietrociarmatori.Model.Beans;

import it.pietrociarmatori.Model.Entity.OsservazioniDipartimento;

import java.util.Map;

public class TabellaOsservazioniBean {
    private Map<String, OsservazioniDipartimento> tabella;

    public Map<String, OsservazioniDipartimento> getTabella() {
        return tabella;
    }

    public void setTabella(Map<String, OsservazioniDipartimento> tabella) {
        this.tabella = tabella;
    }
}
