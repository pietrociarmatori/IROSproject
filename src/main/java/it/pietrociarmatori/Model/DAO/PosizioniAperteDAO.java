package it.pietrociarmatori.Model.DAO;

import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.Beans.PosizioneBean;

import java.util.List;

public interface PosizioniAperteDAO {
    List<PosizioneBean> getPosizioniAperte() throws DAOException;
    void addPosizioneAperta(PosizioneBean pos) throws DAOException;
    void deletePosizioneAperta(PosizioneBean pos) throws DAOException;
    String getRequisiti(String pos) throws DAOException;
}
