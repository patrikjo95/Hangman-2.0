package hangman;

import hangman.data.MultiplayerGameData;
import hangman.data.SingleplayerGameData;

public class GameManager {

    public SingleplayerGame createSingleplayerGame(Player player){
        return new SingleplayerGame(player, new SingleplayerGameData());
    }

    public MultiplayerGame createMultiplayerGame(){
        return new MultiplayerGame(new MultiplayerGameData());
    }

}
