package it.pietrociarmatori.Model.Beans;

import it.pietrociarmatori.Model.Entity.Credentials;

// questa classe, uguale a CredentialsEmployeeBean serve a tracciare l'organico nel mondo reale
public class CredentialsHRBean extends Credentials {
    public String getMatricola(){return this.matricola;}

    public String getPassword(){return this.password;}

    public String getRuolo(){return this.ruolo;}

    public String getNome(){return this.nome;}

    public String getCognome(){return this.cognome;}

    public String getDipartimento(){return this.dipartimento;}

    public void setMatricola(String matr){this.matricola = matr;}

    public void setPassword(String passwd){this.password = passwd;}

    public void setRuolo(String ruolo){this.ruolo = ruolo;}

    public void setNome(String nome){this.nome = nome;}

    public void setCognome(String cognome){this.cognome = cognome;}

    public void setDipartimento(String dip){this.dipartimento = dip;}
}
