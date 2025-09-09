package it.pietrociarmatori.Model.Beans;

public class OsservazioneBean {
    private String ID;
    private String nome;
    private String cognome;
    private String matricola;
    private String dipartimento;
    private String osservazione;
    private String sentiment;

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDipartimento() {
        return dipartimento;
    }

    public void setDipartimento(String dipartimento) {
        this.dipartimento = dipartimento;
    }

    public String getOsservazione() {
        return osservazione;
    }

    public void setOsservazione(String osservazione) {
        this.osservazione = osservazione;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
