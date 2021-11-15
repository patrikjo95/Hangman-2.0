package hangman.data;

import hangman.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiplayerGameData extends GameData {

    private List<Player> players;
    private Player playerTurn;
    private Map<Player, Integer> wrongGuesses;
    private Map<Player, Integer> scores;

    public MultiplayerGameData() {
        players = new ArrayList<>();
        wrongGuesses = new HashMap<>();
        scores = new HashMap<>();
    }

    public MultiplayerGameData(List<Player> players) {
        this.players = new ArrayList<>();
        this.wrongGuesses = new HashMap<>();
        this.scores = new HashMap<>();
        for (Player player : players) {
            addPlayer(player);
        }
    }

    public void addPlayer(Player p) {
        players.add(p);
        wrongGuesses.put(p, 0);
        scores.put(p, 0);
    }

    public void removePlayer(Player p){
        players.remove(p);
        wrongGuesses.remove(p);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayerTurn(){
        return playerTurn;
    }

    public void setPlayerTurn(Player p){
        playerTurn = p;
    }

    public int getWrongGuesses(Player p) {
        return wrongGuesses.get(p);
    }

    public void setWrongGuesses(Player p, int wrongGuesses) {
        this.wrongGuesses.replace(p, wrongGuesses);
    }

    public void increaseWrongGuesses(Player p){
        this.wrongGuesses.replace(p, wrongGuesses.get(p)+1);
    }

    public int getScore(Player p){
        return scores.get(p);
    }

    public void setScore(Player p, int score){
        this.scores.replace(p, score);
    }

    public void increaseScore(Player p){
        this.scores.replace(p, scores.get(p)+1);
    }
}
