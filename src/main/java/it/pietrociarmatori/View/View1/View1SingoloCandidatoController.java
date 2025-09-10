package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.ControllerAppl.GeneraMailDiRispostaController;
import it.pietrociarmatori.ControllerAppl.GestisciCandidatiController;
import it.pietrociarmatori.ControllerAppl.InviaEmailController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Beans.EmailBean;
import it.pietrociarmatori.View.SessionHR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void handleBackButton(ActionEvent event) {
        // carica la pagina dei candidati
        App.getSceneManager().switchTo("candidati", "/Fxml/HRGruppoCandidati.fxml", sessionHR);
    }

    public void handleLogoButton(ActionEvent event) {
        // va alla home
        App.getSceneManager().switchTo("home", "/Fxml/HRhome.fxml", sessionHR);
    }

    // Comune a tutte le schermate con l'icona del profilo
    public void handleProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ProfileInfo.fxml"));
            Parent sidePanel = loader.load();

            // Position it: (bigWidth - smallWidth)
            double bigWidth = 1200;
            double smallWidth = 350; // match prefWidth in FXML
            sidePanel.setLayoutX(bigWidth - smallWidth);
            sidePanel.setLayoutY(0);


            // Give sidePanel access to parent for closing
            View1ProfileDataController controller = loader.getController();
            controller.setParent(Root, sidePanel);
            controller.setData(this.sessionHR);

            Root.getChildren().add(sidePanel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleGenera(ActionEvent event) {
        try{
            GeneraMailDiRispostaController gmdr = new GeneraMailDiRispostaController();
            EmailBean mail = gmdr.generaRisposta(sessionHR.getCred(), cb);

            TextMail.clear();
            TextMail.setWrapText(true);
            TextMail.setText(mail.getEmail());
        } catch (TaskException e) {
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }

    public void handleElimina(ActionEvent event) {
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

    public void handleInvia(ActionEvent event) {
        try {
            if (TextMail.getText().isEmpty()) {
                NotifyBackendLogs nbe = new NotifyBackendLogs();
                nbe.notifyError("Inserire una mail prima di premere invio.");
                return;
            }
            EmailBean email = new EmailBean();
            email.setEmail(TextMail.getText());
            InviaEmailController iec = new InviaEmailController();
            iec.inviaMail(sessionHR.getCred(), cb, email);

            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Mail inviata!");

        } catch (TaskException e) {
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }

    public void handleSposta(ActionEvent event) {
        // da definire il comportamento, comunque penso che farà comparire una tendina
        // di tre bottoni, e il click di ognuno di questi fa partire il comando applicativo
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
