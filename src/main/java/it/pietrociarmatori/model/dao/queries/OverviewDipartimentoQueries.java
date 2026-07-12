package it.pietrociarmatori.model.dao.queries;

import it.pietrociarmatori.model.beans.ProvvedimentoBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Utilizzando PreparedStatement mitigo sql injection
public class OverviewDipartimentoQueries {
    private OverviewDipartimentoQueries(){}
    public static void aggiungiRiassunto(Connection connection, String dip, String riass) throws SQLException{
        String sql = "UPDATE DipartimentiSentimentOverview \n" +
                "    SET RiassuntoOsservazioni = ?\n" +
                "    WHERE Dipartimento = ?;";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, riass);
            ps.setString(2, dip);

            ps.executeUpdate();
        }
    }
    public static void aggiungiSentiment(Connection connection, String dip, String sent) throws SQLException{
        String sql = "UPDATE DipartimentiSentimentOverview \n" +
                "    SET SentimentGenerale = ?\n" +
                "    WHERE Dipartimento = ?;";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, sent);
            ps.setString(2, dip);

            ps.executeUpdate();
        }
    }
    public static ResultSet getDipartimentiOverview(Connection connection) throws SQLException{
        String sql = "SELECT * FROM DipartimentiSentimentOverview;";

        PreparedStatement ps = connection.prepareStatement(sql);

        return ps.executeQuery();
    }
    public static void aggiungiProvvedimenti(Connection connection, ProvvedimentoBean provv) throws SQLException{
        String sql = "UPDATE DipartimentiSentimentOverview \n" +
                "    SET Provvedimenti = ?\n" +
                "    WHERE Dipartimento = ?;";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, provv.getProvvedimento());
            ps.setString(2, provv.getDipartimento());

            ps.executeUpdate();
        }
    }
}
