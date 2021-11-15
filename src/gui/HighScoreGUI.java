package gui;

import gui.utils.ButtonUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class HighScoreGUI {

    private static HighScoreGUI instance = new HighScoreGUI();

    private Scene scene;
    private BorderPane root;

    private VBox vbox;
    private HighScoreLabel firstPlace;
    private HighScoreLabel secondPlace;
    private HighScoreLabel thirdPlace;
    private HighScoreLabel fourthPlace;
    private HighScoreLabel fifthPlace;

    private Button backButton;

    private HighScoreGUI(){
        root = new BorderPane();
        scene = new Scene(root, 550, 400);
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));

        vbox = new VBox(5);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.CENTER);
        vbox.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));

        firstPlace = new HighScoreLabel(Color.DARKGOLDENROD);
        secondPlace = new HighScoreLabel(Color.SILVER);
        thirdPlace = new HighScoreLabel(Color.rgb(122, 77, 30));
        fourthPlace = new HighScoreLabel(Color.GRAY);
        fifthPlace = new HighScoreLabel(Color.GRAY);

        backButton = ButtonUtils.createButton("Back", 17, Color.GRAY, 10, Color.DARKGRAY);

        vbox.getChildren().add(firstPlace);
        vbox.getChildren().add(secondPlace);
        vbox.getChildren().add(thirdPlace);
        vbox.getChildren().add(fourthPlace);
        vbox.getChildren().add(fifthPlace);
        vbox.getChildren().add(backButton);
        root.setCenter(vbox);
    }

    public static HighScoreGUI inst(){
        return instance;
    }

    public Scene getScene(){
        return scene;
    }

    public void resetList(){
        firstPlace.setName("Name");
        firstPlace.scoreLabel.setText("Score");
        secondPlace.setName("Name");
        secondPlace.scoreLabel.setText("Score");
        thirdPlace.setName("Name");
        thirdPlace.scoreLabel.setText("Score");
        fourthPlace.setName("Name");
        fourthPlace.scoreLabel.setText("Score");
        fifthPlace.setName("Name");
        fifthPlace.scoreLabel.setText("Score");
    }

    public void setFirstPlace(String name, int score){
        firstPlace.setName(name);
        firstPlace.setScore(score);
    }

    public void setSecondPlace(String name, int score){
        secondPlace.setName(name);
        secondPlace.setScore(score);
    }

    public void setThirdPlace(String name, int score){
        thirdPlace.setName(name);
        thirdPlace.setScore(score);
    }

    public void setFourthPlace(String name, int score){
        fourthPlace.setName(name);
        fourthPlace.setScore(score);
    }

    public void setFifthPlace(String name, int score){
        fifthPlace.setName(name);
        fifthPlace.setScore(score);
    }

    public void setBackListener(EventHandler<ActionEvent> listener){
        backButton.setOnAction(listener);
    }

    private class HighScoreLabel extends HBox {

        private Label nameLabel;
        private Label scoreLabel;

        HighScoreLabel(Paint color){
            setAlignment(Pos.CENTER);
            setPadding(new Insets(4,20,4,20));
            setBackground(new Background(new BackgroundFill(color, new CornerRadii(15), null)));
            setMaxWidth(400);


            nameLabel = new Label("Name");
            scoreLabel = new Label("Score");

            nameLabel.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 20));
            scoreLabel.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 20));
            nameLabel.setTextFill(Color.WHITESMOKE);
            scoreLabel.setTextFill(Color.WHITESMOKE);
            nameLabel.setPrefWidth(1000);
            scoreLabel.setPrefWidth(1000);
            nameLabel.setAlignment(Pos.CENTER);
            scoreLabel.setAlignment(Pos.CENTER);

            getChildren().add(nameLabel);
            getChildren().add(scoreLabel);
        }

        public void setName(String name){
            nameLabel.setText(name);
        }

        public void setScore(int score){
            scoreLabel.setText(String.valueOf(score));
        }

    }

}
