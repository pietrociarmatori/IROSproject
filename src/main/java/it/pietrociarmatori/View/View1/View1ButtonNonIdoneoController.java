package it.pietrociarmatori.View.View1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class View1ButtonNonIdoneoController {
    @FXML
    private Button BottoneNonIdoneo;
    public View1SingoloCandidatoController parentController;

    public void onNonIdoneo(ActionEvent event) {
        parentController.spostaInNonIdoneo();
    }
    public void setParentController(View1SingoloCandidatoController controller){
        this.parentController = controller;
    }
}
