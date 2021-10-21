package hangman;

public class Player {

    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private String playerName = "";

    public Player() {

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

    public int getGamesLost() {
        return gamesPlayed - gamesWon;
    }

}




