package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.ControllerAppl.GestisciPosizioniAperteController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.PosizioneBean;
import it.pietrociarmatori.View.SessionHR;
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
    public VBox parent;
    public Node self;
    public SessionHR sessionHR;
    public PosizioneBean pd;
    public View1PosizioniAperteController parentController;

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
        try {
            // chiama il controller per eliminare l'osservazione
            GestisciPosizioniAperteController gpac = new GestisciPosizioniAperteController();
            gpac.deletePosizioneAperta(sessionHR.getCred(), pd, parentController.getopcode());
            if (parent != null && self != null) {
                parent.getChildren().remove(self);
            }
            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Posizione eliminata!");
        }catch(TaskException e){
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }
    public void setReference(View1PosizioniAperteController controller){
        this.parentController = controller;
    }
}
