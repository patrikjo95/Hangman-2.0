package controllers;

import gui.GUI;
import gui.MainMenuGUI;
import gui.MultiplayerGUI;
import gui.popup.InputPopupGUI;
import gui.popup.PlayAgainPopupGUI;
import hangman.Main;
import hangman.MultiplayerGame;
import hangman.Player;
import javafx.scene.paint.Color;

public class MultiplayerGameController  {

    private static MultiplayerGameController instance = new MultiplayerGameController();

    private MultiplayerGame game;

    public MultiplayerGameController(){
        setup();
    }

    public static MultiplayerGameController inst(){
        return instance;
    }

    public MultiplayerGame getGame(){
        return game;
    }

    public void setGame(MultiplayerGame game){
        this.game = game;
        MultiplayerGUI.inst().setupLobby();
        MultiplayerGUI.inst().resetBoard();
        updateStartButton();
    }

    /**
     * loads a saved game
     * */
    public void loadGame(MultiplayerGame game) {
        this.game = game;
        for (Player player : game.getPlayers()) {
            createPlayerBox(player);
            MultiplayerGUI.inst().setScore(player.getName(), game.getGameData().getScore(player));
            MultiplayerGUI.inst().setHangmanStage(player.getName(), game.getGameData().getWrongGuesses(player));
        }
        MultiplayerGUI.inst().setupGame();
        MultiplayerGUI.inst().setWordRevelation(game.getWordRevelation());
        MultiplayerGUI.inst().setGuessedLettersLabel(game.getMadeGuesses());
        MultiplayerGUI.inst().highlightPlayer(game.getPlayerTurn().getName(), true);
    }

    private void setup(){
        MultiplayerGUI.inst().setCreatePlayerListener(e -> {
            InputPopupGUI popup = new InputPopupGUI();
            popup.setListener(e1 -> {
                String input = popup.getInput();
                if(input.equals("")){
                    popup.setError("You need to enter a name");
                    return;
                }
                if(!Main.inst().getDataHandler().isPlayerNameValid(input)){
                    popup.setError("You are using invalid characters");
                    return;
                }
                if(!Main.inst().getDataHandler().isPlayerNameAvailable(input)){
                    popup.setError("Player name already exists");
                    return;
                }
                Player p = Main.inst().getDataHandler().createPlayer(input);
                addPlayer(p);
                Main.inst().getDataHandler().savePlayers();
                popup.close();
                updateStartButton();
            });
            popup.show();
        });
        MultiplayerGUI.inst().setLoadPlayerListener(e -> {
            LoadMultiplayerPlayerController.inst().reloadList();
            GUI.inst().open(LoadMultiplayerPlayerController.inst().getLoadPlayerGUI().getScene());
        });
        MultiplayerGUI.inst().setStartGameListener(e -> {
            game.randomizeTurnOrder();
            MultiplayerGUI.inst().resetBoard();
            for (Player player : game.getPlayers()) {
                MultiplayerGUI.inst().addPlayer(player.getName(), null);
            }
            MultiplayerGUI.inst().setupGame();
            setupNewGameWord();
            game.nextTurn();
            MultiplayerGUI.inst().highlightPlayer(game.getPlayerTurn().getName(), true);
        });
        MultiplayerGUI.inst().setEnterListener(e -> {
            MultiplayerGUI.inst().getTextField().requestFocus();
            if (MultiplayerGUI.inst().getInput().equals("")) {
                return;
            }
            char letter = MultiplayerGUI.inst().getInput().charAt(0);
            if (!game.isValidLetter(letter)) {
                return;
            }

            if (game.isLetterGuessed(letter)) {
                return;
            }
            game.getGameData().addGuessedLetter(letter);

            if (!game.isLetterCorrect(letter)) {
                MultiplayerGUI.inst().setGuessedLettersLabel(game.getMadeGuesses());
                game.getGameData().increaseWrongGuesses(game.getPlayerTurn());
                MultiplayerGUI.inst().setHangmanStage(game.getPlayerTurn().getName(), game.getGameData().getWrongGuesses(game.getPlayerTurn()));
                MultiplayerGUI.inst().highlightPlayer(game.getPlayerTurn().getName(), false);
                game.nextTurn();
                MultiplayerGUI.inst().highlightPlayer(game.getPlayerTurn().getName(), true);
            }else{
                MultiplayerGUI.inst().setWordRevelation(game.getWordRevelation());
                MultiplayerGUI.inst().setGuessedLettersLabel(game.getMadeGuesses());
                game.getGameData().increaseScore(getGame().getPlayerTurn());
                MultiplayerGUI.inst().setScore(game.getPlayerTurn().getName(), game.getGameData().getScore(game.getPlayerTurn()));
            }

            MultiplayerGUI.inst().resetTextField();

            if(game.getWordRevelation().equals(game.getGameData().getGameWord())){
                game.getGameData().setScore(game.getPlayerTurn(), game.getGameData().getScore(game.getPlayerTurn())+2);
                MultiplayerGUI.inst().setScore(game.getPlayerTurn().getName(), game.getGameData().getScore(game.getPlayerTurn()));
                setupNewGameWord();
            }

            if(isGameOver()){
                handleGameOver();
                MultiplayerGUI.inst().setWordRevelation(game.getGameData().getGameWord());
                MultiplayerGUI.inst().getWordLabel().setTextFill(Color.RED);
            }
        });

        MultiplayerGUI.inst().setExitButtonListener(e ->{
            GUI.inst().open(MainMenuGUI.inst().getScene());
            MultiplayerGUI.inst().resetBoard();
            MultiplayerGUI.inst().setupLobby();
        });
        MultiplayerGUI.inst().setSaveGameListener(e ->{
            if(game.getPlayerTurn() == null){ //Bad way to check if the game has started.
                return;
            }
            Main.inst().getDataHandler().saveMultiplayerGame(game);
        });
        MultiplayerGUI.inst().setSaveAndExitListener(e ->{
            if(game.getPlayerTurn() == null){ //Bad way to check if the game has started.
                return;
            }
            Main.inst().getDataHandler().saveMultiplayerGame(game);
            GUI.inst().open(MainMenuGUI.inst().getScene());
            MultiplayerGUI.inst().resetBoard();
            MultiplayerGUI.inst().setupLobby();
        });
    }

    /**
     * Handles stats for players when game is over and creates play again popup window
     */
    private void handleGameOver(){
        Player winner = game.getPlayers().get(0);
        for (Player player : game.getPlayers()) {
            if(game.getGameData().getScore(player) > game.getGameData().getScore(winner)){
                winner = player;
            }
            if(player.getMultiPlayerHighestScore() < game.getGameData().getScore(player)){
                player.setMultiplayerHighestScore(game.getGameData().getScore(player));
            }
            player.increaseMultiplayerGamesPlayed();
        }
        winner.increaseMultiplayerGamesWon();
        Main.inst().getDataHandler().savePlayers();


        PlayAgainPopupGUI popup = new PlayAgainPopupGUI();
        popup.setWinnerLabel(winner.getName());
        popup.setPlayAgainListener(e -> {
            popup.close();
            restartGame();
        });
        popup.setMainMenuListener(e -> {
            popup.close();
            GUI.inst().open(MainMenuGUI.inst().getScene());
            MultiplayerGUI.inst().resetBoard();
        });
        popup.show();
        Main.inst().getDataHandler().clearMultiplayerSaveFile();
    }

    /**
     * Adds player to the game.
     * @return <strong>true</strong> if the player got added.<br>
     *         <strong>false</strong> if the player already is in the game.
     * */
    public boolean addPlayer(Player p){
        if(game.getPlayers().contains(p)){
            return false;
        }
        game.addPlayer(p);
        createPlayerBox(p);
        return true;
    }

    /**
     * Creates a new playerbox with hangman window when add player button is clicked.
     * Disables add player button when MAX_PLAYERS is reached.
     * @param p
     */
    public void createPlayerBox(Player p) {
        MultiplayerGUI.inst().addPlayer(p.getName(), e -> {
            game.removePlayer(p);
            MultiplayerGUI.inst().remove(p.getName());
            MultiplayerGUI.inst().getAddPlayerButton().setDisable(false);
            MultiplayerGUI.inst().getCreatePlayerButton().setDisable(false);
            updateStartButton();
        });
        if(game.getPlayers().size() >= MultiplayerGame.MAX_PLAYERS){
            MultiplayerGUI.inst().getAddPlayerButton().setDisable(true);
            MultiplayerGUI.inst().getCreatePlayerButton().setDisable(true);
        }
        updateStartButton();
    }

    public void restartGame(){
        game.restart();
        MultiplayerGUI.inst().setupGame();
        MultiplayerGUI.inst().restart();
        MultiplayerGUI.inst().setGuessedLettersLabel(game.getMadeGuesses());
        MultiplayerGUI.inst().setWordRevelation(game.getWordRevelation());
    }

    /**
     * Checks for alive players to know when game is over
     * @return aliveCount
     */
    public boolean isGameOver(){
        int aliveCount = 0;
        for(Player p : game.getPlayers()){
            if(!game.isPlayerDead(p)){
                aliveCount++;
            }
        }
        return aliveCount == 0;
    }

    /**
     * Makes Start button klickable when there is 2 or more players in the game.
     */
    private void updateStartButton(){
        if(game.getPlayers().size() >= 2){
            MultiplayerGUI.inst().getStartButton().setDisable(false);
        }else{
            MultiplayerGUI.inst().getStartButton().setDisable(true);
        }
    }

    private void setupNewGameWord(){
        game.generateWord();
        game.resetGuesses();
        MultiplayerGUI.inst().setGuessedLettersLabel(game.getMadeGuesses());
        MultiplayerGUI.inst().setWordRevelation(game.getWordRevelation());
    }

}
