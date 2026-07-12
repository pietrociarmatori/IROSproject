package it.pietrociarmatori.view.view1;

import it.pietrociarmatori.controllerappl.LoginController;
import it.pietrociarmatori.exceptions.LoginException;
import it.pietrociarmatori.model.beans.CredentialsEmployeeBean;
import it.pietrociarmatori.model.beans.CredentialsHRBean;
import it.pietrociarmatori.model.beans.LoginCredentialsBean;
import it.pietrociarmatori.model.entity.Credentials;
import it.pietrociarmatori.view.SessionEmployee;
import it.pietrociarmatori.view.SessionHR;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class View1LoginController {
    @FXML
    private ImageView ImageLogo;
    @FXML
    private TextField MatricolaText;
    @FXML
    private TextField PasswordText;
    @FXML
    private Button BottoneLogin;

    public void handleButton(){
        try {
            LoginCredentialsBean lcb = new LoginCredentialsBean();
            lcb.setMatricola(MatricolaText.getText());
            lcb.setPassword(PasswordText.getText());

            LoginController lc = new LoginController();
            NotifyBackendLogs nbe = new NotifyBackendLogs();

            Credentials cred = lc.login(lcb, nbe);

            if(cred instanceof CredentialsHRBean){
                SessionHR session = new SessionHR();
                session.setCred((CredentialsHRBean) cred);

                App.getSceneManager().switchTo("home", "/Fxml/HRhome.fxml", session);

            }else if(cred instanceof CredentialsEmployeeBean){
                SessionEmployee session = new SessionEmployee();
                session.setCred((CredentialsEmployeeBean) cred);

                App.getSceneManager().switchTo("form", "/Fxml/Formosservazioni.fxml", session);
            }
        }catch(LoginException e){
            NotifyBackendLogs nbl = new NotifyBackendLogs();
            nbl.notifyError("Credenziali errate!");
        }
    }
}
