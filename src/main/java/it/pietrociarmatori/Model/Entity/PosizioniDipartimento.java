package it.pietrociarmatori.Model.Entity;

import it.pietrociarmatori.Model.Beans.PosizioneBean;

import java.util.List;

public class PosizioniDipartimento{
    private String nomeDipartimento;
    private List<PosizioneBean> listaPosizioni;

    public String getNomeDipartimento() {
        return nomeDipartimento;
    }

    public void setNomeDipartimento(String nomeDipartimento) {
        this.nomeDipartimento = nomeDipartimento;
    }
    public List<PosizioneBean> getListaPosizioni() {
        return listaPosizioni;
    }

    public void setListaPosizioni(List<PosizioneBean> listaPosizioni) {
        this.listaPosizioni = listaPosizioni;
    }
}
