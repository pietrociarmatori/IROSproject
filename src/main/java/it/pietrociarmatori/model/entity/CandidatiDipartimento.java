package it.pietrociarmatori.model.entity;

import it.pietrociarmatori.model.beans.CandidatoBean;

import java.util.List;

public class CandidatiDipartimento{
    private String nomeDipartimento;
    private List<CandidatoBean> listaCandidati;

    public List<CandidatoBean> getListaCandidati() {
        return listaCandidati;
    }

    public void setListaCandidati(List<CandidatoBean> listaCandidati) {
        this.listaCandidati = listaCandidati;
    }

    public String getNomeDipartimento() {
        return nomeDipartimento;
    }

    public void setNomeDipartimento(String nomeDipartimento) {
        this.nomeDipartimento = nomeDipartimento;
    }
}
