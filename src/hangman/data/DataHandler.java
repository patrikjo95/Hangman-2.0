package hangman.data;

import hangman.Main;
import hangman.MultiplayerGame;
import hangman.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DataHandler {

    private static final String playersFile = "src/hangman/data/players.txt";
    private static final String wordsFile = "src/hangman/data/words.txt";
    private static final String multiplayerGameFile = "src/hangman/data/multiplayerGame.txt";

    private List<Player> players;

    private Player loadedPlayer;

    public DataHandler() {
        players = new ArrayList<>();
        loadedPlayer = null;
        loadPlayers();
    }

    public Player getLoadedPlayer() {
        return loadedPlayer;
    }

    public void setLoadedPlayer(Player p) {
        loadedPlayer = p;
    }

    private void loadPlayers(){
        try {
            Scanner input = new Scanner(new File(playersFile));
            while (input.hasNextLine()) {
                players.add(convertStringToPlayer(input.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Player createPlayer(String playerName) {
        Player player = new Player();
        player.setName(playerName);
        player.setGamesPlayed(0);
        player.setGamesWon(0);

        players.add(player);

        writePlayerListToFile(players);
        return player;
    }

    public void savePlayers() {
        writePlayerListToFile(players);
    }

    public void deletePlayer(Player player) {
        for(int i = 0; i<players.size(); i++){
            if(players.get(i) == player){
                players.remove(i);
                break;
            }
        }
        writePlayerListToFile(players);
    }

    public void saveMultiplayerGame(MultiplayerGame game) {
        writeMultiplayerGameToFile(game);
    }

    public MultiplayerGame loadPreviousMultiplayerGame(){
        try {
            Scanner input = new Scanner(new File(multiplayerGameFile));
            if(!input.hasNextLine()){
                return null;
            }
            return convertStringToMultiplayerGame(input.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isPlayerNameValid(String name) {
        return name.matches("[a-zA-ZåäöÅÄÖ0-9]+");
    }

    public boolean isPlayerNameAvailable(String name) {
        for (Player player : getPlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasStoreMultiplayerGame(){
        return loadPreviousMultiplayerGame() != null;
    }

    public void clearMultiplayerSaveFile(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(multiplayerGameFile));
            bw.write("");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return list of players from players.txt file.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Writes all players into the file players.txt as a list.
     *
     * @param players
     */
    private void writePlayerListToFile(List<Player> players) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(playersFile));
            for (Player p : players) {
                bw.write(convertPlayerToString(p));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeMultiplayerGameToFile(MultiplayerGame game) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(multiplayerGameFile));
            bw.write(convertMultiplayerGameToString(game.getGameData()));
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the player to a string to make it saveable.
     */
    private String convertPlayerToString(Player player) {
        return player.getName() + ":<gamesPlayed:" + player.getGamesPlayed() +
                ",gamesWon:" + player.getGamesWon() +
                ",multiplayerGamesPlayed:" + player.getMultiplayerGamesPlayed() +
                ",multiplayerGamesWon:" + player.getMultiplayerGamesWon() +
                ",multiplayerHighestScore:" + player.getMultiPlayerHighestScore() + ">";
    }

    /**
     * Converts the stored player from a string to a playable player
     * @param storedPlayer
     * @return
     */
    private Player convertStringToPlayer(String storedPlayer) {
        try {
            String name = getFixedPlayerName(storedPlayer);
            int gamesPlayed = getFixedPlayerGamesPlayed(storedPlayer);
            int wins = getFixedPlayerWins(storedPlayer);
            int multiplayerGamesPlayed = getFixedPlayerMultiplayerGamesPlayed(storedPlayer);
            int multiplayerWins = getFixedPlayerMultiplayerWins(storedPlayer);
            int multiplayerHighestScore = getFixedPlayerMultiplayerHighestScore(storedPlayer);
            Player p = new Player(name);
            p.setGamesPlayed(gamesPlayed);
            p.setGamesWon(wins);
            p.setMultiplayerGamesPlayed(multiplayerGamesPlayed);
            p.setMultiplayerGamesWon(multiplayerWins);
            p.setMultiplayerHighestScore(multiplayerHighestScore);
            return p;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Converts the ongoing multiplayer game to a saveable string
     * @param data
     * @return
     */
    private String convertMultiplayerGameToString(MultiplayerGameData data) {
        String dataString = "";
        dataString += "<gameWord>" + data.getGameWord() + "</gameWord>";
        dataString += "<guessedLetters>";
        for (Character character : data.getLettersGuessed()) {
            dataString += character;
        }
        dataString += "</guessedLetters>";
        dataString += "<playerTurn>" + data.getPlayerTurn().getName() + "</playerTurn>";
        dataString += "<players>";
        for (Player player : data.getPlayers()) {
            dataString += player.getName() + ":" + data.getScore(player) + ":" + data.getWrongGuesses(player) + (data.getPlayers().indexOf(player) == data.getPlayers().size()-1 ? "" : ",");
        }
        dataString += "</players>";
        return dataString;
    }

    /**
     * Converts the stored multiplayer game string to a playable multiplayer game
     * @param dataString
     * @return
     */
    private MultiplayerGame convertStringToMultiplayerGame(String dataString){
        MultiplayerGame game = Main.inst().getGameManager().createMultiplayerGame();

        String gameWord = dataString.split("<gameWord>|</gameWord>")[1];
        List<Character> guessedLetters = new ArrayList<>();
        for(char c : dataString.split("<guessedLetters>|</guessedLetters>")[1].toCharArray()){
            guessedLetters.add(c);
        }
        String playerTurnName = dataString.split("<playerTurn>|</playerTurn>")[1];
        List<String> playerDataList = new ArrayList(Arrays.asList(dataString.split("<players>|</players>")[1].split(",")));
        for (String playerData : playerDataList) {
            String playerName = playerData.split(":")[0];
            Player player = null;
            for (Player p : getPlayers()) {
                if(p.getName().equalsIgnoreCase(playerName)){
                    player = p;
                }
            }
            game.addPlayer(player);
            game.getGameData().setScore(player, Integer.parseInt(playerData.split(":")[1]));
            game.getGameData().setWrongGuesses(player, Integer.parseInt(playerData.split(":")[2]));
            if(playerName.equalsIgnoreCase(playerTurnName)){
                game.setPlayerTurn(player);
            }
        }
        game.getGameData().setGameWord(gameWord);
        game.getGameData().setGuessedLetters(guessedLetters);
        return game;
    }

    /**
     * Gets the player name from the player data
     */
    private String getFixedPlayerName(String input) {
        return input.split(":")[0];
    }

    /**
     * Gets the games played from the player data
     */
    private int getFixedPlayerGamesPlayed(String input) throws Exception {
        String content = input.split("<|>")[1];
        String gamesPlayed = content.split(",")[0].split(":")[1];
        return Integer.parseInt(gamesPlayed);
    }

    /**
     * Gets the player wins from the player data
     */
    private int getFixedPlayerWins(String input) throws Exception {
        String content = input.split("<|>")[1];
        String wins = content.split(",")[1].split(":")[1];
        return Integer.parseInt(wins);
    }
    /**
     * Gets the multiplayer games played from the player data
     */
    private int getFixedPlayerMultiplayerGamesPlayed(String input) throws Exception {
        String content = input.split("<|>")[1];
        String gamesPlayed = content.split(",")[2].split(":")[1];
        return Integer.parseInt(gamesPlayed);
    }
    /**
     * Gets the multiplayer wins from the player data
     */
    private int getFixedPlayerMultiplayerWins(String input) throws Exception {
        String content = input.split("<|>")[1];
        String gamesPlayed = content.split(",")[3].split(":")[1];
        return Integer.parseInt(gamesPlayed);
    }
    /**
     * Gets the multiplayer score from the player data
     */
    private int getFixedPlayerMultiplayerHighestScore(String input) throws Exception {
        String content = input.split("<|>")[1];
        String gamesPlayed = content.split(",")[4].split(":")[1];
        return Integer.parseInt(gamesPlayed);
    }

    /**
     * Gets the wordlist which is used for the gameplay to randomize a word
     */
    public String[] getWordsFromStorage() {
        List<String> list = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(wordsFile));
            while (input.hasNextLine()) {
                String word = input.nextLine();
                if (!word.equals("")) {
                    list.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list.toArray(new String[list.size()]);
    }

}
