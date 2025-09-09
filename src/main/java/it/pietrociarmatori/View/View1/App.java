package it.pietrociarmatori.View.View1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class App extends Application{
    private static View1SceneManager sceneManager;

    @Override
    public void start(Stage primaryStage) throws Exception{

        sceneManager = new View1SceneManager(primaryStage);

        sceneManager.switchTo("login", "/Fxml/Login.fxml");

        primaryStage.setTitle("Prototype");
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }

    public static View1SceneManager getSceneManager(){
        return sceneManager;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
