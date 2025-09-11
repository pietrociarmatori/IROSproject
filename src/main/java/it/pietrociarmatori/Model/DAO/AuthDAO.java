package it.pietrociarmatori.Model.DAO;

import it.pietrociarmatori.Exceptions.ConnectionPoolException;
import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.Beans.CredentialsEmployeeBean;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.DAO.queries.AuthQueries;
import it.pietrociarmatori.Model.Helpers.ConnectionPoolInterface;
import it.pietrociarmatori.Model.Helpers.ConnectionPoolMYSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAO {
    private ConnectionPoolInterface connectionPool;

    public CredentialsEmployeeBean getDipendente(CredentialsEmployeeBean cred) throws DAOException{
        CredentialsEmployeeBean ceb = new CredentialsEmployeeBean();
        Connection connection;
        ResultSet rs;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            rs = AuthQueries.getDipendente(connection, cred);

            while(rs.next()){
                ceb.setMatricola(String.valueOf(rs.getInt(1)));
                ceb.setPassword(rs.getString(2));
                ceb.setRuolo(rs.getString(3));
                ceb.setNome(rs.getString(4));
                ceb.setCognome(rs.getString(5));
                ceb.setDipartimento(rs.getString(6));
            }
        }catch(SQLException e) {
            throw new DAOException("Servizio di autorizzazione non disponibile");
        }catch(ConnectionPoolException e){
            throw new DAOException(e.getMessage());
        }
        return ceb;
    }
    public CredentialsHRBean getHR(CredentialsHRBean cred) throws DAOException{
        CredentialsHRBean ceb = new CredentialsHRBean();
        Connection connection;
        ResultSet rs;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            rs = AuthQueries.getHR(connection, cred);

            while(rs.next()){
                ceb.setMatricola(String.valueOf(rs.getInt(1)));
                ceb.setPassword(rs.getString(2));
                ceb.setRuolo(rs.getString(3));
                ceb.setNome(rs.getString(4));
                ceb.setCognome(rs.getString(5));
                ceb.setDipartimento(rs.getString(6));
            }
        }catch(SQLException e) {
            throw new DAOException("Servizio di autorizzazione non disponibile");
        }catch(ConnectionPoolException e){
            throw new DAOException(e.getMessage());
        }
        return ceb;
    }
}
