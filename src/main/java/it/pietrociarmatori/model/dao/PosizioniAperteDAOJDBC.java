package it.pietrociarmatori.model.dao;

import it.pietrociarmatori.exceptions.ConnectionPoolException;
import it.pietrociarmatori.exceptions.DAOException;
import it.pietrociarmatori.model.beans.PosizioneBean;
import it.pietrociarmatori.model.dao.queries.PosizioniQueries;
import it.pietrociarmatori.model.helpers.ConnectionPoolMYSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PosizioniAperteDAOJDBC implements PosizioniAperteDAO {
    private ConnectionPoolMYSQL connectionPool;
    public List<PosizioneBean> getPosizioniAperte() throws DAOException {
        List<PosizioneBean> data = new ArrayList<>(0);
        PosizioneBean pos;
        Connection connection;
        ResultSet rs;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            rs = PosizioniQueries.getPosizioni(connection);

            while(rs.next()){
                pos = new PosizioneBean();
                pos.setDipartimento(rs.getString(1));
                pos.setNomePosizione(rs.getString(2));
                pos.setRequisiti(rs.getString(3));
                data.add(pos);
            }
            connectionPool.releaseConnection(connection);
        }catch(SQLException e) {

            throw new DAOException("Impossibile eliminare il candidato dal database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }

        return data;
    }

    public void addPosizioneAperta(PosizioneBean pos) throws DAOException {
        Connection connection;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            PosizioniQueries.aggiungiPosizione(connection, pos);

            connectionPool.releaseConnection(connection);

        }catch(SQLException e) {

            throw new DAOException("Impossibile eliminare il candidato dal database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
    }

    public void deletePosizioneAperta(PosizioneBean pos) throws DAOException {
        Connection connection;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            PosizioniQueries.eliminaPosizione(connection, pos);

            connectionPool.releaseConnection(connection);

        }catch(SQLException e) {

            throw new DAOException("Impossibile eliminare il candidato dal database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
    }
    public String getRequisiti(String posizione) throws DAOException {
        Connection connection;
        String requisiti = null;
        ResultSet rs;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            rs = PosizioniQueries.getRequisiti(connection, posizione);

            while(rs.next()){
                requisiti = rs.getString(1);
            }
            connectionPool.releaseConnection(connection);
        }catch(SQLException e) {

            throw new DAOException("Impossibile eliminare il candidato dal database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
        return requisiti;
    }
}