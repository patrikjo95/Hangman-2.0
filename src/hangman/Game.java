package hangman;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class Game {


    private final int MAX_WRONG_GUESSES = 10;
    private final int MAX_GUESSES = 10000; //Used to initiate the lettersGuessed array, which needs a limit.

    private Menu gameMenu;
    private Player player;

    private String[] gameMenuArray;
    private String[] randomWords;
    private String gameWord;
    private char[] lettersGuessed;
    private int wrongGuesses;

    String[] hangmanStages;

    public Game(Player player, String[] words) {
        this.player = player;
        initiateHangManStages();
        lettersGuessed = new char[MAX_GUESSES];
        randomWords = words;
        gameMenu = new Menu(null);

        runGameLoop();
    }

    /**
     * Prints out the menu and updates after player guesses.
     */
    public void showGame() {
        gameMenuArray = new String[]{
                "Hej " + Color.BLUE + player.getName() + Color.RESET + "!, Jag tänker på ett ord som har: " + gameWord.length() + " bokstäver.",
                "Så här långt har du gissat: " + Color.GREEN + getWordRevelation() + Color.RESET,
                "Du har gissat på följande bokstäver: " + Color.YELLOW + getMadeGuesses() + Color.RESET,
                "Hängmätare: \n" + Color.RED + hangmanStages[wrongGuesses] + Color.RESET,
                "Vilken bokstav vill du gissa på?"
        };
        gameMenu.updateMenu(gameMenuArray);
        gameMenu.show();
    }
    /***
     * Handles the input from the player and updates the game.
     * @param alpha The guessed letter.
     */
   public void update(String alpha) {

        char guess = alpha.toUpperCase().toCharArray()[0];
        for(int i = 0; i < lettersGuessed.length; i++) {
            if (lettersGuessed[i] == guess) { //If the guess already was made.
                System.out.println(Color.RED + "Du har redan gissat på bokstaven " + Color.RESET + guess + Color.RED + ", gissa på en annan bokstav." + Color.RESET);
                System.out.println();
                return;
            }
            if (lettersGuessed[i] == 0) { //If the guess wasn't made.
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

        while(running){
            if(wrongGuesses >= MAX_WRONG_GUESSES) {
                message = new String[]{
                        Color.RED + hangmanStages[wrongGuesses] + Color.RESET,
                        Color.RED + "Tyvärr du gissade fel " + Color.RESET + MAX_WRONG_GUESSES + Color.RED + " gånger." + Color.RESET,
                        "Ordet var: " + Color.GREEN + gameWord + Color.RESET,
                        "Välj nedanstående alternativ med respektive" + Color.YELLOW + "nummer." + Color.RESET,
                        Color.YELLOW + "1) " + Color.RESET + "Spela igen",
                        Color.YELLOW + "2) " + Color.RESET + "Gå till startmenyn"
                };
                running = false;
            }else if(gameWord.equals(getWordRevelation())){
                message = new String[]{
                        Color.GREEN + "Grattis du gissade rätt ord!!" + Color.RESET,
                        "Ordet var: " + Color.GREEN + gameWord + Color.RESET,
                        "Välj nedanstående alternativ med respektive" + Color.YELLOW + "nummer." + Color.RESET,
                        Color.YELLOW + "1) " + Color.RESET + "Spela igen",
                        Color.YELLOW + "2) " + Color.RESET + "Gå till startmenyn"
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
                    //Returns to main.
                    return;
                default:
                    System.out.println(Color.RED + "Felakting inmatning, välj 1 eller 2" + Color.RESET);
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

    /**
     * Generates a random word from our file of words.
     */
    private void generateWord(){
        Random random = new Random();
        int randomInt = random.nextInt(randomWords.length);
        gameWord = randomWords[randomInt];
    }


    /**
     *
     * @return amount of times the player has guessed and adds the letter.
     */
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


    /**
     *
     * @return the word as underscores and updates as the player guesses the right letter.
     */
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

    private void initiateHangManStages(){
        hangmanStages = new String[]{
                "",
                "---------",
                "|\n" +
                "|\n" +
                "|\n" +
                "|\n" +
                "|\n" +
                "---------",
                "---------\n" +
                "|\n" +
                "|\n" +
                "|\n" +
                "|\n" +
                "|\n" +
                "---------",
                "---------\n" +
                "|      |\n" +
                "|\n" +
                "|\n" +
                "|\n" +
                "|\n" +
                "---------",
                "---------\n" +
                "|      |\n" +
                "|      ☹\n" +
                "|\n" +
                "|\n" +
                "|\n" +
                "---------",
                "---------\n" +
                "|      |\n" +
                "|      ☹\n" +
                "|      |\n" +
                "|      |\n" +
                "|\n" +
                "---------",
                "---------\n" +
                "|      |\n" +
                "|      ☹\n" +
                "|      |\\\n" +
                "|      |\n" +
                "|\n" +
                "---------",
                "---------\n" +
                "|      |\n" +
                "|      ☹\n" +
                "|     /|\\\n" +
                "|      |\n" +
                "|\n" +
                "---------",
                "---------\n" +
                "|      |\n" +
                "|      ☹\n" +
                "|     /|\\\n" +
                "|      |\n" +
                "|       \\\n" +
                "---------",
                "---------\n" +
                "|      |\n" +
                "|      ☹\n" +
                "|     /|\\\n" +
                "|      |\n" +
                "|     / \\\n" +
                "---------"
        };
    }
}

