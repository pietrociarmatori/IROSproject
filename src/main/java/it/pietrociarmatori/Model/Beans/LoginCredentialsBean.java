package it.pietrociarmatori.Model.Beans;

// questa bean serve ad ottenere i dati del login dall'esterno
public class LoginCredentialsBean {
    private String matricola;
    private String password;

    public String getMatricola(){
        return matricola;
    }
    public String getPassword(){
        return password;
    }
    public void setMatricola(String matr){
        this.matricola = matr;
    }
    public void setPassword(String passwd){
        this.password = passwd;
    }
}
