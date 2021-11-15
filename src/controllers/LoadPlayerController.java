package controllers;

import gui.GUI;
import gui.LoadPlayerGUI;
import gui.SingleplayerMenuGUI;
import gui.popup.WarningPopupGUI;
import hangman.Main;
import hangman.Player;

import java.util.Comparator;
import java.util.List;

public class LoadPlayerController {

    /*
    *
    *
    *
    * DENNA KLASS ÄR ABOSULT INTE IDENTISK TILL LOADMULTIPLAYERPLAYERCONTROLLER.
    *
    *
    *
    *
    *
    * */

    private static LoadPlayerController instance = new LoadPlayerController();

    private LoadPlayerGUI gui;

    private Player highLightedPlayer;

    private int sortTracker = 1; //Bad name;

    private LoadPlayerController(){
        gui = new LoadPlayerGUI();
        highLightedPlayer = null;
        setup();
    }

    private void setup(){
        gui.setLoadListener(e -> {
            if(highLightedPlayer == null){
                return;
            }
            Main.inst().getDataHandler().setLoadedPlayer(highLightedPlayer);
            SingleplayerMenuGUI.inst().setLoadedPlayer(highLightedPlayer.getName());
            gui.highlight(highLightedPlayer.getName(), false);
            highLightedPlayer = null;
            GUI.inst().open(SingleplayerMenuGUI.inst().getScene());
        });
        gui.setRemoveListener(e -> {
            if(highLightedPlayer == null){
                return;
            }
            if(Main.inst().getDataHandler().hasStoreMultiplayerGame()){
                if(Main.inst().getDataHandler().loadPreviousMultiplayerGame().getPlayers().contains(highLightedPlayer)){
                    WarningPopupGUI popup = new WarningPopupGUI("This player is currently stored in a multiplayer game.\n This action will delete the stored game.");
                    popup.setConfirmListener(e1 -> {
                        removeHighlightedPlayer();
                        Main.inst().getDataHandler().clearMultiplayerSaveFile();
                        popup.close();
                    });
                    popup.setCancelListener(e1 -> {
                        popup.close();
                    });
                    popup.show();
                    return;
                }
            }
            removeHighlightedPlayer();
        });
        gui.setBackListener(e -> {
            GUI.inst().open(SingleplayerMenuGUI.inst().getScene());
            highLightedPlayer = null;
        });
        gui.setHeaderNameListener(e -> {
            sortTracker = 1;
            reloadList();
        });
        gui.setHeaderGamesPlayedListener(e -> {
            sortTracker = 2;
            reloadList();
        });
        gui.setHeaderGamesWinListener(e -> {
            sortTracker = 3;
            reloadList();
        });
        gui.setHeaderMultiplayerGamesPlayedListener(e -> {
            sortTracker = 4;
            reloadList();
        });
        gui.setHeaderMultiplayerGamesWinListener(e -> {
            sortTracker = 5;
            reloadList();
        });
    }

    public static LoadPlayerController inst(){
        return instance;
    }

    private void removeHighlightedPlayer() {
        Main.inst().getDataHandler().deletePlayer(highLightedPlayer);
        highLightedPlayer = null;
        Main.inst().getDataHandler().setLoadedPlayer(null);
        SingleplayerMenuGUI.inst().setLoadedPlayer("");
        reloadList();
    }

    /**
     * Sorts the player list according to the corresponding int of sortTracker.
     * sortTracker keeps tracks of the clicked header item.
     * */
    private List<Player> sort(List<Player> list) {
        switch(sortTracker){
            case 1:
                list.sort(Comparator.comparing(Player::getName));
                break;
            case 2:
                list.sort(Comparator.comparing(Player::getGamesPlayed).reversed());
                break;
            case 3:
                list.sort(Comparator.comparing(Player::getGamesWon).reversed());
                break;
            case 4:
                list.sort(Comparator.comparing(Player::getMultiplayerGamesPlayed).reversed());
                break;
            case 5:
                list.sort(Comparator.comparing(Player::getMultiplayerGamesWon).reversed());
                break;
        }
        return list;
    }

    /**
     * Reloads the list of saved players when needed to update with newly added and removed players
     */
    public void reloadList(){
        gui.resetGrid();
        List<Player> players = sort(Main.inst().getDataHandler().getPlayers());
        for (Player p : players) {
            gui.addGridItem(p.getName(), p.getGamesPlayed(), p.getGamesWon(), p.getMultiplayerGamesPlayed(), p.getMultiplayerGamesWon(), e -> {
                if(highLightedPlayer != null) {
                    gui.highlight(highLightedPlayer.getName(), false);
                }
                gui.highlight(p.getName(), true);
                highLightedPlayer = p;
            });
        }
    }

    public LoadPlayerGUI getLoadPlayerGUI(){
        return gui;
    }

}
