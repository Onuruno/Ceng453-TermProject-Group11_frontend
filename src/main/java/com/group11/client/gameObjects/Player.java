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
    private int jailValue;
    private int sixDice;

    public Player() {
        this.setMoney(1500);
        this.setJailValue(0);
        this.setSixDice(3);
    }

    public void sendJail() {
        this.jailValue = 2;
    }

    public void decreaseJail() {
        this.jailValue--;
    }

    public void decreaseSixDice() {
        this.sixDice--;
    }

    public void resetSixDice() {
        this.setSixDice(3);
    }
}
