package it.pietrociarmatori.view.view1;

import it.pietrociarmatori.controllerappl.GestisciCandidatiController;
import it.pietrociarmatori.controllerappl.RispondiAlCandidatoController;
import it.pietrociarmatori.exceptions.TaskException;
import it.pietrociarmatori.model.beans.CandidatoBean;
import it.pietrociarmatori.model.beans.EmailBean;
import it.pietrociarmatori.view.SessionHR;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class View1SingoloCandidatoController implements ControlledScreen{
    @FXML
    private AnchorPane Root;
    @FXML
    private Label NomeLabel;
    @FXML
    private Label IdoneitaLabel;
    @FXML
    private Label DipartimentoLabel;
    @FXML
    private Label PosizioneLabel;
    @FXML
    private Label RequisitiLabel;
    @FXML
    private Label SkillLabel;
    @FXML
    private Label MailLabel;
    @FXML
    private Label CognomeLabel;
    @FXML
    private Button BackButton;
    @FXML
    private Button LogoButton;
    @FXML
    private Button ProfileButton;
    @FXML
    private TextArea TextMail;
    @FXML
    private Button GeneraButton;
    @FXML
    private Button EliminaButton;
    @FXML
    private Button SpostaButton;
    @FXML
    private Button InviaButton;
    @FXML
    private VBox ButtonsHolder;
    private SessionHR sessionHR;
    private CandidatoBean cb;
    @FXML
    private Button Idoneo;
    @FXML
    private Button NonIdoneo;
    @FXML
    private Button DaValutare;

    public void handleBackButton() {
        // carica la pagina dei candidati
        App.getSceneManager().switchTo("candidati", "/Fxml/HRGruppoCandidati.fxml", sessionHR);
    }

    public void handleLogoButton() {
        // va alla home
        App.getSceneManager().switchTo("home", "/Fxml/HRhome.fxml", sessionHR);
    }

    // Comune a tutte le schermate con l'icona del profilo
    public void handleProfile() {
        ProfileButtonRenderer pbr = new ProfileButtonRenderer();
        pbr.renderProfile(this.Root, this.sessionHR);
    }
    // extends
    public void handleGenera() {
        try{
            RispondiAlCandidatoController racc = new RispondiAlCandidatoController();
            EmailBean mail = new EmailBean();
            mail.setEmail(null);
            racc.inviaMail(sessionHR.getCred(), cb, mail);

            TextMail.clear();
            TextMail.setWrapText(true);

            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Mail generata e inviata!");
        } catch (TaskException e) {
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }

    public void handleElimina() {
        try {
            GestisciCandidatiController gcc = new GestisciCandidatiController();
            gcc.deleteCandidato(sessionHR.getCred(), cb);

            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Candidato eliminato!");
        } catch (TaskException e) {
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }

    public void handleInvia() {
        try {
            if (TextMail.getText().isEmpty()) {
                NotifyBackendLogs nbe = new NotifyBackendLogs();
                nbe.notifyError("Inserire una mail prima di premere invio.");
                return;
            }
            EmailBean email = new EmailBean();
            email.setEmail(TextMail.getText());
            RispondiAlCandidatoController iec = new RispondiAlCandidatoController();
            iec.inviaMail(sessionHR.getCred(), cb, email);

            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Mail inviata!");

        } catch (TaskException e) {
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }

    public void handleSposta() {

        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/Fxml/BottoneIdoneo.fxml"));
            Idoneo = loader1.load();
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Fxml/BottoneNonIdoneo.fxml"));
            NonIdoneo = loader2.load();
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/Fxml/BottoneDaValutare.fxml"));
            DaValutare = loader3.load();

            View1ButtonIdoneoController controller1 = loader1.getController();
            View1ButtonNonIdoneoController controller2 = loader2.getController();
            View1ButtonDaValutareController controller3 = loader3.getController();

            controller1.setParentController(this);
            controller2.setParentController(this);
            controller3.setParentController(this);

            ButtonsHolder.getChildren().add(Idoneo);
            ButtonsHolder.getChildren().add(NonIdoneo);
            ButtonsHolder.getChildren().add(DaValutare);

        } catch (IOException e) {
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }

    @Override
    public void onShow(Object data) {
        sessionHR = (SessionHR) data;

        NomeLabel.setText("Nome: "+cb.getNome());
        CognomeLabel.setText("Cognome: "+cb.getCognome());
        IdoneitaLabel.setText("Idoneità: "+cb.getIdoneita());
        DipartimentoLabel.setText("Dipartimento: "+cb.getNomeDipartimento());
        PosizioneLabel.setText("Posizione: "+cb.getPosizione());
        RequisitiLabel.setText("Requisiti: "+cb.getRequisitiPosizione());
        SkillLabel.setText("Skill: "+cb.getSkillCandidato());
        MailLabel.setText("Mail: "+cb.getIndirizzoMailCandidato());
    }
    public void setCandidato(CandidatoBean candidato){
        this.cb = candidato;
    }

    public void spostaInIdoneo(){
        try{
            if(cb.getIdoneita().equals("Idoneo")){
                NotifyBackendLogs nbl = new NotifyBackendLogs();
                nbl.notifyError("Il candidato è già idoneo!");
                return;
            }
            GestisciCandidatiController gcc = new GestisciCandidatiController();
            gcc.spostaCandidato(sessionHR.getCred(), cb, "Idoneo");
            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Candidato spostato!");

            ButtonsHolder.getChildren().clear();

        } catch (TaskException e) {
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError("Impossibile spostare candidato");
        }
    }
    public void spostaInNonIdoneo(){
        try{
            if(cb.getIdoneita().equals("NonIdoneo")){
                NotifyBackendLogs nbl = new NotifyBackendLogs();
                nbl.notifyError("Il candidato è già non idoneo!");
                return;
            }
            GestisciCandidatiController gcc = new GestisciCandidatiController();
            gcc.spostaCandidato(sessionHR.getCred(), cb, "NonIdoneo");
            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Candidato spostato!");

            ButtonsHolder.getChildren().clear();

        } catch (TaskException e) {
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError("Impossibile spostare candidato");
        }
    }
    public void spostaInDaValutare(){
        try{
            if(cb.getIdoneita().equals("DaValutare")){
                NotifyBackendLogs nbl = new NotifyBackendLogs();
                nbl.notifyError("Il candidato è già da valutare!");
                return;
            }
            GestisciCandidatiController gcc = new GestisciCandidatiController();
            gcc.spostaCandidato(sessionHR.getCred(), cb, "DaValutare");
            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Candidato spostato!");

            ButtonsHolder.getChildren().clear();

        } catch (TaskException e) {
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError("Impossibile spostare candidato");
        }
    }
}
