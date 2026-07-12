package it.pietrociarmatori.View.View2;

import java.util.Scanner;

public interface CliScreen {
    void render(); // stampa lo schermo
    CliScreen handleInput(Scanner scanner); // processa l'input e ritorna lo schermo corretto
}
