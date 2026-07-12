package it.pietrociarmatori.controllerappl;

import it.pietrociarmatori.exceptions.DAOException;
import it.pietrociarmatori.exceptions.TaskException;
import it.pietrociarmatori.model.beans.CredentialsHRBean;
import it.pietrociarmatori.model.dao.PosizioniAperteDAOCSV;
import it.pietrociarmatori.model.dao.PosizioniAperteDAO;
import it.pietrociarmatori.model.dao.PosizioniAperteDAOJDBC;
import it.pietrociarmatori.model.beans.PosizioneBean;
import it.pietrociarmatori.model.beans.TabellaPosizioniAperteBean;
import it.pietrociarmatori.model.helpers.AuthService;
import it.pietrociarmatori.model.helpers.HROnly;
import it.pietrociarmatori.model.helpers.TabellaPosizioniAperteBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

// Questo controller espone un'interfaccia leggermente più ricca degli altri a causa della presenza
// di dei metodi wrappers, che assieme a degli helper permettono la scelta della strategia di
// persistenza per le posizioni aperte. Ciò dovrebbe avvenire a runtime, si può scegliere tra file
// system e db relazionale. I metodi che vengono wrappati (i primi 3) sono stati comunque mantenuti
// pubblici intenzionalmente. I Wrappers iniziano a circa riga 114 CAMBIARE RIGA SE SI EFFETTUANO MODIFICHE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
public class GestisciPosizioniAperteController {
    private PosizioniAperteDAO dao;
    private final String path = "resources/DAO.properties"; // consiglio sonarqube...strano

    @HROnly
    public TabellaPosizioniAperteBean getTabellaPosizioniAperte(CredentialsHRBean cred) throws TaskException {
        TabellaPosizioniAperteBuilder tpab = null;
        AuthService auth = null;
        TabellaPosizioniAperteBean tpa = null;
        try {
            auth = AuthService.getInstance();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            getDao();
            List<PosizioneBean> posizioni = dao.getPosizioniAperte();

            tpab = new TabellaPosizioniAperteBuilder(posizioni);
            tpab.buildTabella();
            tpa = tpab.getTabellaPosizioniAperte();
        }catch(DAOException e){
            throw new TaskException("Errore durante l'estrazione delle posizioni", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }catch(IOException e){
            // qui magari puoi fare l'handle della exception selezionando
            // una dao di default
            throw new TaskException("Impossibile selezionare modalità Database");
        }

        return tpa;
    }

    @HROnly
    public void addPosizioneAperta(CredentialsHRBean cred, PosizioneBean posizione) throws TaskException {
        AuthService auth = null;
        try {
            auth = AuthService.getInstance();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            getDao();
            dao.addPosizioneAperta(posizione);
        }catch(DAOException e){
            throw new TaskException("Errore durante la pubblicazione della posizione", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }catch(IOException e){
            // qui magari puoi fare l'handle della exception selezionando
            // una dao di default
            throw new TaskException("Impossibile selezionare modalità Database");
        }
    }

    @HROnly
    public void deletePosizioneAperta(CredentialsHRBean cred, PosizioneBean posizione) throws TaskException {
        AuthService auth = null;
        try {
            auth = AuthService.getInstance();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            getDao();
            dao.deletePosizioneAperta(posizione);
        }catch(DAOException e){
            throw new TaskException("Errore durante l'eliminazione della posizione", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }catch(IOException e){
            // qui magari puoi fare l'handle della exception selezionando
            // una dao di default
            throw new TaskException("Impossibile selezionare modalità Database");
        }
    }
    private void getDao() throws IOException {
        InputStream input = new FileInputStream(path);

        Properties properties = new Properties();
        properties.load(input);

        String opCode = properties.getProperty("MODE");
        if(opCode.equals("1")){
            dao = new PosizioniAperteDAOJDBC();
        } else if(opCode.equals("2")){
            dao = new PosizioniAperteDAOCSV();
        }
    }
    // "WRAPPERS"

    // HR può ottenere una lista delle posizioni aperte, l'opcode serve a selezionare la modalità di persistenza dei dati
    // 1 -> DB relazionale
    // 2 -> CSV
    @HROnly
    public TabellaPosizioniAperteBean getTabellaPosizioniAperte(CredentialsHRBean cred, String opCodeDAO) throws TaskException {
        AuthService auth = null;
        TabellaPosizioniAperteBean tpa = null;
        try{
            auth = AuthService.getInstance();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            tryChangeDB(opCodeDAO);
            tpa = getTabellaPosizioniAperte(cred);

        }catch(SecurityException e){
            throw new TaskException(e);
        }
        return tpa;
    }
    // HR può eliminare l'esistenza di una psizione aperta, l'opcode serve a selezionare il metodo di persistenza
    // 1 -> DB relazionale
    // 2 -> CSV
    @HROnly
    public void deletePosizioneAperta(CredentialsHRBean cred, PosizioneBean pos, String opCodeDAO) throws TaskException{
        AuthService auth = null;
        try{
            auth = AuthService.getInstance();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            tryChangeDB(opCodeDAO);
            deletePosizioneAperta(cred, pos);

        }catch(SecurityException e){
            throw new TaskException(e);
        }
    }

    // HR può pubblicare una posizione aperta, l'opcode serve a selezionare il metodo di persistenza
    // 1 -> DB relazionale
    // 2 -> CSV
    @HROnly
    public void addPosizioneAperta(CredentialsHRBean cred, PosizioneBean pos, String opCodeDAO) throws TaskException{
        AuthService auth = null;
        try{
            auth = AuthService.getInstance();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            tryChangeDB(opCodeDAO);
            addPosizioneAperta(cred, pos);

        }catch(SecurityException e){
            throw new TaskException(e);
        }
    }

    // switch della DAO e trasferimento dati
    private void tryChangeDB(String opCodeDAO) throws TaskException{
        try {
            PosizioniAperteDAO dao1 = null;
            PosizioniAperteDAO dao2  = null;

            InputStream input = new FileInputStream(path);

            Properties properties = new Properties();
            properties.load(input);

            String opCode = properties.getProperty("MODE");

            if(!(opCode.equals(opCodeDAO))){
                if(opCodeDAO.equals("1")){ // sposta da csv a jdbc
                    dao1 = new PosizioniAperteDAOCSV();
                    dao2 = new PosizioniAperteDAOJDBC();

                    properties.setProperty("MODE", "1");
                }else if(opCodeDAO.equals("2")){ // sposta da jdbc a csv
                    dao1 = new PosizioniAperteDAOJDBC();
                    dao2 = new PosizioniAperteDAOCSV();

                    properties.setProperty("MODE", "2");
                }else{
                    throw new TaskException("Opcode errato, scegliere tra 1 o 2");
                }

                FileOutputStream output = new FileOutputStream(path);
                properties.store(output,null);
                // sposta dati
                List<PosizioneBean> posizioni = dao1.getPosizioniAperte();

                for(PosizioneBean p: posizioni){
                    dao2.addPosizioneAperta(p);
                }

                for(PosizioneBean p: posizioni){
                    dao1.deletePosizioneAperta(p);
                }
            }
        }catch(IOException e){
            throw new TaskException("Impossibile selezionare modalità database");
        } catch (DAOException e) {
            throw new TaskException("impossibile spostare dati", e);
        }
    }
}