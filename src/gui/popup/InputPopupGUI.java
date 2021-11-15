package gui.popup;

import gui.GUI;
import gui.utils.ButtonUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InputPopupGUI {

    private Stage stage;
    private Scene scene;
    private BorderPane root;

    private VBox vbox;

    private Label errorLabel;
    private TextField input;
    private Button enterButton;

    public InputPopupGUI(){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        root = new BorderPane();
        scene = new Scene(root, 260,100);
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));
        stage.setTitle("Player Creator");

        vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        errorLabel = new Label();

        input = new TextField();
        enterButton = ButtonUtils.createButton("Enter", 15, Color.GRAY, 10, Color.DARKGRAY);
        enterButton.setDefaultButton(true);

        errorLabel.setFont(new Font(13));
        errorLabel.setWrapText(true);
        errorLabel.setTextAlignment(TextAlignment.CENTER);
        errorLabel.setTextFill(Color.INDIANRED);

        input.setMaxWidth(120);
        input.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), null)));
        input.setPromptText("Player name");

        vbox.getChildren().add(errorLabel);
        vbox.getChildren().add(input);
        vbox.getChildren().add(enterButton);

        root.setCenter(vbox);
        stage.setScene(scene);
    }

    public String getInput(){
        return input.getText();
    }

    public void setError(String text){
        errorLabel.setText(text);
    }

    public void setListener(EventHandler<ActionEvent> listener){
        enterButton.setOnAction(listener);
    }

    public void show(){
        stage.show();
    }

    public void close(){
        stage.close();
    }

}
