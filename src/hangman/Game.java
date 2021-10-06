package hangman;

public class Game {

    /*
    * Att göra:
    * Skapa en randomizer som slumpar ett ord
    * fixa bokstavsräknaren för ordet
    * mata ut antal fel gissningar
    * ta in input från spelare och hantera det
    * fixa en updateArray metod i Menu för att kunna få de senaste värdena till arrayen.
    *
    * */

    private String[] gameMenuArray;
    private Menu gameMenu;
    private Player player;
    public Game(Player player){
        this.player = player;

        gameMenuArray = new String[]{
                "Hej " + player.getPlayerName() + "!" + " jag tänker på ett ord som har: " /* + Randomord*/ + "bokstäver",
                "Så här långt har du gissat: "  /* + bokstavsräknare*/,
                "Du har gissat på följande bokstäver: " /* + gissade bokstäver*/,
                "Hängmätare: " /* + antal felaktiga försök*/,
                "Vilken bokstav vill du gissa på?"
        };

        gameMenu = new Menu(gameMenuArray);
        gameMenu.show();
    }

    public void showGame() {
        //Skriva ut spelet i konsolen
    }

   public void update(String alpha) {
        //Uppdaterar spelet efter användarens inmatning

   }

}
