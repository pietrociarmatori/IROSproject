package it.pietrociarmatori.model.helpers;

import it.pietrociarmatori.exceptions.ConnectionPoolException;

import java.sql.Connection;

// Nel caso in cui ci fosse bisogno di tipi diversi di pools e/o connessioni a DB diversi
public interface ConnectionPoolInterface {
    Connection getConnection() throws ConnectionPoolException;
    void releaseConnection(Connection conn) throws ConnectionPoolException;

}
