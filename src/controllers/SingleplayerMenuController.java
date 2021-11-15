package controllers;

import gui.*;
import gui.popup.InputPopupGUI;
import hangman.Main;
import hangman.Player;

public class SingleplayerMenuController {

    private static SingleplayerMenuController instance = new SingleplayerMenuController();

    private SingleplayerMenuController(){
        setup();
    }

    private void setup(){
        SingleplayerMenuGUI.inst().setStartListener(e -> {
            Player p = Main.inst().getDataHandler().getLoadedPlayer();
            if(p == null){
                InputPopupGUI popup = new InputPopupGUI();
                popup.setListener(e1 -> {
                    if(popup.getInput().equals("")){
                        popup.setError("You need to enter a name");
                        return;
                    }
                    if(!Main.inst().getDataHandler().isPlayerNameValid(popup.getInput())){
                        popup.setError("You are using invalid characters");
                        return;
                    }
                    if(!Main.inst().getDataHandler().isPlayerNameAvailable(popup.getInput())){
                        popup.setError("Player name already exists");
                        return;
                    }
                    Player createdPlayer = createPlayer(popup.getInput());
                    loadPlayer(createdPlayer);
                    GUI.inst().open(SingleplayerGUI.inst().getScene());
                    SingleplayerGameController.inst().startGame(Main.inst().getGameManager().createSingleplayerGame(createdPlayer));
                    popup.close();
                });
                popup.show();
            }else {
                GUI.inst().open(SingleplayerGUI.inst().getScene());
                SingleplayerGameController.inst().startGame(Main.inst().getGameManager().createSingleplayerGame(p));
            }
        });
        SingleplayerMenuGUI.inst().setCreatePlayerListener(e -> {
            InputPopupGUI popup = new InputPopupGUI();
            popup.setListener(e1 -> {
                if(popup.getInput().equals("")){
                    popup.setError("You need to enter a name");
                    return;
                }
                if(!Main.inst().getDataHandler().isPlayerNameValid(popup.getInput())){
                    popup.setError("You are using invalid characters");
                    return;
                }
                if(!Main.inst().getDataHandler().isPlayerNameAvailable(popup.getInput())){
                    popup.setError("Player name already exists");
                    return;
                }
                Player p = createPlayer(popup.getInput());
                loadPlayer(p);
                popup.close();
            });
            popup.show();
        });

        SingleplayerMenuGUI.inst().setLoadPlayerListener(e -> {
            LoadPlayerController.inst().reloadList();
            GUI.inst().open(LoadPlayerController.inst().getLoadPlayerGUI().getScene());
        });
        SingleplayerMenuGUI.inst().setBackListener(e -> {
            GUI.inst().open(MainMenuGUI.inst().getScene());
        });
    }

    public static SingleplayerMenuController inst(){
        return instance;
    }

    public Player createPlayer(String name) {
        return Main.inst().getDataHandler().createPlayer(name);
    }

    public void loadPlayer(Player p){
        Main.inst().getDataHandler().setLoadedPlayer(p);
        SingleplayerMenuGUI.inst().setLoadedPlayer(p.getName());
    }

}
