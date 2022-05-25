package com.group11.client.screens;

import com.group11.client.constants.SceneConstants;
import com.group11.client.gameObjects.Card;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen {
    private static GridPane board = null;
    private static boolean b;

    public static Scene createScene() {
        board= new GridPane();
        board.setAlignment(Pos.CENTER);

        Random rand = new Random();
        int taxSpaceIndex = rand.nextInt(13);
        if (taxSpaceIndex<3) {
            taxSpaceIndex+=1;
        } else if (taxSpaceIndex<10) {
            taxSpaceIndex+=2;
        } else {
            taxSpaceIndex+=3;
        }

        List<Card> cardList = new ArrayList<>();
        for (int i=0; i<16; i++) {
            cardList.add(new Card());
        }

        cardList.get(0).setName("Start");
        cardList.get(0).setProperty(false);

        cardList.get(4).setName("Jail");
        cardList.get(4).setProperty(false);

        cardList.get(12).setName("Go Jail");
        cardList.get(12).setProperty(false);

        cardList.get(taxSpaceIndex).setName("Tax Space");
        cardList.get(taxSpaceIndex).setProperty(false);

        int propertyIndex = 0;
        int[] propertyValues = {100, 100, 200, 200, 300, 300, 400, 500};
        for (Card card:cardList) {
            if (card.getName() == null) {
                card.setValue(propertyValues[propertyIndex++]);
                card.setName("Property " + String.valueOf(propertyIndex));
            }
        }




        getNodeFromBoard(0,4).setStyle("-fx-fill: pink; -fx-stroke: black; -fx-stroke-width: 3;");

        getNodeFromBoard(0,0).setStyle("-fx-fill: darkslateblue ; -fx-stroke: black; -fx-stroke-width: 3;");

        getNodeFromBoard(4,4).setStyle("-fx-fill: darkblue ; -fx-stroke: black; -fx-stroke-width: 3;");



        setMiddleImage();

        return new Scene(board, SceneConstants.WINDOW_WIDTH, SceneConstants.WINDOW_HEIGHT, Color.GRAY);
    }

    private static Node getNodeFromBoard(int col, int row) {
        for (Node node : board.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private static void setMiddleImage() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(GameScreen.class.getResource("/backgroundPictures/monopoly_man.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(0.6, 0.6, true, true, false, false));
        board.setBackground(new Background(backgroundImage));
    }
}
