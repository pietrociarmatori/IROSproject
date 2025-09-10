package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.View.SessionHR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class View1HrHomeController implements ControlledScreen{
    @FXML
    private ImageView LogoImage;
    @FXML
    private ImageView ProfileImage;
    @FXML
    private AnchorPane Root;
    @FXML
    private Button GestisciOsservazioniButton;
    @FXML
    private Button GestisciPosizioniAperteButton;
    @FXML
    private Button GestisciCandidatiButton;
    @FXML
    private Button ProfileButton;

    private SessionHR sessionHR;
    
    public void handleOsservazioni(ActionEvent event) {
        // carica la pagina delle osservazioni
        App.getSceneManager().switchTo("osservazioni", "/Fxml/HROsservazioni.fxml", sessionHR);
    }

    public void handlePosizioni(ActionEvent event) {
        // carica la pagina delle posizioni aperte
        App.getSceneManager().switchTo("posizioni", "/Fxml/HRPosizioniAperte.fxml", sessionHR);
    }

    public void handleCandidati(ActionEvent event) {
        // carica la pagina dei candidati
        App.getSceneManager().switchTo("candidati", "/Fxml/HRGruppoCandidati.fxml", sessionHR);
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

    public SessionHR getSessionHR() {
        return sessionHR;
    }

    private void setSessionHR(SessionHR sessionHR) {
        this.sessionHR = sessionHR;
    }

    @Override
    public void onShow(Object data) {
        if(data instanceof SessionHR){
            this.sessionHR = (SessionHR) data;
        }
    }
}
