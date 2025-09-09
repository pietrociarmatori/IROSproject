package it.pietrociarmatori.Model.DAO.queries;

import it.pietrociarmatori.Model.Beans.OsservazioneBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Utilizzando PreparedStatement mitigo sql injection
public class OsservazioniQueries {
    public static ResultSet getSDOsservazioni(Connection connection) throws SQLException{
        String sql = "SELECT \n" +
                "        d.Matricola,\n" +
                "        d.Nome,\n" +
                "        d.Cognome,\n" +
                "        d.Dipartimento,\n" +
                "        o.Osservazione,\n" +
                "        o.Sentiment,\n" +
                "        o.ID\n" +
                "    FROM SoftwareDevelopmentOsservazioni o\n" +
                "    JOIN Dipendenti d ON o.Matricola = d.Matricola;";

        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet getDEOsservazioni(Connection connection) throws SQLException{
        String sql = "SELECT \n" +
                "        d.Matricola,\n" +
                "        d.Nome,\n" +
                "        d.Cognome,\n" +
                "        d.Dipartimento,\n" +
                "        o.Osservazione,\n" +
                "        o.Sentiment,\n" +
                "        o.ID\n" +
                "    FROM DataEngineeringOsservazioni o\n" +
                "    JOIN Dipendenti d ON o.Matricola = d.Matricola;";

        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet getSecOsservazioni(Connection connection) throws SQLException{
        String sql = "SELECT \n" +
                "        d.Matricola,\n" +
                "        d.Nome,\n" +
                "        d.Cognome,\n" +
                "        d.Dipartimento,\n" +
                "        o.Osservazione,\n" +
                "        o.Sentiment,\n" +
                "        o.ID\n" +
                "    FROM SecurityOsservazioni o\n" +
                "    JOIN Dipendenti d ON o.Matricola = d.Matricola;";

        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet getSalOsservazioni(Connection connection) throws SQLException{
        String sql = "SELECT \n" +
                "        d.Matricola,\n" +
                "        d.Nome,\n" +
                "        d.Cognome,\n" +
                "        d.Dipartimento,\n" +
                "        o.Osservazione,\n" +
                "        o.Sentiment,\n" +
                "        o.ID\n" +
                "    FROM SalesOsservazioni o\n" +
                "    JOIN Dipendenti d ON o.Matricola = d.Matricola;";

        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static void elimSDOsservazione(Connection connection, OsservazioneBean oss) throws SQLException{
        String sql = "DELETE FROM SoftwareDevelopmentOsservazioni WHERE ID = ?;";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, Integer.parseInt(oss.getID()));
            ps.executeUpdate();
        }
    }
    public static void elimDEOsservazione(Connection connection, OsservazioneBean oss) throws SQLException{
        String sql = "DELETE FROM DataEngineeringOsservazioni WHERE ID = ?;";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, Integer.parseInt(oss.getID()));
            ps.executeUpdate();
        }
    }
    public static void elimSecOsservazione(Connection connection, OsservazioneBean oss) throws SQLException{
        String sql = "DELETE FROM SecurityOsservazioni WHERE ID = ?;";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, Integer.parseInt(oss.getID()));
            ps.executeUpdate();
        }
    }
    public static void elimSalOsservazione(Connection connection, OsservazioneBean oss) throws SQLException{
        String sql = "DELETE FROM SalesOsservazioni WHERE ID = ?;";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, Integer.parseInt(oss.getID()));
            ps.executeUpdate();
        }
    }
    public static void addSDOsservazione(Connection connection, OsservazioneBean oss) throws SQLException{
        String sql = "INSERT INTO SoftwareDevelopmentOsservazioni (Matricola, Osservazione, Sentiment)\n" +
                "    VALUES (?, ?, ?);";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, Integer.parseInt(oss.getMatricola()));
            ps.setString(2, oss.getOsservazione());
            ps.setString(3, oss.getSentiment());
            ps.executeUpdate();
        }
    }
    public static void addDEOsservazione(Connection connection, OsservazioneBean oss) throws SQLException{
        String sql = "INSERT INTO DataEngineeringOsservazioni (Matricola, Osservazione, Sentiment)\n" +
                "    VALUES (?, ?, ?);";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, Integer.parseInt(oss.getMatricola()));
            ps.setString(2, oss.getOsservazione());
            ps.setString(3, oss.getSentiment());
            ps.executeUpdate();
        }
    }
    public static void addSecOsservazione(Connection connection, OsservazioneBean oss) throws SQLException{
        String sql = "INSERT INTO SecurityOsservazioni (Matricola, Osservazione, Sentiment)\n" +
                "    VALUES (?, ?, ?);";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, Integer.parseInt(oss.getMatricola()));
            ps.setString(2, oss.getOsservazione());
            ps.setString(3, oss.getSentiment());
            ps.executeUpdate();
        }
    }
    public static void addSalOsservazione(Connection connection, OsservazioneBean oss) throws SQLException{
        String sql = "INSERT INTO SalesOsservazioni (Matricola, Osservazione, Sentiment)\n" +
                "    VALUES (?, ?, ?);";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, Integer.parseInt(oss.getMatricola()));
            ps.setString(2, oss.getOsservazione());
            ps.setString(3, oss.getSentiment());
            ps.executeUpdate();
        }
    }
}
