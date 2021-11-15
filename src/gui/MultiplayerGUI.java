package gui;

import gui.utils.ButtonUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiplayerGUI {

    private static MultiplayerGUI instance = new MultiplayerGUI();

    private Scene scene;

    private BorderPane root;

    private VBox content;
    private HBox hbox; //Bad name

    private Button createPlayerButton;
    private Button loadPlayerButton;
    private Button startGameButton;
    private Button exitButton;

    private MenuBar menuBar;
    private Menu menu;
    private MenuItem saveGameMenuItem;
    private MenuItem saveAndExitMenuItem;

    private HBox center;
    private Map<String, PlayerBox> playerBoxes;
    private List<Image> hangmanStages;

    private Label wordLabel;
    private Label guessedLettersLabel;

    private TextField textField;
    private Button enterButton;

    private MultiplayerGUI(){
        hangmanStages = new ArrayList<>();
        playerBoxes = new HashMap<>();

        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));
        scene = new Scene(root, 1600,850);

        menuBar = new MenuBar();
        menu = new Menu("File");
        saveGameMenuItem = new MenuItem("Save Game");
        saveAndExitMenuItem = new MenuItem("Save And Exit Game");

        content = new VBox(20);
        hbox = new HBox(5);
        createPlayerButton = ButtonUtils.createButton("Create Player", 30, Color.GRAY, 10, Color.DARKGRAY);
        loadPlayerButton = ButtonUtils.createButton("Load Player", 30, Color.GRAY, 10, Color.DARKGRAY);
        center = new HBox();
        startGameButton = ButtonUtils.createButton("Start", 30, Color.GRAY, 10, Color.DARKGRAY);
        wordLabel = new Label("________");
        guessedLettersLabel = new Label("G F U T V");
        textField = new TextField();
        textField.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), null)));
        enterButton = ButtonUtils.createButton("Enter", 30, Color.GRAY, 10, Color.DARKGRAY);

        exitButton = ButtonUtils.createButton("Exit", 40, Color.GRAY, 10, Color.DARKGRAY);
        hbox.setAlignment(Pos.CENTER);


        menuBar.lookup(".menu-bar").setStyle("-fx-faint-focus-color: 0;");
        menuBar.prefWidthProperty().bind(root.widthProperty());

        menu.getItems().add(saveGameMenuItem);
        menu.getItems().add(saveAndExitMenuItem);

        content.setPadding(new Insets(40,0,40,0));
        content.setAlignment(Pos.TOP_CENTER);


        initHangmanStages();

        center.setAlignment(Pos.CENTER);

        wordLabel.setFont(new Font(30));
        guessedLettersLabel.setFont(new Font(30));

        startGameButton.setDisable(true);

        textField.setFont(new Font(20));
        textField.setMaxSize(60, 60);
        textField.setAlignment(Pos.CENTER);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                if(!newValue.matches("[a-zA-ZåäöÅÄÖ]+")){
                    textField.setText(oldValue);
                    return;
                }
                textField.setText(String.valueOf(newValue.charAt(newValue.length() - 1)).toUpperCase());
            }
        });

        enterButton.setDefaultButton(true);

        menuBar.getMenus().addAll(menu);
        hbox.getChildren().add(createPlayerButton);
        hbox.getChildren().add(loadPlayerButton);
        content.getChildren().add(hbox);
        content.getChildren().add(center);
        content.getChildren().add(startGameButton);

        root.setTop(menuBar);
        root.setCenter(content);
        root.setBottom(exitButton);
    }

    public static MultiplayerGUI inst(){
        return instance;
    }

    public Button getStartButton(){
        return startGameButton;
    }

    public Button getAddPlayerButton(){
        return loadPlayerButton;
    }

    public Button getCreatePlayerButton(){
        return createPlayerButton;
    }

    /**
     * Changes to the pre game layout
     */
    public void setupLobby(){
        content.getChildren().remove(wordLabel);
        content.getChildren().remove(guessedLettersLabel);
        content.getChildren().remove(textField);
        content.getChildren().remove(enterButton);
        if(!content.getChildren().contains(hbox)){
            content.getChildren().add(0, hbox);
            content.getChildren().add(startGameButton);
        }
    }

    /**
     * Changes to the game layout
     */
    public void setupGame(){
        for(PlayerBox playerBox : playerBoxes.values()){
            playerBox.setRemoveButtonVisible(false);
        }
        content.getChildren().remove(hbox);
        content.getChildren().remove(startGameButton);
        if(!content.getChildren().contains(textField)){
            content.getChildren().add(wordLabel);
            content.getChildren().add(guessedLettersLabel);
            content.getChildren().add(textField);
            content.getChildren().add(enterButton);
        }
        wordLabel.setTextFill(Color.BLACK);
    }

    public void restart(){
        for(PlayerBox playerBox : playerBoxes.values()){
            playerBox.setHangmanStage(0);
            playerBox.setScore(0);
        }
    }

    public void resetBoard(){
        center.getChildren().clear();
        playerBoxes.clear();
        resetTextField();
        System.out.println(textField.getText());
        createPlayerButton.setDisable(false);
        loadPlayerButton.setDisable(false);
        startGameButton.setDisable(true);
    }

    /**
     * Creates a player box and adds it to the gui.
     * */
    public void addPlayer(String name, EventHandler<ActionEvent> removeListener){
        PlayerBox playerBox = new PlayerBox(name);
        playerBox.setRemovePlayerListener(removeListener);
        center.getChildren().add(playerBox);
        playerBoxes.put(name, playerBox);
        setHangmanStage(name, 0);
    }

    public void remove(String name) {
        PlayerBox playerBox = playerBoxes.get(name);
        center.getChildren().remove(playerBox);
    }

    public Scene getScene(){
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

    public String getInput() {
        String input = textField.getText();
        return input;
    }

    public void resetTextField() {
        textField.clear();
    }

    public void setHangmanStage(String name, int stage) {
        PlayerBox playerBox = playerBoxes.get(name);
        playerBox.setHangmanStage(stage);
    }

    private void initHangmanStages() {
        for (int i = 0; i < 11; i++) {
            File file = new File("src/images/hangmanStages/hangman_" + i + ".png");
            Image image = new Image(file.toURI().toString());
            hangmanStages.add(image);
        }
    }

    public void highlightPlayer(String name, boolean value){
        PlayerBox playerBox = playerBoxes.get(name);
        playerBox.highlight(value);
    }

    public void setScore(String name, int score){
        PlayerBox playerBox = playerBoxes.get(name);
        playerBox.setScore(score);
    }

    public void setCreatePlayerListener(EventHandler<ActionEvent> listener){
        createPlayerButton.setOnAction(listener);
    }

    public void setLoadPlayerListener(EventHandler<ActionEvent> listener){
        loadPlayerButton.setOnAction(listener);
    }

    public void setStartGameListener(EventHandler<ActionEvent> listener){
        startGameButton.setOnAction(listener);
    }

    public void setEnterListener(EventHandler<ActionEvent> listener){
        enterButton.setOnAction(listener);
    }

    public void setExitButtonListener(EventHandler<ActionEvent> listener){
        exitButton.setOnAction(listener);
    }

    public void setSaveGameListener(EventHandler<ActionEvent> listener){
        saveGameMenuItem.setOnAction(listener);
    }

    public void setSaveAndExitListener(EventHandler<ActionEvent> listener){
        saveAndExitMenuItem.setOnAction(listener);
    }

    private class PlayerBox extends VBox {

        private Button removePlayerButton;
        private Label nameLabel;
        private ImageView imageView;
        private Label scoreLabel;

        public PlayerBox(String name){
            this.removePlayerButton = ButtonUtils.createButton("Remove Player", 20, Color.GRAY, 10, Color.DARKGRAY);
            this.nameLabel = new Label(name);
            this.imageView = new ImageView();
            this.scoreLabel = new Label();
            setScore(0);

            setAlignment(Pos.CENTER);
            maxHeightProperty().bind(heightProperty());

            nameLabel.setFont(new Font(25));
            nameLabel.setUnderline(true);
            nameLabel.setStyle("-fx-font-weight: bold");

            scoreLabel.setFont(new Font(20));

            getChildren().add(removePlayerButton);
            getChildren().add(nameLabel);
            getChildren().add(imageView);
            getChildren().add(scoreLabel);
        }

        public String getName(){
            return nameLabel.getText();
        }

        public void setHangmanStage(int stage){
            imageView.setImage(hangmanStages.get(stage));
        }

        public void setScore(int score){
            this.scoreLabel.setText("Score: " + score);
        }

        public void setRemoveButtonVisible(boolean value){
            if(value){
                if(!getChildren().contains(removePlayerButton)) {
                    getChildren().add(removePlayerButton);
                }
            }else{
                getChildren().remove(removePlayerButton);
            }
        }

        public void setRemovePlayerListener(EventHandler<ActionEvent> removeListener) {
            removePlayerButton.setOnAction(removeListener);
        }

        public void highlight(boolean value){
            setBackground(new Background(new BackgroundFill(value ? Color.LIGHTGRAY : null, value ? new CornerRadii(5) : null, null)));
        }

    }

}
