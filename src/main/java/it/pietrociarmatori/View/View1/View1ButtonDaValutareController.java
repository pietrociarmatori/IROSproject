package it.pietrociarmatori.View.View1;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class View1ButtonDaValutareController {
    public Button BottoneDaValutare;
    public View1SingoloCandidatoController parentController;

    public void onDaValutare(ActionEvent event) {
        parentController.spostaInDaValutare();
    }
    public void setParentController(View1SingoloCandidatoController controller){
        this.parentController = controller;
    }
}
