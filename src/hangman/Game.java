package hangman;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class Game {


    private final int MAX_GUESSES = 10;


    private Menu gameMenu;
    private Player player;

    private String[] gameMenuArray;
    private String[] randomWords;
    private String gameWord;
    private char[] lettersGuessed;
    private int wrongGuesses;

    public Game(Player player) {
        this.player = player;
        lettersGuessed = new char[MAX_GUESSES];
        randomWords = new String[]{"OSTKAKA", "JAVA", "SYSTEMUTVECKLARE", "ELEFANT", "UNDULAT", "DISCORD", "NEWTON", "LÖN"};
        gameMenu = new Menu(null);

        runGameLoop();
    }

    public void showGame() {
        //Skriva ut spelet i konsolen
    }

   public void update(String alpha) {
        //Uppdaterar spelet efter användarens inmatning
        char guess = alpha.toUpperCase().toCharArray()[0];
        for(int i = 0; i < lettersGuessed.length; i++) {
            if (lettersGuessed[i] == guess) { //If the guess already was made.
                System.out.println("Du har redan gissat på bokstaven " + guess + ", gissa på en annan bokstav.");
                System.out.println();
                return;
            }
            if (lettersGuessed[i] == 0) { //If the guess wasnt made.
                lettersGuessed[i] = guess;
                break;
            }
        }

        if(gameWord.indexOf(guess) == -1){
            wrongGuesses++;
        }

    }

    /**
     * Contains the gameloop and result-menu
     */
    private void runGameLoop(){

        generateWord();

        String[] message = null;
        boolean running = true;

        //Här kan vi lägga wrongGuesses < MAX_GUESSES i while condition (Medvetet fel).
        while(running){
            if(wrongGuesses >= MAX_GUESSES) {
                message = new String[]{
                        "Tyvärr du gissade fel " + MAX_GUESSES + " gånger",
                        "Ordet var: " + gameWord,
                        "Välj nedanstående alternativ med respektive nummer.",
                        "1) Spela igen",
                        "2) Gå till startmenyn"
                };
                running = false;
            }else if(gameWord.equals(getWordRevelation())){
                message = new String[]{
                        "Grattis du gissade rätt ord",
                        "Ordet var: " + gameWord,
                        "Välj nedanstående alternativ med respektive nummer.",
                        "1) Spela igen",
                        "2) Gå till startmenyn"
                };
                player.increaseGamesWon();
                running = false;
            }else {
                showGame();
                update(gameMenu.getAlpha());
            }
        }

        player.increaseGamesPlayed();
        Menu resultMenu = new Menu(message);
        resultMenu.show();

        running = true;
        while(running) {
            switch (resultMenu.getString()) {
                case "1":
                    resetGameBoard();
                    runGameLoop();
                    return;
                case "2":
                    //Går tillbaka till main själv.
                    return;
                default:
                    System.out.println("Felakting inmatning, välj 1 eller 2");
            }
        }
    }

    /**
     * Clears out previous word and wrong guesses to restart game.
     */
    private void resetGameBoard() {
        lettersGuessed = new char[MAX_GUESSES];
        wrongGuesses = 0;
        generateWord();
    }

    private void generateWord(){
        Random random = new Random();
        int randomInt = random.nextInt(randomWords.length);
        gameWord = randomWords[randomInt];
    }

    private String getMadeGuesses() {
        String madeGuesses = "";
        for (char letterGuessed : lettersGuessed) {
            if (letterGuessed == 0) {
                continue;
            }
            madeGuesses += letterGuessed + " ";
        }
        return madeGuesses;
    }

    private String getFixedFailCount() {
        String fixedFailCount = "";

        for(int i = 0; i < wrongGuesses; i++) {
            fixedFailCount += "* ";
        }

        return fixedFailCount;
    }

    private String getWordRevelation() {
        String wordRevelation = "";
        for (char letter : gameWord.toCharArray()) {
            for (int i = 0; i < lettersGuessed.length; i++) {
                if (lettersGuessed[i] == letter) {
                    wordRevelation += letter;
                    break;
                }
                if (i == lettersGuessed.length - 1) {
                    wordRevelation += "_";
                }
            }
        }
        return wordRevelation;
    }

}

