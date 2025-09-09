package it.pietrociarmatori.Model.DAO;

import it.pietrociarmatori.Exceptions.DAOException;
import it.pietrociarmatori.Model.Beans.PosizioneBean;

import java.util.*;

import java.io.*;

public class PosizioniAperteDAOCSV implements PosizioniAperteDAO {
    private static final String CSV_FILE = "src/main/localDBFilePosizioniAperte.csv";
    private static final String[] VALID_DIPARTIMENTI = {
            "SoftwareDevelopment", "DataEngineering", "Security", "Sales", "HumanResources"
    };

    private boolean isDipartimentoValido(String dip) {
        return Arrays.asList(VALID_DIPARTIMENTI).contains(dip);
    }

    public List<PosizioneBean> getPosizioniAperte() throws DAOException {
        List<PosizioneBean> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                if (skipHeader) { skipHeader = false; continue; }
                String[] campi = line.split(",", 3); // 3 campi: dip, nome, req
                if (campi.length != 3) continue;

                PosizioneBean pos = new PosizioneBean();
                pos.setDipartimento(campi[0]);
                pos.setNomePosizione(campi[1]);
                pos.setRequisiti(campi[2]);

                lista.add(pos);
            }
        } catch (IOException e) {
            throw new DAOException("Errore durante la lettura dal file CSV", e);
        }

        return lista;
    }

    public void addPosizioneAperta(PosizioneBean pos) throws DAOException {
        if (!isDipartimentoValido(pos.getDipartimento())) {
            throw new DAOException("Dipartimento non valido: " + pos.getDipartimento());
        }

        List<PosizioneBean> lista = getPosizioniAperte();

        // Controlla che il nome posizione non esista già
        for (PosizioneBean p : lista) {
            if (p.getNomePosizione().equals(pos.getNomePosizione())) {
                throw new DAOException("Posizione già esistente: " + pos.getNomePosizione());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            // Se il file è vuoto, scrivi l'intestazione
            File file = new File(CSV_FILE);
            if (file.length() == 0) {
                writer.write("Dipartimento,NomePosizione,Requisiti");
                writer.newLine();
            }

            writer.write(pos.getDipartimento() + "," +
                    pos.getNomePosizione() + "," +
                    pos.getRequisiti().replace("\n", " ").replace("\r", " "));
            writer.newLine();

        } catch (IOException e) {
            throw new DAOException("Errore durante la scrittura sul file CSV", e);
        }
    }

    public void deletePosizioneAperta(PosizioneBean pos) throws DAOException {
        List<PosizioneBean> lista = getPosizioniAperte();
        boolean removed = lista.removeIf(p -> p.getNomePosizione().equals(pos.getNomePosizione()));

        if (!removed) {
            throw new DAOException("Posizione non trovata: " + pos.getNomePosizione());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            writer.write("Dipartimento,NomePosizione,Requisiti");
            writer.newLine();
            for (PosizioneBean p : lista) {
                writer.write(p.getDipartimento() + "," +
                        p.getNomePosizione() + "," +
                        p.getRequisiti().replace("\n", " ").replace("\r", " "));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DAOException("Errore durante la scrittura sul file CSV", e);
        }
    }

    public String getRequisiti(String nomePosizione) throws DAOException {
        List<PosizioneBean> lista = getPosizioniAperte();

        for (PosizioneBean p : lista) {
            if (p.getNomePosizione().equals(nomePosizione)) {
                return p.getRequisiti();
            }
        }

        throw new DAOException("Posizione non trovata: " + nomePosizione);
    }
}
