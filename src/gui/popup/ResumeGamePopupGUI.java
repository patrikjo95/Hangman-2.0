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
import javafx.stage.StageStyle;

public class ResumeGamePopupGUI {

    private Stage stage;
    private Scene scene;
    private BorderPane root;

    private VBox vbox;
    private Label label;
    private Button yesButton;
    private Button noButton;

    public ResumeGamePopupGUI(){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        root = new BorderPane();
        scene = new Scene(root, 300,150);
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));
        root.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
        stage.setTitle("Resume Game");

        vbox = new VBox(5);
        label = new Label("Resume Previous Game?");
        label.setFont(new Font(20));

        yesButton = ButtonUtils.createButton("Yes", 20, Color.GRAY, 10, Color.DARKGRAY);
        noButton = ButtonUtils.createButton("No", 20, Color.GRAY, 10, Color.DARKGRAY);

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(label);
        vbox.getChildren().add(yesButton);
        vbox.getChildren().add(noButton);
        root.setCenter(vbox);
        stage.setScene(scene);
    }

    public void setYesListener(EventHandler<ActionEvent> listener){
        yesButton.setOnAction(listener);
    }

    public void setNoListener(EventHandler<ActionEvent> listener) {
        noButton.setOnAction(listener);
    }
    public void show(){
        stage.show();
    }

    public void close(){
        stage.close();
    }

}
