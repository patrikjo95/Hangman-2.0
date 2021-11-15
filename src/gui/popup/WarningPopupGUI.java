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
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WarningPopupGUI {

    private Stage stage;
    private Scene scene;
    private BorderPane root;

    private VBox vbox;
    private HBox hbox;
    private Label warningLabel;
    private Button confirmButton;
    private Button cancelButton;

    public WarningPopupGUI(String text){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, new CornerRadii(10), null)));
        root.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
        scene = new Scene(root, 300,150);
        scene.setFill(Color.TRANSPARENT);

        vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER);

        warningLabel = new Label(text);
        warningLabel.setTextAlignment(TextAlignment.CENTER);
        warningLabel.setFont(new Font(15));
        warningLabel.setWrapText(true);
        warningLabel.setTextFill(Color.INDIANRED);

        confirmButton = ButtonUtils.createButton("Confirm", 20, Color.GRAY, 10, Color.DARKGRAY);
        cancelButton = ButtonUtils.createButton("Cancel", 20, Color.GRAY, 10, Color.DARKGRAY);

        hbox.getChildren().add(confirmButton);
        hbox.getChildren().add(cancelButton);
        vbox.getChildren().add(warningLabel);
        vbox.getChildren().add(hbox);
        root.setCenter(vbox);
        stage.setScene(scene);
    }

    public void setConfirmListener(EventHandler<ActionEvent> listener){
        confirmButton.setOnAction(listener);
    }

    public void setCancelListener(EventHandler<ActionEvent> listener){
        cancelButton.setOnAction(listener);
    }

    public void show(){
        stage.show();
    }

    public void close(){
        stage.close();
    }

}
