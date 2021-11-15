package gui.utils;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ButtonUtils {

    /**
     * Creates a button which follows the styling of the GUI, WITHOUT hover option
     */
    public static Button createButton(String text, int font, Color color, int cornerRadii){
        Button b = new Button(text);
        b.setFont(new Font(font));
        b.setBackground(new Background(new BackgroundFill(color == null ? null : color, new CornerRadii(cornerRadii), null)));
        return b;
    }
    /**
     * Creates a button which follows the styling of the GUI, WITH hover option
     */
    public static Button createButton(String text, int font, Color color, int cornerRadii, Color hoverColor){
        Button b = createButton(text, font, color, cornerRadii);
        b.backgroundProperty().bind(Bindings.when(b.hoverProperty())
                .then(new Background(new BackgroundFill(hoverColor, new CornerRadii(cornerRadii), null)))
                .otherwise(new Background(new BackgroundFill(color, new CornerRadii(cornerRadii), null))));
        return b;
    }

}
