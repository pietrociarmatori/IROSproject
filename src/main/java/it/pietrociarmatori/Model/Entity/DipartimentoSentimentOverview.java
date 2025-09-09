package it.pietrociarmatori.Model.Entity;

public class DipartimentoSentimentOverview {
    private String dipartimento;
    private String riassuntoOsservazioni;
    private String provvedimenti;
    private String sentimentGenerale;

    public String getDipartimento() {
        return dipartimento;
    }

    public void setDipartimento(String dipartimento) {
        this.dipartimento = dipartimento;
    }

    public String getRiassuntoOsservazioni() {
        return riassuntoOsservazioni;
    }

    public void setRiassuntoOsservazioni(String riassuntoOsservazioni) {
        this.riassuntoOsservazioni = riassuntoOsservazioni;
    }

    public String getProvvedimenti() {
        return provvedimenti;
    }

    public void setProvvedimenti(String provvedimenti) {
        this.provvedimenti = provvedimenti;
    }

    public String getSentimentGenerale() {
        return sentimentGenerale;
    }

    public void setSentimentGenerale(String sentimentGenerale) {
        this.sentimentGenerale = sentimentGenerale;
    }
}
