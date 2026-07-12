package it.pietrociarmatori.view.view1;

import it.pietrociarmatori.view.SessionHR;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    
    public void handleOsservazioni() {
        // carica la pagina delle osservazioni
        App.getSceneManager().switchTo("osservazioni", "/Fxml/HROsservazioni.fxml", sessionHR);
    }

    public void handlePosizioni() {
        // carica la pagina delle posizioni aperte
        App.getSceneManager().switchTo("posizioni", "/Fxml/HRPosizioniAperte.fxml", sessionHR);
    }

    public void handleCandidati() {
        // carica la pagina dei candidati
        App.getSceneManager().switchTo("candidati", "/Fxml/HRGruppoCandidati.fxml", sessionHR);
    }

    // Comune a tutte le schermate con l'icona del profilo
    public void handleProfile() {
        ProfileButtonRenderer pbr = new ProfileButtonRenderer();
        pbr.renderProfile(this.Root, this.sessionHR);
    }

    public SessionHR getSessionHR() {
        return sessionHR;
    }


    @Override
    public void onShow(Object data) {
        if(data instanceof SessionHR){
            this.sessionHR = (SessionHR) data;
        }
    }
}
