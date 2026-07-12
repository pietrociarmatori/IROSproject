package it.pietrociarmatori.view.view1;

import it.pietrociarmatori.controllerappl.AggiungiProvvedimentoController;
import it.pietrociarmatori.controllerappl.GestisciOsservazioniDipendentiController;
import it.pietrociarmatori.exceptions.TaskException;
import it.pietrociarmatori.model.beans.OsservazioneBean;
import it.pietrociarmatori.model.beans.ProvvedimentoBean;
import it.pietrociarmatori.model.beans.TabellaOsservazioniBean;
import it.pietrociarmatori.model.entity.OsservazioniDipartimento;
import it.pietrociarmatori.view.SessionHR;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    private final String PathHRHome = "/Fxml/HRhome.fxml";

    private final String DIPARTIMENTO = "Dipartimento: ";
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
    public void handleProfile() {
        ProfileButtonRenderer pbr = new ProfileButtonRenderer();
        pbr.renderProfile(this.Root, this.sessionHR);
    }

    public void handlePubblica() {
        try {
            // prende il testo dalla text area e lo pubblica
            AggiungiProvvedimentoController apc = new AggiungiProvvedimentoController();
            ProvvedimentoBean provv = new ProvvedimentoBean();

            String provvedimento = ProvvedimentotextArea.getText();
            String dipartimento = DipartimentoLabel.getText();
            dipartimento = dipartimento.replace(DIPARTIMENTO, "");

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

    public void handleLogoButton() {
        // torna nella home
        App.getSceneManager().switchTo("home", PathHRHome, sessionHR);
    }

    public void handleSoftwareDevButton() {
        // carica la parte di tabella che riguarda software dev
        // riempie le labels e la vbox
        // prima controllo che la vbox sia svuotata

        OsservazioniContainer.getChildren().clear();

        Map<String, OsservazioniDipartimento> tabella = tob.getTabella();
        OsservazioniDipartimento od = tabella.get("SoftwareDevelopment");

        renderButton(od);
    }

    public void handleDataEngButton() {
        // carica la parte di tabella che riguarda data eng
        // riempie le labels e la vbox

        OsservazioniContainer.getChildren().clear();

        Map<String, OsservazioniDipartimento> tabella = tob.getTabella();
        OsservazioniDipartimento od = tabella.get("DataEngineering");

        renderButton(od);
    }

    public void handleSecurityButton() {
        // carica la parte di tabella che riguarda security
        // riempie le labels e la vbox

        OsservazioniContainer.getChildren().clear();

        Map<String, OsservazioniDipartimento> tabella = tob.getTabella();
        OsservazioniDipartimento od = tabella.get("Security");

        renderButton(od);
    }

    public void handleSalesButton() {
        // carica la parte di tabella che riguarda sales
        // riempie le labels e la vbox

        OsservazioniContainer.getChildren().clear();

        Map<String, OsservazioniDipartimento> tabella = tob.getTabella();
        OsservazioniDipartimento od = tabella.get("Sales");

        renderButton(od);
    }
    private void renderButton(OsservazioniDipartimento od){
        DipartimentoLabel.setText(DIPARTIMENTO+od.getNomeDipartimento());
        String SENTIMENT = "Sentiment generale: ";
        SentimentLabel.setText(SENTIMENT +od.getSentimentGenerale());
        String RIASSUNTO = "Riassunto: ";
        RiassuntoLabel.setText(RIASSUNTO +od.getRiassuntoOsservazioni());

        List<OsservazioneBean> listaOsservazioni = od.getListaOsservazioni();

        for(OsservazioneBean osservazione : listaOsservazioni){
            try {
                String pathSingolaOsservazione = "/Fxml/SingolaOsservazione.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(pathSingolaOsservazione));
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

    public void handleBackButton() {
        // va alla home in questo caso
        App.getSceneManager().switchTo("home", PathHRHome, sessionHR);
    }
}
