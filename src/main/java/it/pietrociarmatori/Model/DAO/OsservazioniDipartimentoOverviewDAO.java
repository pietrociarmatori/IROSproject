package it.pietrociarmatori.Model.DAO;

import it.pietrociarmatori.Exceptions.ConnectionPoolException;
import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.DAO.queries.OverviewDipartimentoQueries;
import it.pietrociarmatori.Model.Entity.DipartimentoSentimentOverview;
import it.pietrociarmatori.Model.Beans.ProvvedimentoBean;
import it.pietrociarmatori.Model.Helpers.ConnectionPoolInterface;
import it.pietrociarmatori.Model.Helpers.ConnectionPoolMYSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OsservazioniDipartimentoOverviewDAO {
    private ConnectionPoolInterface connectionPool;

    public void aggiungiOverviewDipartimento(String dip, String riass, String sentiment) throws DAOException {
        Connection connection;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            OverviewDipartimentoQueries.aggiungiRiassunto(connection, dip, riass);
            OverviewDipartimentoQueries.aggiungiSentiment(connection, dip, sentiment);

        }catch(SQLException e) {

            throw new DAOException("Impossibile eliminare il candidato dal database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
    }

    public List<DipartimentoSentimentOverview> getDipartimentiOverview() throws DAOException {
        List<DipartimentoSentimentOverview> data = new ArrayList<>(0);
        DipartimentoSentimentOverview overview;
        Connection connection;
        ResultSet rs;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            rs = OverviewDipartimentoQueries.getDipartimentiOverview(connection);

            while(rs.next()){
                overview = new DipartimentoSentimentOverview();
                overview.setDipartimento(rs.getString(1));
                overview.setRiassuntoOsservazioni(rs.getString(2));
                overview.setProvvedimenti(rs.getString(3));
                overview.setSentimentGenerale(rs.getString(4));
                data.add(overview);
            }
        }catch(SQLException e) {

            throw new DAOException("Impossibile eliminare il candidato dal database");
        }catch(ConnectionPoolException e) {

            throw new DAOException(e.getMessage());
        }

        return data;
    }

    public void addProvvedimento(ProvvedimentoBean provv) throws DAOException {
        Connection connection;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            OverviewDipartimentoQueries.aggiungiProvvedimenti(connection, provv);
        }catch(SQLException e) {

            throw new DAOException("Impossibile aggiungere provvedimento al database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
    }
}