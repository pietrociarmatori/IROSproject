package it.pietrociarmatori.Model.Entity;

import it.pietrociarmatori.Model.Beans.CandidatoBean;

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
