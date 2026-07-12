package it.pietrociarmatori.controllerappl;

import it.pietrociarmatori.exceptions.DAOException;
import it.pietrociarmatori.exceptions.IAServiceException;
import it.pietrociarmatori.exceptions.TaskException;
import it.pietrociarmatori.model.beans.CredentialsEmployeeBean;
import it.pietrociarmatori.model.dao.OsservazioniDAO;
import it.pietrociarmatori.model.dao.OsservazioniDipartimentoOverviewDAO;
import it.pietrociarmatori.model.beans.OsservazioneBean;
import it.pietrociarmatori.model.helpers.AuthService;
import it.pietrociarmatori.model.helpers.EmployeeOnly;
import it.pietrociarmatori.model.helpers.ia.HuggingFaceClient;
import it.pietrociarmatori.model.helpers.ia.IASentiment;
import it.pietrociarmatori.model.helpers.ia.IASummary;
import it.pietrociarmatori.model.helpers.Params;

import java.util.ArrayList;
import java.util.List;

public class AggiungiOsservazioneController {

    // I dipendenti non HR possono pubblicare una loro osservazione sul dipartimento in cui lavorano
    // viene calcolato il sentiment della singola osservazione e questo poi viene usato per ribilanciare
    // il sentiment generale del dipartimento a cui l'osservazione fa riferimento
    @EmployeeOnly
    public void aggiungiOsservazione(CredentialsEmployeeBean cred, OsservazioneBean oss) throws TaskException {
        AuthService auth = null;
        HuggingFaceClient hc = null;
        OsservazioniDAO dao = null;

        try {
            auth = AuthService.getInstance();
            if (!auth.isDipendente(cred)) {
                throw new SecurityException();
            }

            Params param = new Params();
            List<String> list = new ArrayList<>(0);
            list.add(oss.getOsservazione());
            param.setParams(list);

            hc = new HuggingFaceClient(param);
            hc.setStrategy(new IASentiment());
            hc.executeIAService();
            oss.setSentiment(hc.getResultIA().get("sentiment"));

            oss.setMatricola(cred.getMatricola());
            oss.setDipartimento(cred.getDipartimento());

            dao = new OsservazioniDAO();
            dao.addOsservazione(oss);

            aggiornaSituazioneGeneraleDipartimento(oss.getDipartimento(), dao, hc);
        }catch(DAOException | IAServiceException e){
            throw new TaskException("Errore durante la pubblicazione dell'osservazione", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }
    }

    private void aggiornaSituazioneGeneraleDipartimento(String dipartimento, OsservazioniDAO dao, HuggingFaceClient hc) throws DAOException, IAServiceException {
        OsservazioniDipartimentoOverviewDAO daoOverview = null;
        Params param = new Params();
        List<OsservazioneBean> osservazioniDipartimento = dao.getOsservazioni(dipartimento);

        List<String> list = new ArrayList<>(0);
        for(OsservazioneBean oss: osservazioniDipartimento)
            list.add(oss.getOsservazione());

        param.setParams(list);

        hc.setParams(param);
        hc.setStrategy(new IASummary());
        hc.executeIAService();
        String riassunto = hc.getResultIA().get("riassunto");

        list.clear();
        list.add(riassunto);
        param.setParams(list);

        hc.setParams(param);
        hc.setStrategy(new IASentiment());
        hc.executeIAService();
        String sentimentRiassunto = hc.getResultIA().get("sentiment");

        daoOverview = new OsservazioniDipartimentoOverviewDAO();
        daoOverview.aggiungiOverviewDipartimento(dipartimento, riassunto, sentimentRiassunto);
    }
}
