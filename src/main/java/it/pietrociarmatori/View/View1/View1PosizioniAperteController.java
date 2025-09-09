package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.ControllerAppl.GestisciPosizioniAperteController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.PosizioneBean;
import it.pietrociarmatori.Model.Beans.TabellaPosizioniAperteBean;
import it.pietrociarmatori.Model.Entity.PosizioniDipartimento;
import it.pietrociarmatori.View.SessionHR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class View1PosizioniAperteController implements ControlledScreen{
    @FXML
    private Button SoftwareDevButton;
    @FXML
    private Button DataEngButton;
    @FXML
    private Button SecurityButton;
    @FXML
    private Button SalesButton;
    @FXML
    private Button BackButton;
    @FXML
    private ImageView Imagelogo;
    @FXML
    private Button LogoButton;
    @FXML
    private Button ProfileButton;
    @FXML
    private Button PubblicaPosizioneButton;
    @FXML
    private TextField DipartimentoText;
    @FXML
    private TextField NomePosizioneText;
    @FXML
    private TextField RequisitiText;
    @FXML
    private AnchorPane Root;
    @FXML
    private Button SwapButton;
    @FXML
    private VBox PosizioniContainer;
    public SessionHR sessionHR;
    public TabellaPosizioniAperteBean tpa;
    private String opcode = "1";

    public String getopcode(){
        return this.opcode;
    }

    public void handleSoftwareDevButton(ActionEvent action){
        PosizioniContainer.getChildren().clear();

        Map<String, PosizioniDipartimento> tabella = tpa.getTabella();
        PosizioniDipartimento pd = tabella.get("SoftwareDevelopment");
        List<PosizioneBean> listaPosizioni = pd.getListaPosizioni();

        for(PosizioneBean posizione : listaPosizioni){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SingolaPosizione.fxml"));
                Node element = loader.load();

                View1SingolaPosizioneController controller = loader.getController();
                controller.setParent(PosizioniContainer, element);
                controller.setPosizione(posizione, sessionHR);
                controller.setReference(this);
                controller.showPosizione();

                // fai l'append al VBox
                PosizioniContainer.getChildren().add(element);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleDataEngButton(ActionEvent event) {
        PosizioniContainer.getChildren().clear();

        Map<String, PosizioniDipartimento> tabella = tpa.getTabella();
        PosizioniDipartimento pd = tabella.get("DataEngineering");
        List<PosizioneBean> listaPosizioni = pd.getListaPosizioni();

        for(PosizioneBean posizione : listaPosizioni){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SingolaPosizione.fxml"));
                Node element = loader.load();

                View1SingolaPosizioneController controller = loader.getController();
                controller.setParent(PosizioniContainer, element);
                controller.setPosizione(posizione, sessionHR);
                controller.setReference(this);
                controller.showPosizione();

                // fai l'append al VBox
                PosizioniContainer.getChildren().add(element);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleSecurityButton(ActionEvent event) {
        PosizioniContainer.getChildren().clear();

        Map<String, PosizioniDipartimento> tabella = tpa.getTabella();
        PosizioniDipartimento pd = tabella.get("Security");
        List<PosizioneBean> listaPosizioni = pd.getListaPosizioni();

        for(PosizioneBean posizione : listaPosizioni){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SingolaPosizione.fxml"));
                Node element = loader.load();

                View1SingolaPosizioneController controller = loader.getController();
                controller.setParent(PosizioniContainer, element);
                controller.setPosizione(posizione, sessionHR);
                controller.setReference(this);
                controller.showPosizione();

                // fai l'append al VBox
                PosizioniContainer.getChildren().add(element);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleSalesButton(ActionEvent event) {
        PosizioniContainer.getChildren().clear();

        Map<String, PosizioniDipartimento> tabella = tpa.getTabella();
        PosizioniDipartimento pd = tabella.get("Sales");
        List<PosizioneBean> listaPosizioni = pd.getListaPosizioni();

        for(PosizioneBean posizione : listaPosizioni){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SingolaPosizione.fxml"));
                Node element = loader.load();

                View1SingolaPosizioneController controller = loader.getController();
                controller.setParent(PosizioniContainer, element);
                controller.setPosizione(posizione, sessionHR);
                controller.setReference(this);
                controller.showPosizione();

                // fai l'append al VBox
                PosizioniContainer.getChildren().add(element);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleBackButton(ActionEvent event) {
        // in questo caso torna alla home
        App.getSceneManager().switchTo("home", "/Fxml/HRhome.fxml", sessionHR);

    }

    public void handleLogoButton(ActionEvent event) {
        // torna alla home
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

    public void handlePubblica(ActionEvent event){
        try {
            PosizioneBean pb = new PosizioneBean();
            pb.setDipartimento(DipartimentoText.getText());
            pb.setNomePosizione(NomePosizioneText.getText());
            pb.setRequisiti(RequisitiText.getText());

            GestisciPosizioniAperteController gpac = new GestisciPosizioniAperteController();
            gpac.addPosizioneAperta(sessionHR.getCred(), pb, opcode);
            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Posizione pubblicata!");
        }catch(TaskException e){
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }

    @Override
    public void onShow(Object data) {
        sessionHR = (SessionHR) data;
        try {
            GestisciPosizioniAperteController gpac = new GestisciPosizioniAperteController();
            tpa = gpac.getTabellaPosizioniAperte(sessionHR.getCred(), opcode);
        }catch(TaskException e){
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }
    public void handleSwap(ActionEvent event){
        try {
            if (opcode.equals("1")) {
                opcode = "2";
                NotifyBackendLogs nbl = new NotifyBackendLogs();
                nbl.notifyLog("Modalità CSV!");
            }else if(opcode.equals("2")){
                opcode = "1";
                NotifyBackendLogs nbl = new NotifyBackendLogs();
                nbl.notifyLog("Modalità JDBC!");
            }
            GestisciPosizioniAperteController gpac = new GestisciPosizioniAperteController();
            tpa = gpac.getTabellaPosizioniAperte(sessionHR.getCred(), opcode);
        }catch(TaskException e){
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }
}
