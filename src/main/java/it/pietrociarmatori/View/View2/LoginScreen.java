package it.pietrociarmatori.View.View2;

import it.pietrociarmatori.ControllerAppl.LoginController;
import it.pietrociarmatori.Exceptions.LoginException;
import it.pietrociarmatori.Model.Beans.CredentialsEmployeeBean;
import it.pietrociarmatori.Model.Beans.CredentialsHRBean;
import it.pietrociarmatori.Model.Beans.LoginCredentialsBean;
import it.pietrociarmatori.Model.Entity.Credentials;
import it.pietrociarmatori.View.SessionEmployee;
import it.pietrociarmatori.View.SessionHR;

import java.util.Scanner;

public class LoginScreen implements CliScreen{
    @Override
    public void render() {
        System.out.println("-------------------------------------------------------");
        System.out.println("-------------------------------------------------------");
        System.out.println("-------  --------------  --------------  --------------");
        System.out.println("|     |  |            |  |            |  |            |");
        System.out.println("|     |  |     ---    |  |      |     |  |            |");
        System.out.println("|     |  |            |  |      |     |  |       -----|");
        System.out.println("|     |  |       -----|  |      |     |  |            |");
        System.out.println("|     |  |            |  |      |     |  |-----       |");
        System.out.println("|     |  |     |      |  |      |     |  |            |");
        System.out.println("|     |  |     |      |  |            |  |            |");
        System.out.println("-------  --------------  --------------  --------------");
        System.out.println("-------------------------------------------------------");
        System.out.println("-------------------------------------------------------");
        for(int i = 0; i<15;i++){
            System.out.println();
        }
        System.out.println("LOGIN: ");
    }

    @Override
    public CliScreen handleInput(Scanner scanner) {
        System.out.print("Matricola: ");
        String matricola = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        try {
            LoginCredentialsBean lcb = new LoginCredentialsBean();
            lcb.setMatricola(matricola);
            lcb.setPassword(pass);

            LoginController lc = new LoginController();

            // questa interfaccia non ha nessun listener, il log delle notifiche e degli errori viene fatto direttamente nei controller
            Credentials cred = lc.login(lcb, null);

            if(cred instanceof CredentialsHRBean){
                SessionHR session = new SessionHR();
                session.setCred((CredentialsHRBean) cred);

                return new HomeScreen(session);

            }else if(cred instanceof CredentialsEmployeeBean){
                SessionEmployee session = new SessionEmployee();
                session.setCred((CredentialsEmployeeBean) cred);

                return new EmployeeFormScreen(session);
            }
        }catch(LoginException e){
            System.out.println("Credenziali errate! Premere ENTER per riprovare...");
            scanner.nextLine();
            return this; // reload login
        }
        return null;
    }
}
