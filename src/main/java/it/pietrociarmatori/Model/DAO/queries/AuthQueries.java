package it.pietrociarmatori.Model.DAO.queries;

import it.pietrociarmatori.Model.Beans.CredentialsEmployeeBean;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Utilizzando PreparedStatement mitigo sql injection
public class AuthQueries {
    public static ResultSet getDipendente(Connection connection, CredentialsEmployeeBean cred) throws SQLException{
        String sql = "SELECT * FROM Dipendenti WHERE Matricola = ? AND Passwd = ?;";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cred.getMatricola());
        ps.setString(2, cred.getPassword());

        return ps.executeQuery();
    }
    public static ResultSet getHR(Connection connection, CredentialsHRBean cred) throws SQLException{
        String sql = "SELECT * FROM Dipendenti WHERE Matricola = ? AND Passwd = ?;";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cred.getMatricola());
        ps.setString(2, cred.getPassword());

        return ps.executeQuery();
    }
}
