package it.pietrociarmatori.Model.Helpers;

import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.Beans.CredentialsEmployeeBean;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.DAO.AuthDAO;

import java.sql.SQLException;

public class AuthService {
   private AuthDAO auth;
   public Boolean isDipendente(CredentialsEmployeeBean cred1) throws DAOException {
       auth = new AuthDAO();
       CredentialsEmployeeBean cred2 = auth.getDipendente(cred1);
       // se cred1.ruolo == cred2.ruolo && cred1.ruolo != HR
       if((cred1.getRuolo().equals(cred2.getRuolo())) && !(cred1.getRuolo().equals("HR"))){
           return true;
       }
       return false;
   }
   public Boolean isHR(CredentialsHRBean cred1) throws DAOException{
       auth = new AuthDAO();
       CredentialsHRBean cred2 = auth.getHR(cred1);
       // se cred1.ruolo == cred2.ruolo && cred1.ruolo == HR
       if((cred1.getRuolo().equals(cred2.getRuolo())) && (cred1.getRuolo().equals("HR"))){
           return true;
       }
       return false;
   }
}
