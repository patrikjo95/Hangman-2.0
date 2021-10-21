package hangman.data;

import hangman.Color;
import hangman.Menu;
import hangman.Player;
import hangman.StringList;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DataHandler {

    private static String playersFile = "src/hangman/data/players.txt";
    private static String wordsFile = "src/hangman/data/words.txt";

    public DataHandler() {

    }

    public Player createPlayer() {
        Menu newPlayerMenu = new Menu(new String[]{
                "Mata in namn på spelare",
                "Skriv " + Color.CYAN + "exit" + Color.RESET + " för att gå till startmenyn."
        });
        newPlayerMenu.show();
        String playerName = newPlayerMenu.getString();

        if (playerName.equalsIgnoreCase("exit")) {
            System.out.println("Går tillbaka till startmenyn.");
            return null;
        }

        if(!playerName.matches("[a-zA-ZåäöÅÄÖ0-9]+")){
            System.out.println(Color.RED + "Man kan bara använda siffror och bokstäver, försök igen." + Color.RESET);
            return createPlayer();
        }

        StringList playersInfo = getPlayerList();

        for (String playerInfo : playersInfo.toArray()) {
            if (getFixedPlayerName(playerInfo).equals(playerName)) {
                System.out.println(Color.RED + "Detta namn finns redan, försök igen." + Color.RESET);
                return createPlayer();
            }
        }

        Player player = new Player();
        player.setName(playerName);
        player.setGamesPlayed(0);
        player.setGamesWon(0);

        playersInfo.add(convertPlayerToString(player));

        writePlayerListToFile(playersInfo);
        return player;
    }

    public Player loadPlayer() {
        Player player = null;

        StringList playersInfo = getPlayerList();

        for(int i = 0; i < playersInfo.toArray().length; i++) {
            String playerInfo = playersInfo.get(i);
            playersInfo.replace(i, "Namn: " + Color.BLUE + getFixedPlayerName(playerInfo) + Color.RESET + " - [Spelade matcher: " + Color.YELLOW + getFixedPlayerGamesPlayed(playerInfo) + Color.RESET + ", Vinster: " + Color.YELLOW + getFixedPlayerWins(playerInfo) + Color.RESET + "]");
        }

        playersInfo.add(0, "Skriv namnet på spelaren du vill välja.");
        playersInfo.add("Skriv " + Color.CYAN + "exit" + Color.RESET + " för att gå till startmenyn.");

        Menu loadPlayerMenu = new Menu(playersInfo.toArray());
        loadPlayerMenu.show();

        String playerName = loadPlayerMenu.getString();

        if (playerName.equalsIgnoreCase("exit")) {
            System.out.println("Går tillbaka till startmenyn.");
            return null;
        }

        for (String playerInfo : getPlayerList().toArray()) {
            if (getFixedPlayerName(playerInfo).equals(playerName)) {
                player = new Player();
                player.setName(getFixedPlayerName(playerInfo));
                player.setGamesWon(getFixedPlayerWins(playerInfo));
                player.setGamesPlayed(getFixedPlayerGamesPlayed(playerInfo));
            }
        }
        if (player == null) {
            System.out.println(Color.RED + "Spelaren finns inte, försök igen." + Color.RESET);
            return loadPlayer();
        }
        return player;
    }

    public void savePlayer(Player player) {
        StringList playersInfo = getPlayerList();
        if(player == null){
            System.out.println(Color.RED + "Det finns ingen spelare att spara." + Color.RESET);
            return;
        }
        for (String playerInfo : playersInfo.toArray()) {
            if (getFixedPlayerName(playerInfo).equals(player.getName())) {
                playersInfo.replace(playerInfo, convertPlayerToString(player));
            }
        }
        writePlayerListToFile(playersInfo);
        System.out.println("Du har sparat spelaren: " + Color.BLUE + player.getName() + Color.RESET);
    }

    public void deletePlayer(Player player) {
        StringList playersInfo = getPlayerList();
        if(player == null){
            System.out.println(Color.RED + "Det finns ingen spelare att ta bort." + Color.RESET);
            return;
        }
        for (String playerInfo : playersInfo.toArray()) {
            if (getFixedPlayerName(playerInfo).equals(player.getName())) {
                playersInfo.remove(playerInfo);
                break;
            }
        }
        writePlayerListToFile(playersInfo);
        System.out.println("Spelare " + Color.BLUE + player.getName() + Color.RESET + " har tagits bort");
    }

    /**
     *
     * @return list of players from players.txt file.
     */
    private StringList getPlayerList() {
        StringList list = new StringList();
        try {
            Scanner input = new Scanner(new File(playersFile));
            while (input.hasNextLine()) {
                list.add(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Writes all players into the file players.txt as a list.
     * @param list
     */
    private void writePlayerListToFile(StringList list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(playersFile));
            for (String playerInfo : list.toArray()) {
                bw.write(playerInfo);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the player to a string to make it saveable.
     */
    private String convertPlayerToString(Player player) {
        return player.getName() + ":<gamesPlayed:" + player.getGamesPlayed() + ",gamesWon:" + player.getGamesWon() + ">";
    }
    /**
     * Gets the player name from the player data
     * */
    private String getFixedPlayerName(String input) {
        return input.split(":")[0];
    }

    /**
     * Gets the player wins from the player data
     * */
    private int getFixedPlayerWins(String input) {
        String content = input.split("<|>")[1];
        String wins = content.split(",")[1].split(":")[1];
        try {
            return Integer.parseInt(wins);
        } catch (Exception e) {
            System.out.println("Något fel hände med inladdning av spelarvinsterna.");
        }
        return -1;
    }

    /**
     * Gets the games played from the player data
     * */
    private int getFixedPlayerGamesPlayed(String input) {
        String content = input.split("<|>")[1];
        String gamesPlayed = content.split(",")[0].split(":")[1];
        try {
            return Integer.parseInt(gamesPlayed);
        } catch (Exception e) {
            System.out.println(Color.RED + "Något fel hände med inladdning av spelade matcher." + Color.RESET);
        }
        return -1;
    }

    /**
     * Gets the wordlist which is used for the gameplay to randomize a word
     * */
    public String[] getWordsFromStorage(){
        StringList list = new StringList();
        try {
            Scanner input = new Scanner(new File(wordsFile));
            while(input.hasNextLine()){
                list.add(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list.toArray();
    }

}
