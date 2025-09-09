package it.pietrociarmatori.Model.DAO;

import it.pietrociarmatori.Exceptions.ConnectionPoolException;
import it.pietrociarmatori.Model.Beans.CredentialsEmployeeBean;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.Beans.LoginCredentialsBean;
import it.pietrociarmatori.Model.DAO.queries.LoginQueries;
import it.pietrociarmatori.Model.Entity.Credentials;
import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.Helpers.ConnectionPoolMYSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    private ConnectionPoolMYSQL connectionPool;

    public Credentials execute(LoginCredentialsBean lcb) throws DAOException {
        Credentials cred = null;
        Connection connection = null;
        Throwable exc = null;
        ResultSet rs = null;

        try {
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            rs = LoginQueries.login(connection, lcb);

            while (rs.next()) {
                if (rs.getString(3).equals("HR")) {
                    cred = new CredentialsHRBean();
                } else {
                    cred = new CredentialsEmployeeBean();
                }
                cred.setMatricola(String.valueOf(rs.getInt(1)));
                cred.setPassword(rs.getString(2));
                cred.setRuolo(rs.getString(3));
                cred.setNome(rs.getString(4));
                cred.setCognome(rs.getString(5));
                cred.setDipartimento(rs.getString(6));
            }
        } catch(SQLException e) {
            exc = e;
            throw new DAOException("Impossibile accedere al database");
        }catch(ConnectionPoolException e){
            exc = e;
            throw new DAOException(e.getMessage());
        } finally{
            try{
                if(connection != null)
                    connectionPool.releaseConnection(connection);
            }catch(ConnectionPoolException e){
                if(exc != null){ // non voglio perdere l'eccezione originale
                    exc.addSuppressed(e);
                }else {
                    throw new DAOException("Impossibile chiudere propriamente la connessione");
                }
            }
        }
        return cred;
    }
}