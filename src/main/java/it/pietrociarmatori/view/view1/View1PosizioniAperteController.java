package it.pietrociarmatori.view.view1;

import it.pietrociarmatori.controllerappl.GestisciPosizioniAperteController;
import it.pietrociarmatori.exceptions.TaskException;
import it.pietrociarmatori.model.beans.PosizioneBean;
import it.pietrociarmatori.model.beans.TabellaPosizioniAperteBean;
import it.pietrociarmatori.model.entity.PosizioniDipartimento;
import it.pietrociarmatori.view.SessionHR;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private SessionHR sessionHR;
    private TabellaPosizioniAperteBean tpa;
    private String opcode = "1";
    private final String PathSingolaPosizione = "/Fxml/SingolaPosizione.fxml";
    private final String PathHRHome = "/Fxml/HRhome.fxml";

    public String getopcode(){
        return this.opcode;
    }

    public void handleSoftwareDevButton(){
        PosizioniContainer.getChildren().clear();

        Map<String, PosizioniDipartimento> tabella = tpa.getTabella();
        PosizioniDipartimento pd = tabella.get("SoftwareDevelopment");
        List<PosizioneBean> listaPosizioni = pd.getListaPosizioni();

        for(PosizioneBean posizione : listaPosizioni){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource(PathSingolaPosizione)); // cambiato da PathSingolaOsservazione a PathSingolaPosizione
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

    public void handleDataEngButton() {
        PosizioniContainer.getChildren().clear();

        Map<String, PosizioniDipartimento> tabella = tpa.getTabella();
        PosizioniDipartimento pd = tabella.get("DataEngineering");
        List<PosizioneBean> listaPosizioni = pd.getListaPosizioni();

        for(PosizioneBean posizione : listaPosizioni){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource(PathSingolaPosizione)); // cambiato da PathSingolaOsservazione a PathSingolaPosizione
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

    public void handleSecurityButton() {
        PosizioniContainer.getChildren().clear();

        Map<String, PosizioniDipartimento> tabella = tpa.getTabella();
        PosizioniDipartimento pd = tabella.get("Security");
        List<PosizioneBean> listaPosizioni = pd.getListaPosizioni();

        for(PosizioneBean posizione : listaPosizioni){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource(PathSingolaPosizione)); // cambiato da PathSingolaOsservazione a PathSingolaPosizione
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

    public void handleSalesButton() {
        PosizioniContainer.getChildren().clear();

        Map<String, PosizioniDipartimento> tabella = tpa.getTabella();
        PosizioniDipartimento pd = tabella.get("Sales");
        List<PosizioneBean> listaPosizioni = pd.getListaPosizioni();

        for(PosizioneBean posizione : listaPosizioni){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource(PathSingolaPosizione)); // cambiato da PathSingolaOsservazione a PathSingolaPosizione
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

    public void handleBackButton() {
        // in questo caso torna alla home
        App.getSceneManager().switchTo("home", PathHRHome, sessionHR);

    }

    public void handleLogoButton() {
        // torna alla home
        App.getSceneManager().switchTo("home", PathHRHome, sessionHR);

    }

    // Comune a tutte le schermate con l'icona del profilo
    public void handleProfile() {
        ProfileButtonRenderer pbr = new ProfileButtonRenderer();
        pbr.renderProfile(this.Root, this.sessionHR);
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
    public void handleSwap(){
            if (opcode.equals("1")) {
                opcode = "2";
                NotifyBackendLogs nbl = new NotifyBackendLogs();
                nbl.notifyLog("Modalità CSV!");
            }else if(opcode.equals("2")){
                opcode = "1";
                NotifyBackendLogs nbl = new NotifyBackendLogs();
                nbl.notifyLog("Modalità JDBC!");
            }
            new Thread(() -> {
                try {
                    GestisciPosizioniAperteController gpac = new GestisciPosizioniAperteController();
                    TabellaPosizioniAperteBean nuova =
                            gpac.getTabellaPosizioniAperte(sessionHR.getCred(), opcode);

                    Platform.runLater(() -> tpa = nuova);

                } catch (TaskException e) {
                    Platform.runLater(() ->
                            new NotifyBackendLogs().notifyError(e.getMessage())
                    );
                }
            }).start();
    }
}
