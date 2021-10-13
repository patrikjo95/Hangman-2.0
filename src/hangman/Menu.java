package hangman;

import java.util.Scanner;

public class Menu {

    private String[] menuStrings;

    private Scanner input = new Scanner(System.in);

    //arrayvariabel, innehåller massa stränger som ska skrivas ut i show()

    //ta in en array av meny strängar
    public Menu(String[] menuStrings){
        this.menuStrings = menuStrings;
    }

    //Skriver ut menyn för spelet
    public void show(){
        //en for loop som loopar igenom alla strängar i arrayen och printar ut dem.
        for(int i = 0; i < menuStrings.length; i++){
            System.out.println(menuStrings[i]);
        }

    }

    public void updateMenu(String[] menuStrings) {
        this.menuStrings = menuStrings;
    }

    //Hämtar en string från användarens input
    public String getString(){
        return input.nextLine();
    }

    //Hämtar en en bokstav från användaren
    public String getAlpha(){
        String str = input.nextLine();
        if(!str.matches("[a-zA-ZåäöÅÄÖ]+")){
            System.out.println("Du kan bara mata in bokstäver.");
            return getAlpha();
        }
        if(str.length() > 1){
            System.out.println("Du kan bara mata in en bokstav i taget, försök igen.");
            return getAlpha();
        }

        return str;
    }

    //Denna metod är värdelös.
    public int getInt(){
        return 0;
    }

}


