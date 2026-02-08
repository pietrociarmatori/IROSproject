package it.pietrociarmatori.Model.Helpers;

import it.pietrociarmatori.Model.Beans.CredentialsEmployeeBean;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.Entity.Credentials;

import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private static AuthService auth;
    private static List<Credentials> ActiveHR;
    private static List<Credentials> ActiveEmployee;
    public void addActive(Credentials cred){
        if(cred instanceof CredentialsHRBean)
            ActiveHR.add(cred);
        else if(cred instanceof CredentialsEmployeeBean)
            ActiveEmployee.add(cred);
    }
    public void removeActive(Credentials cred){
        if(cred instanceof CredentialsHRBean)
            ActiveHR.remove(cred);
        else if(cred instanceof CredentialsEmployeeBean)
            ActiveEmployee.remove(cred);
    }
    public Boolean isDipendente(CredentialsEmployeeBean cred){
       return ActiveEmployee.contains(cred);
    }
    public Boolean isHR(CredentialsHRBean cred){
        return ActiveHR.contains(cred);
    }
    private AuthService(){
    }
    public static synchronized AuthService getInstance(){
        if(auth == null){
            auth = new AuthService();
            ActiveEmployee = new ArrayList<>();
            ActiveHR = new ArrayList<>();
        }
        return auth;
    }
}
