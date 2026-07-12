package it.pietrociarmatori.view.view1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class View1ButtonDaValutareController {
    @FXML
    private Button BottoneDaValutare;
    public View1SingoloCandidatoController parentController;

    public void onDaValutare() {
        parentController.spostaInDaValutare();
    }
    public void setParentController(View1SingoloCandidatoController controller){
        this.parentController = controller;
    }
}
