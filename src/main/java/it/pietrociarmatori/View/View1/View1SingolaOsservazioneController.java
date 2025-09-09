package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.ControllerAppl.GestisciOsservazioniDipendentiController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.OsservazioneBean;
import it.pietrociarmatori.View.SessionHR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class View1SingolaOsservazioneController {
    @FXML
    private AnchorPane OsservazioneContainer;
    @FXML
    private Button EliminaButton;
    @FXML
    private Label DipendenteLabel;
    @FXML
    private Label RuoloLabel;
    @FXML
    private Label OsservazioneLabel;
    @FXML
    private Label SentimentLabel;
    private OsservazioneBean od;
    private VBox parent;
    private Node self;
    private SessionHR sessionHR;

    public void handleEliminaButton(ActionEvent event){
        try {
            // chiama il controller per eliminare l'osservazione
            GestisciOsservazioniDipendentiController godc = new GestisciOsservazioniDipendentiController();
            godc.deleteOsservazione(sessionHR.getCred(), od);
            if (parent != null && self != null) {
                parent.getChildren().remove(self);
            }
            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Osservazione eliminata!");
        }catch(TaskException e){
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }
    public void setOsservazione(OsservazioneBean od, SessionHR session){
        this.od = od;
        this.sessionHR = session;
    }
    public void showOsservazione(){
        DipendenteLabel.setText("Il dipendente: "+od.getNome()+" "+od.getCognome());
        RuoloLabel.setText("Ruolo: "+od.getMatricola());
        OsservazioneLabel.setText("Osservazione: "+od.getOsservazione());
        SentimentLabel.setText("Sentiment: "+od.getSentiment());
    }
    public void setParent(VBox parent, Node self) {
        this.parent = parent;
        this.self = self;
    }
}
