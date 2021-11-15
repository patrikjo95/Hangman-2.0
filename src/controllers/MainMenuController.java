package controllers;

import gui.*;
import gui.popup.ResumeGamePopupGUI;
import hangman.Main;
import hangman.MultiplayerGame;
import hangman.Player;

public class MainMenuController {

    private static MainMenuController instance = new MainMenuController();

    private MainMenuController() {
        setup();
    }

    public static MainMenuController inst() {
        return instance;
    }

    private void setup() {
        MainMenuGUI.inst().setSingleplayerListener(e -> {
            GUI.inst().open(SingleplayerMenuGUI.inst().getScene());
        });
        MainMenuGUI.inst().setMultiplayerListener(e -> {
            if(Main.inst().getDataHandler().hasStoreMultiplayerGame()){
                ResumeGamePopupGUI popup = new ResumeGamePopupGUI();
                popup.setYesListener(e1 -> {
                    MultiplayerGameController.inst().loadGame(Main.inst().getDataHandler().loadPreviousMultiplayerGame());
                    popup.close();
                });
                popup.setNoListener(e1 -> {
                    MultiplayerGameController.inst().setGame(Main.inst().getGameManager().createMultiplayerGame());
                    popup.close();
                });
                popup.show();
            }else{
                MultiplayerGameController.inst().setGame(Main.inst().getGameManager().createMultiplayerGame());
            }
            GUI.inst().open(MultiplayerGUI.inst().getScene());
        });
        MainMenuGUI.inst().setHighScoreListener(e ->{
            GUI.inst().open(HighScoreGUI.inst().getScene());
            HighScoreController.inst().updateList();
        });
    }

}
