package it.pietrociarmatori.Model.DAO.queries;
import it.pietrociarmatori.Model.Beans.CandidatoBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Utilizzando PreparedStatement mitigo sql injection
public class CandidatiQueries {

    public static void addCandidatoIdoneo(Connection connection, CandidatoBean candidato) throws SQLException {
        String sql = "INSERT INTO CandidatiIdonei (Nome, Cognome, Posizione, RequisitiPosizione, SkillCandidato, IndirizzoMailCandidato) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidato.getNome());
            ps.setString(2, candidato.getCognome());
            ps.setString(3, candidato.getPosizione());
            ps.setString(4, candidato.getRequisitiPosizione());
            ps.setString(5, candidato.getSkillCandidato());
            ps.setString(6, candidato.getIndirizzoMailCandidato());

            ps.executeUpdate();
        }
    }
    public static void addCandidatoNonIdoneo(Connection connection, CandidatoBean candidato) throws SQLException {
        String sql = "INSERT INTO CandidatiNonIdonei (Nome, Cognome, Posizione, RequisitiPosizione, SkillCandidato, IndirizzoMailCandidato) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidato.getNome());
            ps.setString(2, candidato.getCognome());
            ps.setString(3, candidato.getPosizione());
            ps.setString(4, candidato.getRequisitiPosizione());
            ps.setString(5, candidato.getSkillCandidato());
            ps.setString(6, candidato.getIndirizzoMailCandidato());

            ps.executeUpdate();
        }
    }
    public static void addCandidatoDaValutare(Connection connection, CandidatoBean candidato) throws SQLException {
        String sql = "INSERT INTO CandidatiDaValutare (Nome, Cognome, Posizione, RequisitiPosizione, SkillCandidato, IndirizzoMailCandidato) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidato.getNome());
            ps.setString(2, candidato.getCognome());
            ps.setString(3, candidato.getPosizione());
            ps.setString(4, candidato.getRequisitiPosizione());
            ps.setString(5, candidato.getSkillCandidato());
            ps.setString(6, candidato.getIndirizzoMailCandidato());

            ps.executeUpdate();
        }
    }
    public static void aggiungiMailDiRispostaCandidatoIdoneo(Connection connection, CandidatoBean candidato) throws SQLException{
        String sql = "UPDATE CandidatiIdonei SET MailDiRisposta = ? WHERE IndirizzoMailCandidato = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidato.getMailDiRisposta());
            ps.setString(2, candidato.getIndirizzoMailCandidato());

            ps.executeUpdate();
        }
    }
    public static void aggiungiMailDiRispostaCandidatoNonIdoneo(Connection connection, CandidatoBean candidato) throws SQLException{
        String sql = "UPDATE CandidatiNonIdonei SET MailDiRisposta = ? WHERE IndirizzoMailCandidato = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidato.getMailDiRisposta());
            ps.setString(2, candidato.getIndirizzoMailCandidato());

            ps.executeUpdate();
        }
    }
    public static void aggiungiMailDiRispostaCandidatoDaValutare(Connection connection, CandidatoBean candidato) throws SQLException{
        String sql = "UPDATE CandidatiDaValutare SET MailDiRisposta = ? WHERE IndirizzoMailCandidato = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidato.getMailDiRisposta());
            ps.setString(2, candidato.getIndirizzoMailCandidato());

            ps.executeUpdate();
        }
    }
    public static void eraseCandidatoIdoneo(Connection connection, CandidatoBean candidato) throws SQLException{
        String sql = "DELETE FROM CandidatiIdonei WHERE IndirizzoMailCandidato = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidato.getIndirizzoMailCandidato());

            ps.executeUpdate();
        }
    }
    public static void eraseCandidatoNonIdoneo(Connection connection, CandidatoBean candidato) throws SQLException{
        String sql = "DELETE FROM CandidatiNonIdonei WHERE IndirizzoMailCandidato = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidato.getIndirizzoMailCandidato());

            ps.executeUpdate();
        }
    }
    public static void eraseCandidatoDaValutare(Connection connection, CandidatoBean candidato) throws SQLException{
        String sql = "DELETE FROM CandidatiDaValutare WHERE IndirizzoMailCandidato = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidato.getIndirizzoMailCandidato());

            ps.executeUpdate();
        }
    }
    public static ResultSet getCandidatiIdonei(Connection connection) throws SQLException{
        String sql = "select \n" +
                "o.nome,\n" +
                "o.cognome,\n" +
                "o.posizione,\n" +
                "o.RequisitiPosizione,\n" +
                "o.skillcandidato,\n" +
                "o.IndirizzoMailCandidato,\n" +
                "o.MailDiRisposta,\n" +
                "p.dipartimento\n" +
                "from candidatiidonei o join posizioniaperte p\n" +
                "on o.posizione = p.NomePosizione;";

        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet getCandidatiNonIdonei(Connection connection) throws SQLException{
        String sql = "select \n" +
                "o.nome,\n" +
                "o.cognome,\n" +
                "o.posizione,\n" +
                "o.RequisitiPosizione,\n" +
                "o.skillcandidato,\n" +
                "o.IndirizzoMailCandidato,\n" +
                "o.MailDiRisposta,\n" +
                "p.dipartimento\n" +
                "from candidatinonidonei o join posizioniaperte p\n" +
                "on o.posizione = p.NomePosizione;";

        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet getCandidatiDaValutare(Connection connection) throws SQLException{
        String sql = "select \n" +
                "o.nome,\n" +
                "o.cognome,\n" +
                "o.posizione,\n" +
                "o.RequisitiPosizione,\n" +
                "o.skillcandidato,\n" +
                "o.IndirizzoMailCandidato,\n" +
                "o.MailDiRisposta,\n" +
                "p.dipartimento\n" +
                "from candidatidavalutare o join posizioniaperte p\n" +
                "on o.posizione = p.NomePosizione;";

        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }
}
