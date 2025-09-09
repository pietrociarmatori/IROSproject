package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.ControllerAppl.AggiungiOsservazioneController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.OsservazioneBean;
import it.pietrociarmatori.View.SessionEmployee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;

public class View1FormOsservazioneController implements ControlledScreen{
    @FXML
    public ImageView ImageLogo;
    @FXML
    public Label NomeLabel;
    @FXML
    public Label CognomeLabel;
    @FXML
    public Label RuoloLabel;
    @FXML
    public Label DipartimentoLabel;
    @FXML
    public Label MatricolaLabel;
    @FXML
    public Label PasswordLabel;
    @FXML
    public TextArea OsservazioneText;
    @FXML
    public Button BottonePubblica;
    private SessionEmployee session;

    public void handleBottone(ActionEvent event){
        try {
            AggiungiOsservazioneController aoc = new AggiungiOsservazioneController();
            OsservazioneBean ob = new OsservazioneBean();

            ob.setOsservazione(OsservazioneText.getText());
            ob.setNome(session.getCred().getNome());
            ob.setCognome(session.getCred().getCognome());

            aoc.aggiungiOsservazione(session.getCred(), ob);

            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyLog("Grazie per la condivisione!");

        }catch(TaskException e){
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }

    @Override
    public void onShow(Object data) {
        if(data instanceof SessionEmployee){
            this.session = (SessionEmployee) data;
        }

        OsservazioneText.setWrapText(true);
        NomeLabel.setText("Nome: "+session.getCred().getNome());
        CognomeLabel.setText("Cognome: "+session.getCred().getCognome());
        RuoloLabel.setText("Ruolo: "+session.getCred().getRuolo());
        DipartimentoLabel.setText("Dipartimento: "+session.getCred().getDipartimento());
        MatricolaLabel.setText("Matricola: "+session.getCred().getMatricola());
        PasswordLabel.setText("Password: "+session.getCred().getPassword());
    }

    public SessionEmployee getSession() {
        return session;
    }

}
