package it.pietrociarmatori.Model.DAO;

import it.pietrociarmatori.Exceptions.ConnectionPoolException;
import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.DAO.queries.CandidatiQueries;
import it.pietrociarmatori.Model.Helpers.ConnectionPoolInterface;
import it.pietrociarmatori.Model.Helpers.ConnectionPoolMYSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidatiDAO {
    private ConnectionPoolInterface connectionPool;
    private final String idoneo = "idoneo";
    private final String nonidoneo = "nonidoneo";
    private final String davalutare = "davalutare";

    public List<CandidatoBean> getCandidati(String idoneita) throws DAOException{
        List<CandidatoBean> data = new ArrayList<>(0);
        CandidatoBean cand;
        Connection connection;
        int type;
        ResultSet rs;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            switch(idoneita){
                case idoneo:
                    rs = CandidatiQueries.getCandidatiIdonei(connection);
                    type = 0;
                    break;
                case nonidoneo:
                    rs = CandidatiQueries.getCandidatiNonIdonei(connection);
                    type = 1;
                    break;
                case davalutare:
                    rs = CandidatiQueries.getCandidatiDaValutare(connection);
                    type = 2;
                    break;
                default:
                    throw new DAOException("Valore di idoneità non valido: " + idoneita);
            }

            while(rs.next()){
                cand = new CandidatoBean();
                cand.setNome(rs.getString(1));
                cand.setCognome(rs.getString(2));
                cand.setPosizione(rs.getString(3));
                cand.setRequisitiPosizione(rs.getString(4));
                cand.setSkillCandidato(rs.getString(5));
                cand.setIndirizzoMailCandidato(rs.getString(6));
                String risposta = rs.getString(7);
                if(risposta != null && !risposta.trim().isEmpty()){
                    cand.setMailDiRisposta(risposta);
                }else{
                    cand.setMailDiRisposta("Candidato attende risposta.");
                }
                cand.setNomeDipartimento(rs.getString(8));
                if(type == 0){
                    cand.setIdoneita("idoneo");
                }else if(type == 1){
                    cand.setIdoneita("nonidoneo");
                }else if(type == 2){
                    cand.setIdoneita("davalutare");
                }

                data.add(cand);
            }
        }catch(SQLException e) {

            throw new DAOException("Impossibile ottenere il candidato dal database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
        return data;
    }
    public void deleteCandidato(CandidatoBean candidato) throws DAOException{
        Connection connection;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            switch(candidato.getIdoneita()){
                case idoneo:
                    CandidatiQueries.eraseCandidatoIdoneo(connection, candidato);
                    break;
                case nonidoneo:
                    CandidatiQueries.eraseCandidatoNonIdoneo(connection, candidato);
                    break;
                case davalutare:
                    CandidatiQueries.eraseCandidatoDaValutare(connection, candidato);
                    break;
                default:
                    throw new DAOException("Valore di idoneità non valido: " + candidato.getIdoneita());
            }

        }catch(SQLException e) {

            throw new DAOException("Impossibile eliminare il candidato dal database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
    }
    public void addCandidato(CandidatoBean candidato) throws DAOException{
        Connection connection;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            switch(candidato.getIdoneita()){
                case idoneo:
                    CandidatiQueries.addCandidatoIdoneo(connection, candidato);
                    break;
                case nonidoneo:
                    CandidatiQueries.addCandidatoNonIdoneo(connection, candidato);
                    break;
                case davalutare:
                    CandidatiQueries.addCandidatoDaValutare(connection, candidato);
                    break;
                default:
                    throw new DAOException("Valore di idoneità non valido: " + candidato.getIdoneita());
            }

        }catch(SQLException e) {

            throw new DAOException("Impossibile aggiungere il candidato al database");
        }catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
    }
    public void addMailCandidato(CandidatoBean candidato) throws DAOException{
        Connection connection;

        try{
            connectionPool = ConnectionPoolMYSQL.getInstance();
            connection = connectionPool.getConnection();

            switch(candidato.getIdoneita()){
                case idoneo:
                    CandidatiQueries.aggiungiMailDiRispostaCandidatoIdoneo(connection, candidato);
                    break;
                case nonidoneo:
                    CandidatiQueries.aggiungiMailDiRispostaCandidatoNonIdoneo(connection, candidato);
                    break;
                case davalutare:
                    CandidatiQueries.aggiungiMailDiRispostaCandidatoDaValutare(connection, candidato);
                    break;
                default:
                    throw new DAOException("Valore di idoneità non valido: " + candidato.getIdoneita());
            }

        }catch(SQLException e) {

            throw new DAOException("Impossibile aggiungere mail verso candidato nel database");
        }
        catch(ConnectionPoolException e){

            throw new DAOException(e.getMessage());
        }
    }
}