package it.pietrociarmatori.Model.DAO.queries;

import it.pietrociarmatori.Model.Beans.PosizioneBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Utilizzando PreparedStatement mitigo sql injection
public class PosizioniQueries {
    public static ResultSet getPosizioni(Connection connection) throws SQLException{
        String sql = "SELECT * FROM PosizioniAperte;";

        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static void aggiungiPosizione(Connection connection, PosizioneBean pos)throws SQLException{
        String sql = "INSERT INTO PosizioniAperte (Dipartimento, NomePosizione, Requisiti)\n" +
                "    VALUES (?, ?, ?);";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, pos.getDipartimento());
            ps.setString(2, pos.getNomePosizione());
            ps.setString(3, pos.getRequisiti());

            ps.executeUpdate();
        }
    }
    public static void eliminaPosizione(Connection connection, PosizioneBean pos)throws SQLException {
        String sql = "DELETE FROM PosizioniAperte WHERE NomePosizione = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pos.getNomePosizione());

            ps.executeUpdate();
        }
    }
    public static ResultSet getRequisiti(Connection connection, String pos) throws SQLException{
        String sql = "SELECT Requisiti FROM PosizioniAperte\n" +
                "    WHERE NomePosizione LIKE ?;";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, pos);

        return ps.executeQuery();
    }
}
