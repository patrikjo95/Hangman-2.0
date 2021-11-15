package gui;

import controllers.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class GUI extends Application {

    private static GUI instance;

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        if(instance != null){
            throw new RuntimeException("Cant make more than one instance of GUI class.");
        }
        instance = this;
        this.stage = stage;
        stage.setTitle("Defender's Hangman");
        stage.getIcons().add(new Image(new File("src/images/hangmanStages/hangman_10.png").toURI().toString()));

        //In need to instantiate singleton.
        MainMenuController.inst();
        SingleplayerMenuController.inst();
        SingleplayerGameController.inst();
        LoadPlayerController.inst();
        MultiplayerGameController.inst();
        HighScoreController.inst();
        //

        open(MainMenuGUI.inst().getScene());
        stage.show();
    }

    public static GUI inst(){
        return instance;
    }

    public GUI(){

    }

    public Stage getStage(){
        return stage;
    }

    public void open(Scene scene) {
        stage.setScene(scene);
        stage.centerOnScreen();
    }

}
