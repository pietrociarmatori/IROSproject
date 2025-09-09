package it.pietrociarmatori.ControllerAppl;

import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.DAO.OsservazioniDipartimentoOverviewDAO;
import it.pietrociarmatori.Model.Beans.ProvvedimentoBean;
import it.pietrociarmatori.Model.Helpers.AuthService;
import it.pietrociarmatori.Model.Helpers.HROnly;

public class AggiungiProvvedimentoController {

    // Un'azione di tipo CRUD che permette ad HR di pubblicare i provvedimenti presi per soddisfare
    // le richieste dei dipendenti comunicate tramite osservazioni sul portale
    @HROnly
    public void addProvvedimento(CredentialsHRBean cred, ProvvedimentoBean provv) throws TaskException {
        AuthService auth = null;
        OsservazioniDipartimentoOverviewDAO dao = null;
        try {
            auth = new AuthService();
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
