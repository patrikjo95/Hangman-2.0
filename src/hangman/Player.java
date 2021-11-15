package hangman;

public class Player {

    private String playerName = "";

    private int gamesPlayed = 0;
    private int gamesWon = 0;

    private int multiplayerGamesPlayed = 0;
    private int multiplayerGamesWon = 0;
    private int multiplayerHighestScore = 0;

    public Player() {

    }

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getName() {
        return playerName;
    }

    public void setName(String playerName) {
        this.playerName = playerName;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void increaseGamesPlayed(){
        gamesPlayed++;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void increaseGamesWon(){
        gamesWon++;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getMultiplayerGamesPlayed(){
        return multiplayerGamesPlayed;
    }

    public void increaseMultiplayerGamesPlayed(){
        multiplayerGamesPlayed++;
    }

    public void setMultiplayerGamesPlayed(int multiplayerGamesPlayed) {
        this.multiplayerGamesPlayed = multiplayerGamesPlayed;
    }

    public int getMultiplayerGamesWon(){
        return multiplayerGamesWon;
    }

    public void increaseMultiplayerGamesWon(){
        multiplayerGamesWon++;
    }

    public void setMultiplayerGamesWon(int multiplayerGamesWon){
        this.multiplayerGamesWon = multiplayerGamesWon;
    }

    public int getMultiPlayerHighestScore(){
        return multiplayerHighestScore;
    }

    public void setMultiplayerHighestScore(int multiplayerHighestScore){
        this.multiplayerHighestScore = multiplayerHighestScore;
    }



}




