package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.Model.Helpers.ListenerThread;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NotifyBackendLogs implements ListenerThread {

    @Override
    public void notify(Throwable e) {
        // carica un piccolo box che notifica l'utente di un errore avvenuto nel backend
        // if un tipo di eccezione allora printa una cosa
        // if un altro tipo di eccezione allora printa un'altra cosa
        notifyError(e.getMessage());
    }
    public void notifyError(String error){
        // carica un piccolo box che notifica di errori a runtime per i casi d'uso
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/NotifyBox.fxml"));
            Parent root = loader.load();

            // recupero il controller
            View1NotifyBoxController controller = loader.getController();
            controller.setTheBox("Errore", error);

            // creo un nuovo Stage
            Stage stage = new Stage();
            stage.setTitle("");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // blocca interazione con altre finestre finché non chiudi
            stage.setResizable(false);

            // faccio vedere la finestra
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void notifyLog(String str){
        // serve per notifiche non di errore quant'altro
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/NotifyBox.fxml"));
            Parent root = loader.load();

            // recupero il controller
            View1NotifyBoxController controller = loader.getController();
            controller.setTheBox("Notifica", str);

            // creo un nuovo Stage
            Stage stage = new Stage();
            stage.setTitle("");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // blocca interazione con altre finestre finché non chiudi
            stage.setResizable(false);

            // faccio vedere la finestra
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
