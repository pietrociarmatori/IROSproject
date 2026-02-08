package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.ControllerAppl.GestisciPosizioniAperteController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.PosizioneBean;
import it.pietrociarmatori.View.SessionHR;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class View1SingolaPosizioneController{
    @FXML
    private AnchorPane PosizioneContainer;
    @FXML
    private Button EliminaButton;
    @FXML
    private Label DipartimentoLabel;
    @FXML
    private Label NomePosizioneLabel;
    @FXML
    private Label RequisitiLabel;
    private VBox parent;
    private Node self;
    private SessionHR sessionHR;
    private PosizioneBean pd;
    private View1PosizioniAperteController parentController;

    public void setPosizione(PosizioneBean pd, SessionHR session){
        this.pd = pd;
        this.sessionHR = session;
    }
    public void showPosizione(){
        DipartimentoLabel.setText("Dipartimento: "+ pd.getDipartimento());
        NomePosizioneLabel.setText("Nome posizione: "+ pd.getNomePosizione());
        RequisitiLabel.setText("Requisisti: "+ pd.getRequisiti());
    }
    public void setParent(VBox parent, Node self) {
        this.parent = parent;
        this.self = self;
    }

    public void handleEliminaButton(ActionEvent event){
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                GestisciPosizioniAperteController gpac =
                        new GestisciPosizioniAperteController();

                gpac.deletePosizioneAperta(
                        sessionHR.getCred(),
                        pd,
                        parentController.getopcode()
                );
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            parent.getChildren().remove(self);
            new NotifyBackendLogs().notifyLog("Posizione eliminata!");
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            new NotifyBackendLogs().notifyError(
                    ex != null ? ex.getMessage() : "Errore durante eliminazione"
            );
        });

        new Thread(task).start();
    }
    public void setReference(View1PosizioniAperteController controller){
        this.parentController = controller;
    }
}
