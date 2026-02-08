package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.View.SessionHR;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ProfileButtonRenderer {
    public void renderProfile(AnchorPane Root, SessionHR sessionHR){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ProfileInfo.fxml"));
            Parent sidePanel = loader.load();

            // Position it: (bigWidth - smallWidth)
            double bigWidth = 1200;
            double smallWidth = 350; // match prefWidth in FXML
            sidePanel.setLayoutX(bigWidth - smallWidth);
            sidePanel.setLayoutY(0);


            // Give sidePanel access to parent for closing
            View1ProfileDataController controller = loader.getController();
            controller.setParent(Root, sidePanel);
            controller.setData(sessionHR);

            Root.getChildren().add(sidePanel);

        } catch (IOException ignored) {
        }
    }
}
