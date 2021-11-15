package gui;

import gui.utils.ButtonUtils;
import hangman.SingleplayerGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SingleplayerMenuGUI {

    private static SingleplayerMenuGUI instance = new SingleplayerMenuGUI();

    private Scene scene;
    private BorderPane root;

    private VBox vbox;

    private Label loadedPlayerLabel;

    private Button startButton;
    private Button createPlayerButton;
    private Button loadPlayerButton;
    private Button backButton;

    private SingleplayerMenuGUI(){
        root = new BorderPane();
        scene = new Scene(root, 500, 400);
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));


        vbox = new VBox(5);
        loadedPlayerLabel = new Label();
        startButton = createMenuButton("Start");
        createPlayerButton = createMenuButton("Create player");
        loadPlayerButton = createMenuButton("Load player");
        backButton = ButtonUtils.createButton("Back", 20, Color.GRAY, 10, Color.DARKGRAY);

        vbox.setAlignment(Pos.CENTER);

        setLoadedPlayer("");
        loadedPlayerLabel.setFont(new Font(30));
        loadedPlayerLabel.setStyle("-fx-font-weight: bold");

        vbox.getChildren().add(startButton);
        vbox.getChildren().add(createPlayerButton);
        vbox.getChildren().add(loadPlayerButton);
        vbox.getChildren().add(backButton);

        root.setTop(loadedPlayerLabel);
        root.setCenter(vbox);
    }

    public static SingleplayerMenuGUI inst(){
        return instance;
    }

    public Scene getScene(){
        return scene;
    }

    public void setLoadedPlayer(String name){
        loadedPlayerLabel.setText("Loaded player: " + name);
    }

    public Button createMenuButton(String text){
        Button b = ButtonUtils.createButton(text, 30, Color.GRAY, 10, Color.DARKGRAY);
        return b;
    }

    public void setStartListener(EventHandler<ActionEvent> listener) {
        startButton.setOnAction(listener);
    }

    public void setCreatePlayerListener(EventHandler<ActionEvent> listener) {
        createPlayerButton.setOnAction(listener);
    }

    public void setLoadPlayerListener(EventHandler<ActionEvent> listener) {
        loadPlayerButton.setOnAction(listener);
    }

    public void setBackListener(EventHandler<ActionEvent> listener) {
        backButton.setOnAction(listener);
    }
}
