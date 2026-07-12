package it.pietrociarmatori.controllerappl;

import it.pietrociarmatori.exceptions.DAOException;
import it.pietrociarmatori.exceptions.TaskException;
import it.pietrociarmatori.model.beans.CredentialsHRBean;
import it.pietrociarmatori.model.dao.OsservazioniDipartimentoOverviewDAO;
import it.pietrociarmatori.model.beans.ProvvedimentoBean;
import it.pietrociarmatori.model.helpers.AuthService;
import it.pietrociarmatori.model.helpers.HROnly;

public class AggiungiProvvedimentoController {

    // Un'azione di tipo CRUD che permette ad HR di pubblicare i provvedimenti presi per soddisfare
    // le richieste dei dipendenti comunicate tramite osservazioni sul portale
    @HROnly
    public void addProvvedimento(CredentialsHRBean cred, ProvvedimentoBean provv) throws TaskException {
        AuthService auth = null;
        OsservazioniDipartimentoOverviewDAO dao = null;
        try {
            auth = AuthService.getInstance();
            if (!auth.isHR(cred)) {
                throw new SecurityException();
            }

            dao = new OsservazioniDipartimentoOverviewDAO();
            dao.addProvvedimento(provv);
        }catch(DAOException e){
            throw new TaskException("Errore durante la pubblicazione dell'osservazione", e);
        }catch(SecurityException e){
            throw new TaskException(e);
        }
    }
}
