package it.pietrociarmatori.Model.DAO;

import it.pietrociarmatori.Exceptions.ConnectionPoolException;
import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.Beans.PosizioneBean;
import it.pietrociarmatori.Model.DAO.queries.PosizioniQueries;
import it.pietrociarmatori.Model.Helpers.ConnectionPoolMYSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PosizioniAperteDAOJDBC implements PosizioniAperteDAO {
    private ConnectionPoolMYSQL connectionPool;
    public List<PosizioneBean> getPosizioniAperte() throws DAOException {
        List<PosizioneBean> data = new ArrayList<>(0);
        PosizioneBean pos = null;
        Connection connection = null;
        Throwable exc = null;
        ResultSet rs = null;

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
        }catch(SQLException e) {
            exc = e;
            throw new DAOException("Impossibile eliminare il candidato dal database");
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

        return data;
    }

    public void addPosizioneAperta(PosizioneBean pos) throws DAOException {
        Connection connection = null;
        Throwable exc = null;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            PosizioniQueries.aggiungiPosizione(connection, pos);

        }catch(SQLException e) {
            exc = e;
            throw new DAOException("Impossibile eliminare il candidato dal database");
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
    }

    public void deletePosizioneAperta(PosizioneBean pos) throws DAOException {
        Connection connection = null;
        Throwable exc = null;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            PosizioniQueries.eliminaPosizione(connection, pos);

        }catch(SQLException e) {
            exc = e;
            throw new DAOException("Impossibile eliminare il candidato dal database");
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
    }
    public String getRequisiti(String posizione) throws DAOException {
        Connection connection = null;
        String requisiti = null;
        Throwable exc = null;
        ResultSet rs = null;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            rs = PosizioniQueries.getRequisiti(connection, posizione);

            while(rs.next()){
                requisiti = rs.getString(1);
            }
        }catch(SQLException e) {
            exc = e;
            throw new DAOException("Impossibile eliminare il candidato dal database");
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
        return requisiti;
    }
}