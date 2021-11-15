package gui;

import gui.utils.ButtonUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SingleplayerGUI {

    private static final SingleplayerGUI instance = new SingleplayerGUI();

    private Scene scene;

    private BorderPane root;
    private VBox vbox;
    private HBox header;
    private HBox lowerHBox; //Bad name.

    //Header
    private Label playerNameLabel;
    private Label lifeCountLabel;
    //
    private ImageView imageView;
    private Label wordLabel;
    private Label guessedLettersLabel;
    private TextField textField;
    private Button enterButton;
    private Label errorLabel;

    //after game
    private HBox postGameHBox; //Bad name.
    private Button playAgainButton;
    private Button mainMenuButton;
    //
    private List<Image> hangmanStages;

    private SingleplayerGUI() {
        hangmanStages = new ArrayList<>();

        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));
        scene = new Scene(root, 600, 550);

        header = new HBox(15);
        header.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(0), null)));

        playerNameLabel = new Label("playerName");
        playerNameLabel.setFont(new Font(20));

        lifeCountLabel = new Label("Lives left: 0");
        lifeCountLabel.setFont(new Font(20));

        vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        imageView = new ImageView();
        initHangmanStages();
        setHangmanStage(0);

        textField = new TextField();
        textField.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), null)));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                if(!newValue.matches("[a-zA-ZåäöÅÄÖ]+")){
                    textField.setText(oldValue);
                    return;
                }
                textField.setText(String.valueOf(newValue.charAt(newValue.length() - 1)).toUpperCase());
            }
        });
        textField.setFont(new Font(20));
        textField.setMaxSize(60, 60);
        textField.setAlignment(Pos.CENTER);

        //Post game buttons
        postGameHBox = new HBox(15);
        playAgainButton = ButtonUtils.createButton("Play again", 20, Color.GRAY, 10, Color.DARKGRAY);
        mainMenuButton = ButtonUtils.createButton("Main menu", 20, Color.GRAY, 10, Color.DARKGRAY);
        postGameHBox.setAlignment(Pos.CENTER);
        postGameHBox.getChildren().add(playAgainButton);
        postGameHBox.getChildren().add(mainMenuButton);
        //

        lowerHBox = new HBox();
        lowerHBox.setAlignment(Pos.CENTER);

        wordLabel = new Label("________");
        wordLabel.setFont(new Font(30));
        //wordLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, new CornerRadii(0), null)));
        guessedLettersLabel = new Label("G F U T V");
        guessedLettersLabel.setFont(new Font(30));
        enterButton = ButtonUtils.createButton("Enter", 20, Color.GRAY, 10, Color.DARKGRAY);
        errorLabel = new Label("");

        enterButton.setDefaultButton(true);

        header.getChildren().add(playerNameLabel);
        header.getChildren().add(lifeCountLabel);

        vbox.getChildren().add(imageView);
        vbox.getChildren().add(wordLabel);
        vbox.getChildren().add(guessedLettersLabel);
        lowerHBox.getChildren().add(textField);
        lowerHBox.getChildren().add(errorLabel);
        vbox.getChildren().add(lowerHBox);
        vbox.getChildren().add(enterButton);

        root.setTop(header);
        root.setCenter(vbox);
    }

    public static SingleplayerGUI inst() {
        return instance;
    }

    public Scene getScene() {
        return scene;
    }

    public TextField getTextField() {
        return textField;
    }

    public Label getWordLabel(){
        return wordLabel;
    }

    public void setWordRevelation(String wordRevelation) {
        wordLabel.setText(wordRevelation);
    }

    public void setGuessedLettersLabel(String guessedLetters) {
        guessedLettersLabel.setText(guessedLetters);
    }

    public void setSubmitListener(EventHandler<ActionEvent> listener) {
        enterButton.setOnAction(listener);
    }

    public void setPlayAgainListener(EventHandler<ActionEvent> listener) {
        playAgainButton.setOnAction(listener);

    }

    public void setMainMenuListener(EventHandler<ActionEvent> listener) {
        mainMenuButton.setOnAction(listener);
    }

    public String getInput() {
        String input = textField.getText();
        return input;
    }

    public void resetTextField() {
        textField.setText("");
    }

    public void setPlayerName(String playerName) {
        this.playerNameLabel.setText(playerName);

    }

    public void setLivesLeft(int livesLeft) {
        lifeCountLabel.setText("Lives left: " + livesLeft);
    }

    /**
     * Changes to the post game layout
     */
    public void setupPostGame() {
        vbox.getChildren().add(vbox.getChildren().indexOf(lowerHBox), postGameHBox);
        vbox.getChildren().remove(lowerHBox);
        enterButton.setVisible(false);
    }

    /**
     * Changes to the pre game layout
     */
    public void setupPreGame() {
        if (vbox.getChildren().contains(postGameHBox)) {
            vbox.getChildren().add(vbox.getChildren().indexOf(postGameHBox), lowerHBox);
            vbox.getChildren().remove(postGameHBox);
        }
        getWordLabel().setTextFill(Color.BLACK);
        enterButton.setVisible(true);
    }

    /**
     * Sets the image to the corresponding int
     */
    public void setHangmanStage(int stage) {
        imageView.setImage(hangmanStages.get(stage));
    }

    private void initHangmanStages() {
        for (int i = 0; i < 11; i++) {
            File file = new File("src/images/hangmanStages/hangman_" + i + ".png");
            Image image = new Image(file.toURI().toString());
            hangmanStages.add(image);
        }
    }

}
