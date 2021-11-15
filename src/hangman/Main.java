package hangman;

import gui.GUI;
import hangman.data.DataHandler;
import javafx.application.Application;

public class Main {

    /*
    *
    * Creators: Patrik, Lucas
    *
    * */

    private static Main main = new Main();

    private DataHandler dataHandler;
    private GameManager gameManager;

    public static void main(String[] args) {
        Application.launch(GUI.class, args); //This method needs to be called before using the static instance of GUI class.
    }

    public Main() {
        dataHandler = new DataHandler();
        gameManager = new GameManager();
    }

    public static Main inst() {
        return main;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

}

