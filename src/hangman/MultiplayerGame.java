package hangman;

import hangman.data.GameData;
import hangman.data.MultiplayerGameData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MultiplayerGame extends Game {

    public static int MAX_PLAYERS = 4;

    private MultiplayerGameData gameData;

    public MultiplayerGame(MultiplayerGameData gameData) {
        this.gameData = gameData;
    }

    public void addPlayer(Player p) {
        gameData.addPlayer(p);
    }

    public void removePlayer(Player p) {
        gameData.removePlayer(p);
    }

    public List<Player> getPlayers() {
        return gameData.getPlayers();
    }

    public Player getPlayerTurn() {
        return gameData.getPlayerTurn();
    }

    public void setPlayerTurn(Player p) {
        gameData.setPlayerTurn(p);
    }

    public void randomizeTurnOrder() {
        if (gameData.getPlayers().size() <= 1) {
            return;
        }
        Collections.shuffle(gameData.getPlayers());
    }

    public void nextTurn() {
        for (Player p : getPlayers()) {
            if (!isPlayerDead(p)) {
                break;
            }
            if (getPlayers().indexOf(p) == getPlayers().size() - 1) {
                return;
            }
        }
        if (getPlayerTurn() == null) {
            setPlayerTurn(getPlayers().get(0));
        } else if (getPlayers().indexOf(getPlayerTurn()) + 1 >= getPlayers().size()) {
            setPlayerTurn(getPlayers().get(0));
        } else {
            setPlayerTurn(getPlayers().get(getPlayers().indexOf(getPlayerTurn()) + 1));
        }
        if (isPlayerDead(getPlayerTurn())) {
            nextTurn();
        }
    }

    public boolean isPlayerDead(Player p) {
        if (gameData.getWrongGuesses(p) >= MAX_WRONG_GUESSES) {
            return true;
        }
        return false;
    }

    @Override
    public MultiplayerGameData getGameData() {
        return gameData;
    }

    @Override
    public void restart() {
        gameData = new MultiplayerGameData(gameData.getPlayers());
        generateWord();
        nextTurn();
    }
}
