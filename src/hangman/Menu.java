package hangman;

import java.util.Scanner;

public class Menu {

    private String[] menuStrings;

    Scanner input = new Scanner(System.in);

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

    //Hämtar en string från användarens input
    public String getString(){
        String getString = input.nextLine();
        return getString;
    }

    //Hämtar en en bokstav från användaren
    public String getAlpha(){
        return null;
    }

   //Hämtar ett nummer från användaren
    public int getInt(){
        return 0;
    }

}


