package it.pietrociarmatori.ControllerAppl;

import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.DAO.*;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Beans.TabellaCandidatiBean;
import it.pietrociarmatori.Model.Helpers.AuthService;
import it.pietrociarmatori.Model.Helpers.HROnly;
import it.pietrociarmatori.Model.Helpers.TabellaCandidatiBuilder;

import java.util.List;

public class GestisciCandidatiController {

    // Permette ad HR di ottenere una lista di candidati
    @HROnly
    public TabellaCandidatiBean getTabellaCandidati(CredentialsHRBean cred) throws SecurityException, TaskException {
        AuthService auth = null;
        CandidatiDAO dao = null;
        TabellaCandidatiBuilder tcb = null;
        List<CandidatoBean> candidati = null;
        TabellaCandidatiBean tc = null;
        try {
            auth = new AuthService();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            //ottieni tutti i candidati
            dao = new CandidatiDAO();
            candidati = dao.getCandidati("idoneo");

            candidati.addAll(dao.getCandidati("nonidoneo"));

            candidati.addAll(dao.getCandidati("davalutare"));

            tcb = new TabellaCandidatiBuilder(candidati);
            tcb.buildTabella();

            tc = tcb.getTabellaCandidati();
        }catch(DAOException e){
            throw new TaskException("Errore durante la pubblicazione dell'osservazione", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }
       return tc;
    }

    // Permette ad HR di cancellare una candidatura
    @HROnly
    public void deleteCandidato(CredentialsHRBean cred, CandidatoBean candidato) throws TaskException {
        AuthService auth = null;
        CandidatiDAO dao = null;
        try {
            auth = new AuthService();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            dao = new CandidatiDAO();
            dao.deleteCandidato(candidato);
        }catch(DAOException e){
            throw new TaskException("Errore durante la pubblicazione dell'osservazione", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }
    }

    // Permette ad HR di cambiare il livello di idoneità di un candidato in quanto l'LLM potrebbe aver
    // effettuato un giudizio errato
    @HROnly
    public void spostaCandidato(CredentialsHRBean cred, CandidatoBean candidato, String newBucket) throws TaskException {
        AuthService auth = null;
        CandidatiDAO dao = null;
        try {
            auth = new AuthService();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            dao = new CandidatiDAO();

            String oldBucket = candidato.getIdoneita();
            newBucket = newBucket.toLowerCase().replaceAll("\\s+", "");
            candidato.setIdoneita(newBucket);

            if (!(newBucket.equals("idoneo") || newBucket.equals("nonidoneo") || newBucket.equals("davalutare"))) {
                throw new IllegalArgumentException("Valore di idoneità non valido: " + newBucket);
            }

            dao.addCandidato(candidato);

            candidato.setIdoneita(oldBucket);
            dao.deleteCandidato(candidato);
        }catch(DAOException e){
            throw new TaskException("Errore durante la pubblicazione dell'osservazione", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }
    }
}
