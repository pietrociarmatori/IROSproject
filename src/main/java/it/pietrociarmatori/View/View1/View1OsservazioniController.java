package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.ControllerAppl.AggiungiProvvedimentoController;
import it.pietrociarmatori.ControllerAppl.GestisciOsservazioniDipendentiController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.OsservazioneBean;
import it.pietrociarmatori.Model.Beans.ProvvedimentoBean;
import it.pietrociarmatori.Model.Beans.TabellaOsservazioniBean;
import it.pietrociarmatori.Model.Entity.OsservazioniDipartimento;
import it.pietrociarmatori.View.SessionHR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class View1OsservazioniController implements ControlledScreen{
    @FXML
    private AnchorPane Root;
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
    private ImageView ProfileImage;
    @FXML
    private Button ProfileButton;
    @FXML
    private Label SentimentLabel;
    @FXML
    private Label DipartimentoLabel;
    @FXML
    private Label RiassuntoLabel;
    @FXML
    private VBox OsservazioniContainer;
    @FXML
    private Button PubblicaProvvedimentoButton;
    @FXML
    private TextArea ProvvedimentotextArea;

    private SessionHR sessionHR;
    private TabellaOsservazioniBean tob;
    @Override
    public void onShow(Object data) {
        sessionHR = (SessionHR) data;
        try {
            GestisciOsservazioniDipendentiController godc = new GestisciOsservazioniDipendentiController();
            tob = godc.getTabellaOsservazioni(sessionHR.getCred());
        }catch(TaskException e){
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
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

    public void handlePubblica(ActionEvent event) {
        try {
            // prende il testo dalla text area e lo pubblica
            AggiungiProvvedimentoController apc = new AggiungiProvvedimentoController();
            ProvvedimentoBean provv = new ProvvedimentoBean();

            String provvedimento = ProvvedimentotextArea.getText();
            String dipartimento = DipartimentoLabel.getText();
            dipartimento = dipartimento.replace("Dipartimento: ", "");

            provv.setDipartimento(dipartimento);
            provv.setProvvedimento(provvedimento);

            apc.addProvvedimento(sessionHR.getCred(), provv);
            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Provvedimenti pubblicati!");
        }catch(TaskException e){
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }

    public void handleLogoButton(ActionEvent event) {
        // torna nella home
        App.getSceneManager().switchTo("home", "/Fxml/HRhome.fxml", sessionHR);
    }

    public void handleSoftwareDevButton(ActionEvent event) {
        // carica la parte di tabella che riguarda software dev
        // riempie le labels e la vbox
        // prima controllo che la vbox sia svuotata

        OsservazioniContainer.getChildren().clear();

        Map<String, OsservazioniDipartimento> tabella = tob.getTabella();
        OsservazioniDipartimento od = tabella.get("SoftwareDevelopment");

        DipartimentoLabel.setText("Dipartimento: "+od.getNomeDipartimento());
        SentimentLabel.setText("Sentiment generale: "+od.getSentimentGenerale());
        RiassuntoLabel.setText("Riassunto: "+od.getRiassuntoOsservazioni());

        List<OsservazioneBean> listaOsservazioni = od.getListaOsservazioni();

        for(OsservazioneBean osservazione : listaOsservazioni){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SingolaOsservazione.fxml"));
                Node element = loader.load();

                View1SingolaOsservazioneController controller = loader.getController();
                controller.setParent(OsservazioniContainer, element);
                controller.setOsservazione(osservazione, sessionHR);
                controller.showOsservazione();

                // fai l'append al VBox
                OsservazioniContainer.getChildren().add(element);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleDataEngButton(ActionEvent event) {
        // carica la parte di tabella che riguarda data eng
        // riempie le labels e la vbox

        OsservazioniContainer.getChildren().clear();

        Map<String, OsservazioniDipartimento> tabella = tob.getTabella();
        OsservazioniDipartimento od = tabella.get("DataEngineering");

        DipartimentoLabel.setText("Dipartimento: "+od.getNomeDipartimento());
        SentimentLabel.setText("Sentiment generale: "+od.getSentimentGenerale());
        RiassuntoLabel.setText("Riassunto: "+od.getRiassuntoOsservazioni());

        List<OsservazioneBean> listaOsservazioni = od.getListaOsservazioni();

        for(OsservazioneBean osservazione : listaOsservazioni){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SingolaOsservazione.fxml"));
                Node element = loader.load();

                View1SingolaOsservazioneController controller = loader.getController();
                controller.setParent(OsservazioniContainer, element);
                controller.setOsservazione(osservazione, sessionHR);
                controller.showOsservazione();

                // fai l'append al VBox
                OsservazioniContainer.getChildren().add(element);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSecurityButton(ActionEvent event) {
        // carica la parte di tabella che riguarda security
        // riempie le labels e la vbox

        OsservazioniContainer.getChildren().clear();

        Map<String, OsservazioniDipartimento> tabella = tob.getTabella();
        OsservazioniDipartimento od = tabella.get("Security");

        DipartimentoLabel.setText("Dipartimento: "+od.getNomeDipartimento());
        SentimentLabel.setText("Sentiment generale: "+od.getSentimentGenerale());
        RiassuntoLabel.setText("Riassunto: "+od.getRiassuntoOsservazioni());

        List<OsservazioneBean> listaOsservazioni = od.getListaOsservazioni();

        for(OsservazioneBean osservazione : listaOsservazioni){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SingolaOsservazione.fxml"));
                Node element = loader.load();

                View1SingolaOsservazioneController controller = loader.getController();
                controller.setParent(OsservazioniContainer, element);
                controller.setOsservazione(osservazione, sessionHR);
                controller.showOsservazione();

                // fai l'append al VBox
                OsservazioniContainer.getChildren().add(element);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSalesButton(ActionEvent event) {
        // carica la parte di tabella che riguarda sales
        // riempie le labels e la vbox

        OsservazioniContainer.getChildren().clear();

        Map<String, OsservazioniDipartimento> tabella = tob.getTabella();
        OsservazioniDipartimento od = tabella.get("Sales");

        DipartimentoLabel.setText("Dipartimento: "+od.getNomeDipartimento());
        SentimentLabel.setText("Sentiment generale: "+od.getSentimentGenerale());
        RiassuntoLabel.setText("Riassunto: "+od.getRiassuntoOsservazioni());

        List<OsservazioneBean> listaOsservazioni = od.getListaOsservazioni();

        for(OsservazioneBean osservazione : listaOsservazioni){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SingolaOsservazione.fxml"));
                Node element = loader.load();

                View1SingolaOsservazioneController controller = loader.getController();
                controller.setParent(OsservazioniContainer, element);
                controller.setOsservazione(osservazione, sessionHR);
                controller.showOsservazione();

                // fai l'append al VBox
                OsservazioniContainer.getChildren().add(element);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleBackButton(ActionEvent event) {
        // va alla home in questo caso
        App.getSceneManager().switchTo("home", "/Fxml/HRhome.fxml", sessionHR);
    }
}
