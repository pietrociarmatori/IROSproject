package it.pietrociarmatori.View.View1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class View1ButtonIdoneoController {
    @FXML
    private Button BottoneIdoneo;
    public View1SingoloCandidatoController parentController;

    public void onIdoneo(ActionEvent event) {
        parentController.spostaInIdoneo();
    }
    public void setParentController(View1SingoloCandidatoController controller){
        this.parentController = controller;
    }
}
