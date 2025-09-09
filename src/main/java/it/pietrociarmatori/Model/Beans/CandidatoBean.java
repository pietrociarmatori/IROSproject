package it.pietrociarmatori.Model.Beans;

public class CandidatoBean {
    private String nome;
    private String cognome;
    private String posizione;
    private String requisitiPosizione;
    private String skillCandidato;
    private String indirizzoMailCandidato;
    private String mailDiRisposta;
    private String nomeDipartimento;
    private String idoneita;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPosizione() {
        return posizione;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public String getRequisitiPosizione() {
        return requisitiPosizione;
    }

    public void setRequisitiPosizione(String requisitiPosizione) {
        this.requisitiPosizione = requisitiPosizione;
    }

    public String getSkillCandidato() {
        return skillCandidato;
    }

    public void setSkillCandidato(String skillCandidato) {
        this.skillCandidato = skillCandidato;
    }

    public String getIndirizzoMailCandidato() {
        return indirizzoMailCandidato;
    }

    public void setIndirizzoMailCandidato(String indirizzoMailCandidato) {
        this.indirizzoMailCandidato = indirizzoMailCandidato;
    }

    public String getMailDiRisposta() {
        return mailDiRisposta;
    }

    public void setMailDiRisposta(String mailDiRisposta) {
        this.mailDiRisposta = mailDiRisposta;
    }

    public String getNomeDipartimento() {
        return nomeDipartimento;
    }

    public void setNomeDipartimento(String dipartimento) {
        this.nomeDipartimento = dipartimento;
    }

    public String getIdoneita() {
        return idoneita;
    }

    public void setIdoneita(String idoneita) {
        this.idoneita = controlCase(idoneita);
    }
    private String controlCase(String idoneita){
        idoneita = idoneita.toLowerCase().replaceAll("\\s+","");
        if(idoneita.equals("idoneo")){
            return "idoneo";
        }else if(idoneita.equals("nonidoneo")){
            return "nonidoneo";
        }else if(idoneita.equals("davalutare")){
            return "davalutare";
        }
        return null;
    }
}
