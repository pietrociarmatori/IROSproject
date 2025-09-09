package it.pietrociarmatori.Model.Entity;

import it.pietrociarmatori.Model.Beans.OsservazioneBean;

import java.util.List;

public class OsservazioniDipartimento{
    private String nomeDipartimento;
    private List<OsservazioneBean> listaOsservazioni;
    private String riassuntoOsservazioni;
    private String sentimentGenerale;
    private String provvedimenti;

    public String getNomeDipartimento() {
        return nomeDipartimento;
    }

    public void setNomeDipartimento(String nomeDipartimento) {
        this.nomeDipartimento = nomeDipartimento;
    }

    public List<OsservazioneBean> getListaOsservazioni() {
        return listaOsservazioni;
    }

    public void setListaOsservazioni(List<OsservazioneBean> listaOsservazioni) {
        this.listaOsservazioni = listaOsservazioni;
    }

    public String getRiassuntoOsservazioni() {
        return riassuntoOsservazioni;
    }

    public void setRiassuntoOsservazioni(String riassuntoOsservazioni) {
        this.riassuntoOsservazioni = riassuntoOsservazioni;
    }

    public String getSentimentGenerale() {
        return sentimentGenerale;
    }

    public void setSentimentGenerale(String sentimentGenerale) {
        this.sentimentGenerale = sentimentGenerale;
    }

    public String getProvvedimenti() {
        return provvedimenti;
    }

    public void setProvvedimenti(String provvedimenti) {
        this.provvedimenti = provvedimenti;
    }
}
