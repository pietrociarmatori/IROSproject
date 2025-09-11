package it.pietrociarmatori.Model.DAO;

import it.pietrociarmatori.Exceptions.ConnectionPoolException;
import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.Beans.OsservazioneBean;
import it.pietrociarmatori.Model.DAO.queries.OsservazioniQueries;
import it.pietrociarmatori.Model.Helpers.ConnectionPoolInterface;
import it.pietrociarmatori.Model.Helpers.ConnectionPoolMYSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OsservazioniDAO{
    private ConnectionPoolInterface connectionPool;
    public List<OsservazioneBean> getOsservazioni(String dipartimento) throws DAOException{
        List<OsservazioneBean> data = new ArrayList<>(0);
        OsservazioneBean oss;
        Connection connection;
        ResultSet rs;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            switch(dipartimento){
                case "SoftwareDevelopment":
                    rs = OsservazioniQueries.getSDOsservazioni(connection);
                    break;
                case "DataEngineering":
                    rs = OsservazioniQueries.getDEOsservazioni(connection);
                    break;
                case "Security":
                    rs = OsservazioniQueries.getSecOsservazioni(connection);
                    break;
                case "Sales":
                    rs = OsservazioniQueries.getSalOsservazioni(connection);
                    break;
                default:
                    throw new DAOException("Valore di dipartimento non valido: "+dipartimento);
            }

            while(rs.next()){
                oss = new OsservazioneBean();
                oss.setMatricola(String.valueOf(rs.getInt(1)));
                oss.setNome(rs.getString(2));
                oss.setCognome(rs.getString(3));
                oss.setDipartimento(rs.getString(4));
                oss.setOsservazione(rs.getString(5));
                oss.setSentiment(rs.getString(6));
                oss.setID(String.valueOf(rs.getInt(7)));
                data.add(oss);
            }
        }catch(SQLException e) {

            throw new DAOException("Impossibile eliminare il candidato dal database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
        return data;
    }
    public void deleteOsservazione(OsservazioneBean osservazione) throws DAOException{
        Connection connection;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            switch (osservazione.getDipartimento()) {
                case "SoftwareDevelopment":
                    OsservazioniQueries.elimSDOsservazione(connection, osservazione);
                    break;
                case "DataEngineering":
                    OsservazioniQueries.elimDEOsservazione(connection, osservazione);
                    break;
                case "Security":
                    OsservazioniQueries.elimSecOsservazione(connection, osservazione);
                    break;
                case "Sales":
                    OsservazioniQueries.elimSalOsservazione(connection, osservazione);
                    break;
                default:
                        throw new DAOException("Valore di dipartimento non valido: " + osservazione.getDipartimento());
            };

        }catch(SQLException e) {

            throw new DAOException("Impossibile eliminare il candidato dal database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
    }
    public void addOsservazione(OsservazioneBean osservazione) throws DAOException{
        Connection connection;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            switch (osservazione.getDipartimento()) {
                case "SoftwareDevelopment":
                        OsservazioniQueries.addSDOsservazione(connection, osservazione);
                        break;
                case "DataEngineering":
                    OsservazioniQueries.addDEOsservazione(connection, osservazione);
                    break;
                case "Security":
                    OsservazioniQueries.addSecOsservazione(connection, osservazione);
                    break;
                case "Sales":
                    OsservazioniQueries.addSalOsservazione(connection, osservazione);
                    break;
                default:
                        throw new DAOException("Valore di dipartimento non valido: " + osservazione.getDipartimento());
            };

        }catch(SQLException e) {

            throw new DAOException("Impossibile eliminare il candidato dal database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
    }
}