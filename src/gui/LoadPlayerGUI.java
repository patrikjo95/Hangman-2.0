package gui;

import gui.utils.ButtonUtils;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class LoadPlayerGUI {

    private Scene scene;
    private BorderPane root;

    private ScrollPane scrollPane;
    private VBox vbox;

    private HBox header;
    private StackPane nameLabel;
    private StackPane gamesPlayedLabel;
    private StackPane gamesWonLabel;
    private StackPane multiPlayerGamesPlayedLabel;
    private StackPane multiPlayerGamesWonLabel;
    private Map<String, PlayerBox> playerBoxes;

    private HBox footer;
    private Button loadButton;
    private Button removeButton;
    private Button backButton;

    public LoadPlayerGUI(){
        playerBoxes = new HashMap<>();
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));
        scene = new Scene(root, 600,500);
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null ,null)));
        scrollPane.getStylesheets().add(getClass().getResource("css/ScrollBar.css").toExternalForm());

        vbox = new VBox(1);
        vbox.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null ,null)));

        initHeader();

        footer = new HBox(5);
        footer.setPadding(new Insets(5));

        loadButton = addFooterButton("Load");
        removeButton = addFooterButton("Remove");
        backButton = addFooterButton("Back");

        scrollPane.setContent(vbox);
        footer.getChildren().add(loadButton);
        footer.getChildren().add(removeButton);
        footer.getChildren().add(backButton);

        root.setTop(header);
        root.setCenter(scrollPane);
        root.setBottom(footer);
    }

    public Scene getScene(){
        return scene;
    }

    private Button addFooterButton(String text){
        Button b = ButtonUtils.createButton(text, 25, Color.GRAY, 10, Color.DARKGRAY);
        return b;
    }

    /**
     * Initializes header with respective labels to the info wanted to be shown for each player.
     */
    private void initHeader(){
        header = new HBox();
        header.setAlignment(Pos.CENTER);
        nameLabel = createListHeader("Player Name");
        gamesPlayedLabel = createListHeader("Games Played");
        gamesWonLabel = createListHeader("Games Won");
        multiPlayerGamesPlayedLabel = createListHeader("MultiPlayer Games Played");
        multiPlayerGamesWonLabel = createListHeader("MultiPlayer Games Won");

        header.getChildren().addAll(nameLabel,
                VerticalSeparator(), gamesPlayedLabel,
                VerticalSeparator(), gamesWonLabel,
                VerticalSeparator(), multiPlayerGamesPlayedLabel,
                VerticalSeparator(), multiPlayerGamesWonLabel);
    }

    private Line VerticalSeparator(){
        Line line = new Line();
        line.endYProperty().bind(multiPlayerGamesPlayedLabel.heightProperty().subtract(1)); //for some reason we need to subtract the heightProperty by 1 otherwise the line will grow infinitely
        return line;
    }

    private StackPane createListHeader(String text){
        StackPane pane = new StackPane();
        pane.backgroundProperty().bind(Bindings.when(pane.hoverProperty())
                .then(new Background(new BackgroundFill(Color.DARKGRAY, null, null)))
                .otherwise(new Background(new BackgroundFill(Color.DIMGRAY, null, null))));
        Label label = createListLabel(text, 15);
        pane.getChildren().add(label);
        return pane;
    }

    /**
     * Creates styled list-labels
     * @param text
     * @param fontSize
     * @return the label
     */
    private Label createListLabel(String text, int fontSize){
        Label label = new Label(text);
        label.setPrefWidth(10000);
        label.setWrapText(true);
        label.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, fontSize));
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        HBox.setHgrow(label, Priority.ALWAYS);
        return label;
    }

    public void resetGrid(){
        vbox.getChildren().clear();
        playerBoxes.clear();
    }

    /**
     * adds nex Hbox with all player-related info when new a player is added.
     * @param name
     * @param gamesPlayed
     * @param wins
     * @param multiPlayerGamesPlayed
     * @param multiPlayerWins
     * @param clickListener
     */
    public void addGridItem(String name, int gamesPlayed, int wins, int multiPlayerGamesPlayed, int multiPlayerWins, EventHandler<MouseEvent> clickListener) {
        PlayerBox playerBox = new PlayerBox();
        playerBox.setName(name);
        playerBox.setGamesPlayed(gamesPlayed);
        playerBox.setWins(wins);
        playerBox.setClickListener(clickListener);
        playerBox.setMultiPlayerGamesPlayed(multiPlayerGamesPlayed);
        playerBox.setMultiPlayerWins(multiPlayerWins);
        vbox.getChildren().add(playerBox);
        playerBoxes.put(name, playerBox);
    }

    /**
     * Hightligts the selected player map to enable loading of that player
     * @param name
     * @param value
     */
    public void highlight(String name, boolean value){
        PlayerBox playerBox = playerBoxes.get(name);
        playerBox.highlight(value);
    }

    public void setLoadListener(EventHandler<ActionEvent> listener){
        loadButton.setOnAction(listener);
    }

    public void setRemoveListener(EventHandler<ActionEvent> listener){
        removeButton.setOnAction(listener);
    }

    public void setBackListener(EventHandler<ActionEvent> listener){
        backButton.setOnAction(listener);
    }

    public void setHeaderNameListener(EventHandler<MouseEvent> listener) {
        nameLabel.setOnMouseClicked(listener);
    }

    public void setHeaderGamesPlayedListener(EventHandler<MouseEvent> listener) {
        gamesPlayedLabel.setOnMouseClicked(listener);
    }

    public void setHeaderGamesWinListener(EventHandler<MouseEvent> listener) {
        gamesWonLabel.setOnMouseClicked(listener);
    }

    public void setHeaderMultiplayerGamesPlayedListener(EventHandler<MouseEvent> listener) {
        multiPlayerGamesPlayedLabel.setOnMouseClicked(listener);
    }
    public void setHeaderMultiplayerGamesWinListener(EventHandler<MouseEvent> listener) {
        multiPlayerGamesWonLabel.setOnMouseClicked(listener);
    }

    private class PlayerBox extends HBox {

        private Label nameLabel;
        private Label gamesPlayedLabel;
        private Label winsLabel;
        private Label multiPlayerGamesPlayedLabel;
        private Label multiPlayerWinsLabel;

        PlayerBox(){
            setPrefHeight(100);
            setMinHeight(70);
            setPadding(new Insets(5));
            highlight(false);

            nameLabel = createListLabel("pla", 20);
            gamesPlayedLabel = createListLabel("ce", 20);
            winsLabel = createListLabel("ho", 20);
            multiPlayerGamesPlayedLabel = createListLabel("ld", 20);
            multiPlayerWinsLabel = createListLabel("er", 20);

            setAlignment(Pos.CENTER);

            getChildren().add(nameLabel);
            getChildren().add(gamesPlayedLabel);
            getChildren().add(winsLabel);
            getChildren().add(multiPlayerGamesPlayedLabel);
            getChildren().add(multiPlayerWinsLabel);
        }

        public void setName(String name) {
            nameLabel.setText(name);
        }

        public void setGamesPlayed(int gamesPlayed) {
            gamesPlayedLabel.setText(String.valueOf(gamesPlayed));
        }

        public void setWins(int wins) {
            winsLabel.setText(String.valueOf(wins));
        }

        public void setMultiPlayerGamesPlayed(int multiPlayerGamesPlayed){
            multiPlayerGamesPlayedLabel.setText(String.valueOf(multiPlayerGamesPlayed));
        }

        public void setMultiPlayerWins(int multiPlayerWins){
            multiPlayerWinsLabel.setText(String.valueOf(multiPlayerWins));
        }

        public void setClickListener(EventHandler<MouseEvent> listener) {
            setOnMouseClicked(listener);
        }

        /**
         * Highligts the selected player-box to show the user which player that is selected.
         * @param value
         */
        public void highlight(boolean value){
            backgroundProperty().bind(Bindings.when(this.hoverProperty())
                    .then(new Background(new BackgroundFill(value ? Color.LIGHTGRAY : Color.DARKGRAY, new CornerRadii(15), null)))
                    .otherwise(new Background(new BackgroundFill(value ? Color.LIGHTGRAY : Color.GRAY, new CornerRadii(15), null))));
        }
    }
}
