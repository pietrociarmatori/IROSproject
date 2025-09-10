package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.View.SessionHR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class View1CandidatoButtonController implements ControlledScreen{
    @FXML
    private AnchorPane Root;
    @FXML
    private Label PosizioneLabel;
    @FXML
    private Label IdoneitaLabel;
    @FXML
    private Label SkillLabel;
    @FXML
    private Button CandidatoButton;
    private SessionHR sessionHR;
    private VBox parent;
    private Node self;
    private CandidatoBean cb;


    public void setCandidato(CandidatoBean candidato){
        cb = candidato;
    }
    @Override
    public void onShow(Object data) {
        sessionHR = (SessionHR) data;
        PosizioneLabel.setText("Posizione: "+cb.getPosizione());
        IdoneitaLabel.setText("Idoneità: "+cb.getIdoneita());
        SkillLabel.setText("Skill: "+cb.getSkillCandidato());
    }

    public void handleButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/HrSingoloCandidato.fxml"));
            Parent root = loader.load(); // first load the FXML

            // now the controller exists
            View1SingoloCandidatoController controller = loader.getController();
            controller.setCandidato(cb);
            controller.onShow(sessionHR);

            // replace the scene root
            Stage stage = App.getSceneManager().getStage();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setParent(VBox parent, Node self) {
        this.parent = parent;
        this.self = self;
    }
}
