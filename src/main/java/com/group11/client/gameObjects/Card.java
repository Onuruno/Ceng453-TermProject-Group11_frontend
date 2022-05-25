package com.group11.client.gameObjects;

import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card extends Rectangle {
    private int value;
    private String name;
    private boolean isProperty;
    private boolean hasBought;

    public Card() {
        this.setWidth(150);
        this.setHeight(150);
        this.hasBought = false;
    }

    public Card(int value) {
        this.setWidth(150);
        this.setHeight(150);
        this.value = value;
        this.hasBought = false;
    }

    public Card(String name) {
        this.setWidth(150);
        this.setHeight(150);
        this.name = name;
        this.hasBought = false;
    }

    public Card(int value, String name) {
        this.setWidth(150);
        this.setHeight(150);
        this.value = value;
        this.name = name;
        this.hasBought = false;
    }
}
