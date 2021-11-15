package hangman.data;

import hangman.Main;

import java.util.ArrayList;
import java.util.List;

public abstract class GameData {

    private String[] wordList;
    private String gameWord;
    private List<Character> lettersGuessed;

    public GameData(){
        this.wordList = Main.inst().getDataHandler().getWordsFromStorage();
        lettersGuessed = new ArrayList<>();
    }

    public String[] getWordList() {
        return wordList;
    }

    public String getGameWord() {
        return gameWord;
    }

    public void setGameWord(String gameWord) {
        this.gameWord = gameWord;
    }

    public List<Character> getLettersGuessed() {
        return lettersGuessed;
    }

    public void resetGuesses(){
        lettersGuessed.clear();
    }

    public void addGuessedLetter(char letter) {
        lettersGuessed.add(letter);
    }

    public void setGuessedLetters(List<Character> letters){
        lettersGuessed = letters;
    }
}
