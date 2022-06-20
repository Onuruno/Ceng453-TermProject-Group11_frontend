package com.group11.client.gameObjects;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player {
    private Integer money;
    private List<Card> propertyList = new ArrayList<>();
    private boolean isInJail;

    public Player() {
        this.setMoney(1500);
        this.setInJail(false);
    }
}
