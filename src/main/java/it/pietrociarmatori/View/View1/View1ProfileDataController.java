package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.View.SessionHR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class View1ProfileDataController {
    @FXML
    private Label NomeLabel;
    @FXML
    private Label CognomeLabel;
    @FXML
    private Label RuoloLabel;
    @FXML
    private Label DipartimentoLabel;
    @FXML
    private Label MatricolaLabel;
    @FXML
    private Label PasswordLabel;
    @FXML
    private Button LogoutButton;
    @FXML
    private Button CloseButton;

    private Pane parent;
    private Node self;

    public void setData(SessionHR sessionHR){
        NomeLabel.setText("Nome: "+sessionHR.getCred().getNome());
        CognomeLabel.setText("Cognome: "+sessionHR.getCred().getCognome());
        RuoloLabel.setText("Ruolo: "+sessionHR.getCred().getRuolo());
        DipartimentoLabel.setText("Dipartimento: "+sessionHR.getCred().getDipartimento());
        MatricolaLabel.setText("Matricola: "+sessionHR.getCred().getMatricola());
        PasswordLabel.setText("Password: "+sessionHR.getCred().getPassword());
    }

    public void handleLogout(ActionEvent event) {
        App.getSceneManager().switchTo("login", "/Fxml/Login.fxml");
    }
    public void setParent(Pane parent, Node self) {
        this.parent = parent;
        this.self = self;
    }

    public void handleClose(ActionEvent event) {
        if (parent != null && self != null) {
            parent.getChildren().remove(self);
        }
    }
}
