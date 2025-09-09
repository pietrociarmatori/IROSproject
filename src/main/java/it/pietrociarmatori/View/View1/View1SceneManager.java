package it.pietrociarmatori.View.View1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class View1SceneManager {
    private final Stage stage;
    private final Map<String, FXMLLoader> cache = new HashMap<>();

    public View1SceneManager(Stage stage){
        this.stage = stage;
    }

    public void switchTo(String typeOfScreen, String fxmlPath){
        switchTo(typeOfScreen, fxmlPath, null);
    }

    public void switchTo(String typeOfScreen, String fxmlPath, Object data){
        try {
            FXMLLoader loader = cache.computeIfAbsent(typeOfScreen, k -> {
                FXMLLoader l = new FXMLLoader(getClass().getResource(fxmlPath));
                try {
                    l.load();
                } catch (IOException e) {
                    throw new RuntimeException("Impossibile caricare lo schermo: "+typeOfScreen+"\nCausa:\n"+e);
                }
                return l;
            });

            Parent root = loader.getRoot();

            if (stage.getScene() == null) {
                stage.setScene(new Scene(root, 1200, 800));
            } else {
                stage.getScene().setRoot(root);
            }

            Object controller = loader.getController();
            if (controller instanceof ControlledScreen controlled) {
                controlled.onShow(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Stage getStage(){return this.stage;}
}
