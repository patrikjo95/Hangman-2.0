package hangman;

import hangman.data.DataHandler;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.*;
import java.util.Scanner;

public class Main {

    /* TODO:
     *  När vi får tillbaka uppgiften så kanske vi kan göra egna exception som: playerfiledamageexception typ.
     *  Kommentera koden.
     * */

    public static void main(String[] args) {

        Player player = null; //this variable contains the loaded player.
        Menu startMenu = new Menu(null);
        DataHandler dataHandler = new DataHandler();

        boolean running = true;

        do {

            startMenu.updateMenu(new String[]{
                    Color.PURPLE + "* Välkomen till Defenders hangman *" + Color.RESET,
                    "Inladdad spelare: " + Color.BLUE + (player == null ? "Ingen" : player.getName()) + Color.RESET,
                    "Välj ett av nedanstående alternativ med respektive " + Color.YELLOW + "nummer:" + Color.RESET,
                    Color.YELLOW + "1) " + Color.RESET + "Spela",
                    Color.YELLOW + "2) " + Color.RESET + "Ladda in spelare",
                    Color.YELLOW + "3) " + Color.RESET + "Spara spelare",
                    Color.YELLOW + "4) " + Color.RESET + "Ta bort inladdad spelare",
                    Color.YELLOW + "5) " + Color.RESET + "Avsluta spelet"
            });
            startMenu.show();

            switch (startMenu.getString()) {
                case "1":
                    if (player == null) {
                        Player newPlayer = dataHandler.createPlayer();
                        if(newPlayer == null){
                            break;
                        }else{
                            player = newPlayer;
                        }
                    }
                    //Start game.
                    Game newGame = new Game(player, dataHandler.getWordsFromStorage());
                    break;
                case "2":
                    Player loadedPlayer = dataHandler.loadPlayer();
                    player = loadedPlayer == null ? player : loadedPlayer;
                    break;
                case "3":
                    dataHandler.savePlayer(player);
                    break;

                case "4":
                    dataHandler.deletePlayer(player);
                    player = null;
                    break;

                case "5":
                    System.out.println(Color.CYAN + "Avslutar spelet.");
                    System.out.println("Hade gött, hej!");
                    running = false;
                    break;

                default:
                    System.out.println(Color.RED + "Felaktigt val, välj mellan 1-5" + Color.RESET);
            }
        } while (running);
    }

}

