package it.pietrociarmatori.model.dao;

import it.pietrociarmatori.exceptions.DAOException;
import it.pietrociarmatori.model.beans.PosizioneBean;

import java.util.List;

public interface PosizioniAperteDAO {
    List<PosizioneBean> getPosizioniAperte() throws DAOException;
    void addPosizioneAperta(PosizioneBean pos) throws DAOException;
    void deletePosizioneAperta(PosizioneBean pos) throws DAOException;
    String getRequisiti(String pos) throws DAOException;
}
