package it.pietrociarmatori.View.View2;

import java.util.Scanner;

public class CliSceneManager {
    private CliScreen currentScreen;
    private Scanner scanner = new Scanner(System.in);

    public CliSceneManager(CliScreen startScreen) {
        this.currentScreen = startScreen;
    }

    public void start() {
        while (currentScreen != null) {
            clearScreen();
            currentScreen.render();
            currentScreen = currentScreen.handleInput(scanner);
        }
        System.out.println("Exiting application...");
    }

    private void clearScreen() {
        // Bruttissimo ma pazienza
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
