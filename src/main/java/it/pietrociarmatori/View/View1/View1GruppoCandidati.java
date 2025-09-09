package it.pietrociarmatori.View.View1;

import it.pietrociarmatori.ControllerAppl.GestisciCandidatiController;
import it.pietrociarmatori.Exceptions.TaskException;
import it.pietrociarmatori.Model.Beans.CandidatoBean;
import it.pietrociarmatori.Model.Beans.TabellaCandidatiBean;
import it.pietrociarmatori.Model.Entity.CandidatiDipartimento;
import it.pietrociarmatori.View.SessionHR;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class View1GruppoCandidati implements ControlledScreen{
    public Button SoftwareDevButton;
    public Button DataEngButton;
    public Button SecurityButton;
    public Button SalesButton;
    public Button BackButton;
    public Button LogoButton;
    public Button ProfileButton;
    public VBox CandidatiContainer;
    public SessionHR sessionHR;
    public AnchorPane Root;
    public TabellaCandidatiBean tcb;

    public void handleSoftwareDevButton(ActionEvent event) {
        CandidatiContainer.getChildren().clear();

        Map<String, CandidatiDipartimento> tabella = tcb.getTabella();
        CandidatiDipartimento cd = tabella.get("SoftwareDevelopment");
        List<CandidatoBean> listaCandidati = cd.getListaCandidati();

        for(CandidatoBean candidato : listaCandidati){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/CandidatoButton.fxml"));
                Node element = loader.load();

                View1CandidatoButtonController controller = loader.getController();
                controller.setParent(CandidatiContainer, element);
                controller.setCandidato(candidato);
                controller.onShow(sessionHR);

                CandidatiContainer.getChildren().add(element);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleDataEngButton(ActionEvent event) {
        CandidatiContainer.getChildren().clear();

        Map<String, CandidatiDipartimento> tabella = tcb.getTabella();
        CandidatiDipartimento cd = tabella.get("DataEngineering");
        List<CandidatoBean> listaCandidati = cd.getListaCandidati();

        for(CandidatoBean candidato : listaCandidati){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/CandidatoButton.fxml"));
                Node element = loader.load();

                View1CandidatoButtonController controller = loader.getController();
                controller.setParent(CandidatiContainer, element);
                controller.setCandidato(candidato);
                controller.onShow(sessionHR);

                CandidatiContainer.getChildren().add(element);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSecurityButton(ActionEvent event) {
        CandidatiContainer.getChildren().clear();

        Map<String, CandidatiDipartimento> tabella = tcb.getTabella();
        CandidatiDipartimento cd = tabella.get("Security");
        List<CandidatoBean> listaCandidati = cd.getListaCandidati();

        for(CandidatoBean candidato : listaCandidati){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/CandidatoButton.fxml"));
                Node element = loader.load();

                View1CandidatoButtonController controller = loader.getController();
                controller.setParent(CandidatiContainer, element);
                controller.setCandidato(candidato);
                controller.onShow(sessionHR);

                CandidatiContainer.getChildren().add(element);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSalesButton(ActionEvent event) {
        CandidatiContainer.getChildren().clear();

        Map<String, CandidatiDipartimento> tabella = tcb.getTabella();
        CandidatiDipartimento cd = tabella.get("Sales");
        List<CandidatoBean> listaCandidati = cd.getListaCandidati();

        for(CandidatoBean candidato : listaCandidati){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/CandidatoButton.fxml"));
                Node element = loader.load();

                View1CandidatoButtonController controller = loader.getController();
                controller.setParent(CandidatiContainer, element);
                controller.setCandidato(candidato);
                controller.onShow(sessionHR);

                CandidatiContainer.getChildren().add(element);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleBackButton(ActionEvent event) {
        // va alla home in questo caso
        App.getSceneManager().switchTo("home", "/Fxml/HRhome.fxml", sessionHR);
    }

    public void handleLogoButton(ActionEvent event) {
        // va alla home
        App.getSceneManager().switchTo("home", "/Fxml/HRhome.fxml", sessionHR);
    }

    // Comune a tutte le schermate con l'icona del profilo
    public void handleProfile(ActionEvent event) {
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
            controller.setData(this.sessionHR);

            Root.getChildren().add(sidePanel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onShow(Object data) {
        if(data instanceof SessionHR){
            this.sessionHR = (SessionHR) data;
        }
        try {
            GestisciCandidatiController gcc = new GestisciCandidatiController();
            tcb = gcc.getTabellaCandidati(sessionHR.getCred());
        }catch(TaskException e){
            NotifyBackendLogs nbe = new NotifyBackendLogs();
            nbe.notifyError(e.getMessage());
        }
    }
}
