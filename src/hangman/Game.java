package hangman;

import hangman.data.GameData;

import java.util.Random;

public abstract class Game {

    public static final int MAX_WRONG_GUESSES = 10;

    public Game() {
    }

    public abstract GameData getGameData();
    public abstract void restart();

    public boolean isValidLetter(char alpha) {
        return String.valueOf(alpha).matches("[a-zA-ZåäöÅÄÖ]+");
    }

    public boolean isLetterGuessed(char letter) {
        return getGameData().getLettersGuessed().contains(letter);
    }

    public boolean isLetterCorrect(char letter) {
        return getGameData().getGameWord().indexOf(letter) != -1;
    }

    /**
     * Generates a random word from our file of words.
     */
    public void generateWord() {
        Random random = new Random();
        int randomInt = random.nextInt(getGameData().getWordList().length);
        getGameData().setGameWord(getGameData().getWordList()[randomInt]);
    }

    public void resetGuesses(){
        getGameData().resetGuesses();
    }


    /**
     * @return amount of times the player has guessed and adds the letter.
     */
    public String getMadeGuesses() {
        String madeGuesses = "";
        for (char letterGuessed : getGameData().getLettersGuessed()) {
            if (letterGuessed == 0) {
                continue;
            }
            madeGuesses += letterGuessed + " ";
        }
        return madeGuesses;
    }


    /**
     * @return the word as underscores and updates as the player guesses the right letter.
     */
    public String getWordRevelation() {
        String wordRevelation = "";
        for (char letter : getGameData().getGameWord().toCharArray()) {
            if (getGameData().getLettersGuessed().isEmpty()) {
                wordRevelation += "_ ";
                continue;
            }
            for (int i = 0; i < getGameData().getLettersGuessed().size(); i++) {
                if (getGameData().getLettersGuessed().get(i) == letter) {
                    wordRevelation += letter;
                    break;
                }
                if (i == getGameData().getLettersGuessed().size() - 1) {
                    wordRevelation += "_ ";
                }
            }
        }
        return wordRevelation;
    }
}

