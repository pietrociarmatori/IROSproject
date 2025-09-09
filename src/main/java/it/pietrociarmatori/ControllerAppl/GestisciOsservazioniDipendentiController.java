package it.pietrociarmatori.ControllerAppl;

import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.DAO.*;
import it.pietrociarmatori.Model.Entity.DipartimentoSentimentOverview;
import it.pietrociarmatori.Model.Beans.OsservazioneBean;
import it.pietrociarmatori.Model.Beans.TabellaOsservazioniBean;
import it.pietrociarmatori.Model.Helpers.AuthService;
import it.pietrociarmatori.Model.Helpers.HROnly;
import it.pietrociarmatori.Model.Helpers.TabellaOsservazioniBuilder;

import java.util.List;

public class GestisciOsservazioniDipendentiController {
    // serve ad HR per ottenere tutte le osservazioni fatte dai dipendenti
    @HROnly
    public TabellaOsservazioniBean getTabellaOsservazioni(CredentialsHRBean cred) throws TaskException {
        TabellaOsservazioniBuilder tob = null;
        AuthService auth = null;
        OsservazioniDAO dao = null;
        List<OsservazioneBean> osservazioni = null;
        TabellaOsservazioniBean to = null;
        try {
            auth = new AuthService();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            // ottieni tutte le osservazioni
            dao = new OsservazioniDAO();
            osservazioni = dao.getOsservazioni("DataEngineering");

            osservazioni.addAll(dao.getOsservazioni("Sales"));

            osservazioni.addAll(dao.getOsservazioni("Security"));

            osservazioni.addAll(dao.getOsservazioni("SoftwareDevelopment"));

            tob = new TabellaOsservazioniBuilder(osservazioni);
            tob.buildTabella();

            to = tob.getTabellaOsservazioni();
            to = setDipartimentiOverview(to);
        }catch(DAOException e){
            throw new TaskException("Errore durante l'estrazione delle osservazione", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }
        return to;
    }

    // Hr può eliminare le osservazioni quando il provvedimento è stato preso
    @HROnly
    public void deleteOsservazione(CredentialsHRBean cred, OsservazioneBean oss) throws TaskException {
        AuthService auth = null;
        OsservazioniDAO dao = null;
        try {
            auth = new AuthService();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            dao = new OsservazioniDAO();

            dao.deleteOsservazione(oss);
        }catch(DAOException e){
            throw new TaskException("Errore durante l'eliminazione dell'osservazione", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }
    }

    private TabellaOsservazioniBean setDipartimentiOverview(TabellaOsservazioniBean to) throws DAOException {

        OsservazioniDipartimentoOverviewDAO daoOverview = null;

        daoOverview = new OsservazioniDipartimentoOverviewDAO();
        List<DipartimentoSentimentOverview> dips = daoOverview.getDipartimentiOverview();

        for (DipartimentoSentimentOverview dso : dips) {
            to.getTabella().get(dso.getDipartimento()).setRiassuntoOsservazioni(dso.getRiassuntoOsservazioni());
            to.getTabella().get(dso.getDipartimento()).setProvvedimenti(dso.getProvvedimenti());
            to.getTabella().get(dso.getDipartimento()).setSentimentGenerale(dso.getSentimentGenerale());

        }
        return to;
    }
}
