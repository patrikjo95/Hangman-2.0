package gui;

import gui.utils.ButtonUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.File;

public class MainMenuGUI {

    private static MainMenuGUI instance = new MainMenuGUI();

    private Scene scene;

    private BorderPane root;

    private VBox menuOrderVBox;
    private Button singlePlayerButton;
    private Button multiplayerButton;
    private Button highScoreButton;
    private Label label;
    private Image titleImage;
    private ImageView imageView;

    private MainMenuGUI() {
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));
        scene = new Scene(root, 600, 400);

        label = new Label("Defenders Hangman Royale");
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font(40));

        titleImage = new Image(new File("src/images/Hangman_Title.png").toURI().toString());
        imageView = new ImageView(titleImage);

        menuOrderVBox = new VBox(5);
        menuOrderVBox.setAlignment(Pos.CENTER);
        singlePlayerButton = ButtonUtils.createButton("Singleplayer", 40, Color.GRAY, 10, Color.DARKGRAY);
        multiplayerButton = ButtonUtils.createButton("Multiplayer", 40, Color.GRAY, 10, Color.DARKGRAY);
        highScoreButton = ButtonUtils.createButton("HighScore", 30, Color.GRAY, 10, Color.DARKGRAY);

        menuOrderVBox.getChildren().add(imageView);
        menuOrderVBox.getChildren().add(singlePlayerButton);
        menuOrderVBox.getChildren().add(multiplayerButton);
        menuOrderVBox.getChildren().add(highScoreButton);

        root.setCenter(menuOrderVBox);
    }

    public static MainMenuGUI inst() {
        return instance;
    }

    public Scene getScene() {
        return scene;
    }

    public void setSingleplayerListener(EventHandler<ActionEvent> listener) {
        singlePlayerButton.setOnAction(listener);
    }

    public void setMultiplayerListener(EventHandler<ActionEvent> listener) {
        multiplayerButton.setOnAction(listener);
    }

    public void setHighScoreListener(EventHandler<ActionEvent> listener){
        highScoreButton.setOnAction(listener);
    }

}
