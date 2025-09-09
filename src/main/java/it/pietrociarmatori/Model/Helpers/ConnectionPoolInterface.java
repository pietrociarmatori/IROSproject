package it.pietrociarmatori.Model.Helpers;

import it.pietrociarmatori.Exceptions.ConnectionPoolException;

import java.sql.Connection;

// Nel caso in cui ci fosse bisogno di tipi diversi di pools e/o connessioni a DB diversi
public interface ConnectionPoolInterface {
    Connection getConnection() throws ConnectionPoolException;
    void releaseConnection(Connection conn) throws ConnectionPoolException;

}
