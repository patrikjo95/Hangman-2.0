package hangman.data;

import java.sql.Wrapper;

public class SingleplayerGameData extends GameData {

    private int wrongGuesses = 0;

    public SingleplayerGameData() {

    }

    public int getWrongGuesses(){
        return wrongGuesses;
    }

    public void setWrongGuesses(int wrongGuesses){
         this.wrongGuesses = wrongGuesses;
    }

    public void increaseWrongGuesses(){
        wrongGuesses++;
    }
}
