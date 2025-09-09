package it.pietrociarmatori.Model.Entity;

// questa classe serve a creare un albero che tracci l'organico nel mondo reale
public abstract class Credentials {
    protected String matricola;
    protected String password;
    protected String ruolo;
    protected String nome;
    protected String cognome;
    protected String dipartimento;

    public abstract String getMatricola();
    public abstract String getPassword();
    public abstract String getRuolo();
    public abstract String getNome();
    public abstract String getCognome();
    public abstract String getDipartimento();
    public abstract void setMatricola(String matr);
    public abstract void setPassword(String passwd);
    public abstract void setRuolo(String ruolo);
    public abstract void setNome(String nome);
    public abstract void setCognome(String cognome);
    public abstract void setDipartimento(String dip);
}
