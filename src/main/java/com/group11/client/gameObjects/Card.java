package com.group11.client.gameObjects;

import com.group11.client.enums.PropertyType;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card extends StackPane {

    private Rectangle rect = new Rectangle(150,150);
    private Text text = new Text("");
    private int value;
    private boolean hasBought;
    private PropertyType propertyType;

    public Card() {
        fixed();
    }

    public Card(int value) {
        fixed();
        this.value = value;
    }

    public Card(String name) {
        fixed();
        this.text.setText(name);
    }

    public Card(int value, String name) {
        fixed();
        this.value = value;
        this.text.setText(name);
    }

    private void fixed() {
        this.setWidth(150);
        this.setHeight(150);
        this.hasBought = false;
        this.getText().setStyle("-fx-font: 24 arial;");
        this.getText().setTextAlignment(TextAlignment.CENTER);
        this.getChildren().addAll(rect, text);
    }
}
