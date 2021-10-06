package hangman;

import java.util.Scanner;

public class Main {

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
                    System.out.println("Ny spelare vald, vänligen skriv in ditt namn:");
                    player = new Player();
                    player.setPlayerName(startMenu.getString());
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
}

