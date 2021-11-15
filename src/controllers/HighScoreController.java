package controllers;

import gui.GUI;
import gui.HighScoreGUI;
import gui.MainMenuGUI;
import hangman.Main;
import hangman.Player;

import java.util.Comparator;
import java.util.List;

public class HighScoreController {

    private static HighScoreController instance = new HighScoreController();

    private HighScoreController(){
        setup();
    }

    private void setup(){
        HighScoreGUI.inst().setBackListener(e -> {
            GUI.inst().open(MainMenuGUI.inst().getScene());
        });
    }

    public static HighScoreController inst(){
        return instance;
    }

    /**
     * Updates the highscore list with info from playerfile and sets the respective label to the players.
     */
    public void updateList(){
        List<Player> players = Main.inst().getDataHandler().getPlayers();
        players.sort(Comparator.comparing(Player::getMultiPlayerHighestScore).reversed());
        HighScoreGUI.inst().resetList();
        try{
            HighScoreGUI.inst().setFirstPlace(players.get(0).getName(), players.get(0).getMultiPlayerHighestScore());
            HighScoreGUI.inst().setSecondPlace(players.get(1).getName(), players.get(1).getMultiPlayerHighestScore());
            HighScoreGUI.inst().setThirdPlace(players.get(2).getName(), players.get(2).getMultiPlayerHighestScore());
            HighScoreGUI.inst().setFourthPlace(players.get(3).getName(), players.get(3).getMultiPlayerHighestScore());
            HighScoreGUI.inst().setFifthPlace(players.get(4).getName(), players.get(4).getMultiPlayerHighestScore());
        }catch(Exception e){
            //Not enough players to fill the highscore list
        }
    }
}
