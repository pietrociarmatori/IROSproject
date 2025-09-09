package it.pietrociarmatori.View.View1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class View1NotifyBoxController {
    @FXML
    private Label ErroreONotifica;
    @FXML
    private Label ContenutoMessaggio;
    @FXML
    private Button OkButton;
    @FXML
    private AnchorPane Root;

    public void handleButton(ActionEvent event) {
        Stage stage = (Stage) Root.getScene().getWindow();
        stage.close();
    }

    public void setTheBox(String tipo, String contenuto){
        if(tipo.equals("Notifica")){
            ErroreONotifica.setText("Notifica!");
            ErroreONotifica.setTextFill(Paint.valueOf("#479e3e"));
        }else if(tipo.equals("Errore")){
            ErroreONotifica.setText("Errore!");
            ErroreONotifica.setTextFill(Paint.valueOf("#bd3935"));
        }
        ContenutoMessaggio.setText(contenuto);
    }
}
