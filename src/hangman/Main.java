package hangman;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Main {

    /* TODO:
    *  Lägga till färg i konsolen.
    *  När vi får tillbaka uppgiften så kanske vi kan göra egna exception som: playerdamageexception typ.
    * */

    private static String playersFile = "src/hangman/data/players.txt";

    private static Player player = null; //this variable contains the loaded player.

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Menu startMenu = new Menu(null);

//en for loop som loopar igenom alla strängar i arrayen och printar ut dem.

        System.out.println("* Välkomen till Defenders hangman *");
        System.out.print("Inladdad spelare: ");
        if(player == null) {
            System.out.println("ingen");
        }else {
            System.out.println(player.getPlayerName());
        }
        System.out.println("Välj ett av nedanstående alternativ:");
        System.out.println("1) Spela");
        System.out.println("2) Ladda in spelare");
        System.out.println("3) Spara spelare");
        System.out.println("4) Avsluta spelet");

        do{
            switch (scan.nextLine()) {
                case "1":
                    if (player == null) {
                        createPlayer();
                        return;
                    }
                    //Start game.
                    Game newGame = new Game(player);

                    break;
                case "2":
                    loadPlayer();
                    break;
                case "3":

                    break;

                case "4":
                    break;

                default:
                    System.out.println("Felaktigt val, välj mellan 1-4");
            }
        }while(scan.hasNextLine());
    }

    public static void createPlayer() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(playersFile, true));
            Scanner input = new Scanner(new File(playersFile));

            Menu newPlayerMenu = new Menu(new String[]{
                    "Ny spelare vald,",
                    "Mata in namn på spelare",
            });
            newPlayerMenu.show();
            String playerName = newPlayerMenu.getString();

            String line = "";
            while(input.hasNextLine()) {
                line = input.nextLine();
                if (getFixedPlayerName(line).equals(playerName)) {
                    System.out.println("Namn finns,");
                    System.out.println("Vänligen välj ett annat namn");
                    playerName = newPlayerMenu.getString();
                    input = new Scanner(new File(playersFile)); //Vi kommer att förbättra sen(Medveten ineffektivt)
                }
            }
            playerName += ":<gamesPlayed:0,gamesWon:0>";
            bw.write(playerName);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPlayer() {
        try {
            Scanner input = new Scanner(new File(playersFile));

            Menu loadPlayerMenu = new Menu(new String[]{
                    "Vilken spelare vill du ladda in?"
            });
            loadPlayerMenu.show();

            String playerName = loadPlayerMenu.getString();

            String line = "";
            while(input.hasNextLine()) {
                line = input.nextLine();
                if(getFixedPlayerName(line).equals(playerName)) {
                    player = new Player();
                    player.setPlayerName(getFixedPlayerName(line));
                    player.setGamesWon(getFixedPlayerWins(line));
                    player.setGamesPlayed(getFixedPlayerGamesPlayed(line));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
* player1[gamesWon:0,gamesPlayed:0]
* player1:
* gamesWon: 0
* gamesPlayed: 0
* gamesLost: 0
* */
    public static void savePlayer(){

    }

    public static void deletePlayer() {

    }

    private static String getFixedPlayerName(String input) {
        return input.split(":")[0];
    }

    private static int getFixedPlayerWins(String input){
        String content = input.split("<|>")[1];
        String wins = content.split(",")[1].split(":")[1];
        try{
            return Integer.parseInt(wins);
        }catch(Exception e) {
            System.out.println("Något fel hände med inladdning av spelarvinsterna.");
        }
        return -1;
    }

    private static int getFixedPlayerGamesPlayed(String input){
        String content = input.split("<|>")[1];
        String gamesPlayed = content.split(",")[0].split(":")[1];
        try{
            return Integer.parseInt(gamesPlayed);
        }catch(Exception e) {
            System.out.println("Något fel hände med inladdning av spelade matcher.");
        }
        return -1;
    }

}

