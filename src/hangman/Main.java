package hangman;

import java.io.*;
import java.util.Scanner;

public class Main {

    /* TODO:
    *  Lägga till färg i konsolen.
    * */

    private static String playersFile = "src/hangman/data/players.txt";

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Player player = null; //this variable contains the loaded player.
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

        String menyVal= scan.nextLine();

        switch (menyVal) {
            case "1":
                if(player == null) {
                    createPlayer();
                    return;
                }
                //Start game.
                Game newGame = new Game(player);

                break;
            case "2":

                break;
            case "3":

                break;

            case "4":
                break;

            default:
                System.out.println("Felaktigt val, välj mellan 1-4");
        }
    }

    public static void createPlayer() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(playersFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(playersFile));

            Menu newPlayerMenu = new Menu(new String[]{
                    "Ny spelare vald,",
                    "Mata in namn på spelare",
            });
            newPlayerMenu.show();
            String playerName = newPlayerMenu.getString();
            bw.write(playerName);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadPlayer(String name) {


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

}

