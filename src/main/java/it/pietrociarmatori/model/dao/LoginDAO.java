package it.pietrociarmatori.model.dao;

import it.pietrociarmatori.exceptions.ConnectionPoolException;
import it.pietrociarmatori.model.beans.CredentialsEmployeeBean;
import it.pietrociarmatori.model.beans.CredentialsHRBean;
import it.pietrociarmatori.model.beans.LoginCredentialsBean;
import it.pietrociarmatori.model.dao.queries.LoginQueries;
import it.pietrociarmatori.model.entity.Credentials;
import it.pietrociarmatori.exceptions.DAOException;
import it.pietrociarmatori.model.helpers.ConnectionPoolMYSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public Credentials execute(LoginCredentialsBean lcb) throws DAOException {
        Credentials cred = null;
        Connection connection;
        ResultSet rs;

        try {
            ConnectionPoolMYSQL connectionPool = ConnectionPoolMYSQL.getInstance();
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
            connectionPool.releaseConnection(connection);
        } catch(SQLException e) {

            throw new DAOException("Impossibile accedere al database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
        return cred;
    }
}