package controllers;

import gui.GUI;
import hangman.Main;
import gui.MainMenuGUI;
import gui.SingleplayerGUI;
import hangman.Game;
import hangman.SingleplayerGame;
import javafx.scene.paint.Color;

public class SingleplayerGameController {

    private static SingleplayerGameController instance = new SingleplayerGameController();

    private SingleplayerGame game;

    private SingleplayerGameController() {
        setup();
    }

    public static SingleplayerGameController inst() {
        return instance;
    }

    /**
     * Sets up all info concerning new singleplayergame,playername, lives left, gameword and hangmanstage when starting a new game.
     * @param game
     */
    public void startGame(SingleplayerGame game) {
        this.game = game;
        SingleplayerGUI.inst().setupPreGame();
        game.generateWord();
        SingleplayerGUI.inst().setPlayerName(game.getPlayer().getName());
        SingleplayerGUI.inst().setLivesLeft(Game.MAX_WRONG_GUESSES);
        SingleplayerGUI.inst().setWordRevelation(game.getWordRevelation());
        SingleplayerGUI.inst().setGuessedLettersLabel(game.getMadeGuesses());
        SingleplayerGUI.inst().setHangmanStage(0);
    }

    /**
     * Setup for single player game.
     */
    private void setup() {
        SingleplayerGUI.inst().setSubmitListener(e -> {
            SingleplayerGUI.inst().getTextField().requestFocus();
            if (SingleplayerGUI.inst().getInput().equals("")) {
                return;
            }
            char letter = SingleplayerGUI.inst().getInput().charAt(0);
            if (!game.isValidLetter(letter)) {
                return;
            }

            if (game.isLetterGuessed(letter)) {
                return;
            }
            game.getGameData().addGuessedLetter(letter);
            if (!game.isLetterCorrect(letter)) {
                SingleplayerGUI.inst().setGuessedLettersLabel(game.getMadeGuesses());
                game.getGameData().increaseWrongGuesses();
                SingleplayerGUI.inst().setLivesLeft(Game.MAX_WRONG_GUESSES - game.getGameData().getWrongGuesses());
                SingleplayerGUI.inst().setHangmanStage(game.getGameData().getWrongGuesses());
            }else{
                SingleplayerGUI.inst().setWordRevelation(game.getWordRevelation());
                SingleplayerGUI.inst().setGuessedLettersLabel(game.getMadeGuesses());
            }

            SingleplayerGUI.inst().resetTextField();
            if(isGameOver()){
                game.getPlayer().increaseGamesPlayed();
                if(game.getWordRevelation().equals(game.getGameData().getGameWord())){
                    game.getPlayer().increaseGamesWon();
                    SingleplayerGUI.inst().getWordLabel().setTextFill(Color.GREEN);
                }else{
                    SingleplayerGUI.inst().setWordRevelation(game.getGameData().getGameWord());
                    SingleplayerGUI.inst().getWordLabel().setTextFill(Color.RED);
                }
                Main.inst().getDataHandler().savePlayers();
                SingleplayerGUI.inst().setupPostGame();
                return;
            }
        });

        SingleplayerGUI.inst().setPlayAgainListener(e -> {
            startGame(Main.inst().getGameManager().createSingleplayerGame(game.getPlayer()));
        });

        SingleplayerGUI.inst().setMainMenuListener(e -> {
            GUI.inst().open(MainMenuGUI.inst().getScene());
        });
    }

    public boolean isGameOver(){
        if(game.getWordRevelation().equals(game.getGameData().getGameWord())){
            return true;
        }else if(game.getGameData().getWrongGuesses() >= Game.MAX_WRONG_GUESSES) {
            return true;
        }
        return false;
    }

}
