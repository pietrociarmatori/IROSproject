package it.pietrociarmatori.View.View2;

public class AppCli {
    public static void main(String[] args) {
        CliSceneManager manager = new CliSceneManager(new LoginScreen());
        manager.start();
    }
}
