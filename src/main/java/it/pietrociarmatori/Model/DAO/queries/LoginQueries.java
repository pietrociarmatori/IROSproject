package it.pietrociarmatori.Model.DAO.queries;

import it.pietrociarmatori.Model.Beans.LoginCredentialsBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Utilizzando PreparedStatement mitigo sql injection
public class LoginQueries {
    public static ResultSet login(Connection connection, LoginCredentialsBean lcb) throws SQLException{
        String sql = "SELECT * FROM Dipendenti WHERE Matricola = ? AND Passwd = ?;";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, lcb.getMatricola());
        ps.setString(2, lcb.getPassword());

        return ps.executeQuery();
    }
}
