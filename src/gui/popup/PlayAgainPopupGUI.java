package gui.popup;


import gui.utils.ButtonUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayAgainPopupGUI {

    private Stage stage;
    private Scene scene;
    private BorderPane root;

    private Label winnerLabel;

    private VBox vbox;
    private HBox hbox;
    private HBox footer;
    private Button playAgainButton;
    private Button mainMenuButton;

    public PlayAgainPopupGUI(){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        root = new BorderPane();
        scene = new Scene(root, 400,160);
        stage.setTitle("Play again?");
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));

        vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);

        footer = new HBox(5);
        footer.setAlignment(Pos.CENTER);

        winnerLabel = new Label(" Is The Winner!!");
        winnerLabel.setFont(new Font(30));
        winnerLabel.setStyle("-fx-font-weight: bold");

        hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER);

        playAgainButton = ButtonUtils.createButton("Play Again", 30, Color.GRAY, 10, Color.DARKGRAY);
        mainMenuButton = ButtonUtils.createButton("Main Menu", 30, Color.GRAY, 10, Color.DARKGRAY);

        hbox.getChildren().add(playAgainButton);
        hbox.getChildren().add(mainMenuButton);

        vbox.getChildren().add(winnerLabel);
        vbox.getChildren().add(hbox);
        root.setCenter(vbox);
        root.setBottom(footer);
        stage.setScene(scene);
    }

    public void setWinnerLabel(String name){
        winnerLabel.setText(name + " Is The Winner!!");
    }

    public void setPlayAgainListener(EventHandler<ActionEvent> listener){
        playAgainButton.setOnAction(listener);
    }

    public void setMainMenuListener(EventHandler<ActionEvent> listener){
        mainMenuButton.setOnAction(listener);
    }

    public void show(){
        stage.show();
    }

    public void close(){
        stage.close();
    }

}
