package hangman;

import hangman.data.GameData;
import hangman.data.SingleplayerGameData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SingleplayerGame extends Game {

    private SingleplayerGameData gameData;

    private Player p;

    public SingleplayerGame(Player p, SingleplayerGameData gameData) {
        this.gameData = gameData;
        this.p = p;
    }

    public Player getPlayer(){
        return p;
    }

    @Override
    public SingleplayerGameData getGameData() {
        return gameData;
    }

    @Override
    public void restart() {
        gameData = new SingleplayerGameData();
        generateWord();
    }
}
