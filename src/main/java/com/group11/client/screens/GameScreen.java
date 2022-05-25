package com.group11.client.screens;

import com.group11.client.constants.SceneConstants;
import com.group11.client.gameObjects.Card;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen {
    private static StackPane root = null;
    private static GridPane board = null;
    private static boolean b;

    public static Scene createScene() {
        board= new GridPane();
        board.setAlignment(Pos.CENTER);

        root = new StackPane();
        root.getChildren().add(board);

        Circle player1 = new Circle(20, Color.BLACK);
        Circle player2 = new Circle(20, Color.WHITE);

        Random rand = new Random();
        int randInt = rand.nextInt(9);
        int[] taxSpaceLocations = {1,3,5,7,8,9,11,13,15};
        int taxSpaceIndex = taxSpaceLocations[randInt];

        String[] colorList = {"blanchedalmond", "coral", "goldenrod", "lightgreen", "mediumpurple", "teal", "thistle", "steelblue"};

        List<Card> cardList = new ArrayList<>();
        for (int i=0; i<16; i++) {
            cardList.add(new Card(""));
        }

        createNonProperties(cardList, taxSpaceIndex);

        int propertyIndex = 0;
        int[] propertyValues = {100, 100, 200, 200, 300, 300, 400, 500};
        for (Card card:cardList) {
            if (card.getText().getText().isEmpty()) {
                card.setProperty(true);
                card.setValue(propertyValues[propertyIndex]);
                card.getText().setText("Property " + String.valueOf(propertyIndex+1));
                card.getRect().setStyle("-fx-fill: " + colorList[propertyIndex] + " ; -fx-stroke: black; -fx-stroke-width: 3;");
                propertyIndex++;
            }
        }

        for (Card card:cardList) {
            if (card.isProperty()) {
                String text = card.getText().getText();
                card.getText().setText(text + "\n" + "$" + String.valueOf(card.getValue()));
            }
        }

        addCardsToBoard(board, cardList);

        setMiddleImage();

        /*cardList.get(0).getChildren().add(player1);
        cardList.get(0).setAlignment(player1, Pos.TOP_CENTER);

        cardList.get(0).getChildren().add(player2);
        cardList.get(0).setAlignment(player2, Pos.BOTTOM_CENTER);*/

        root.getChildren().add(player1);
        player1.setTranslateX(-300);
        player1.setTranslateY(260);

        return new Scene(root, SceneConstants.WINDOW_WIDTH, SceneConstants.WINDOW_HEIGHT, Color.GRAY);
    }


    private static void createNonProperties(List<Card> cardList, int taxSpaceIndex) {
        cardList.get(0).getText().setText("Start");
        cardList.get(0).setProperty(false);
        cardList.get(0).getRect().setStyle("-fx-fill: pink; -fx-stroke: black; -fx-stroke-width: 3;");

        cardList.get(2).getText().setText("Ferry 1");
        cardList.get(2).setProperty(true);
        cardList.get(2).setValue(250);
        cardList.get(2).getRect().setStyle("-fx-fill: deepskyblue; -fx-stroke: black; -fx-stroke-width: 3;");

        cardList.get(4).getText().setText("Jail");
        cardList.get(4).setProperty(false);
        cardList.get(4).getRect().setStyle("-fx-fill: darkslateblue ; -fx-stroke: black; -fx-stroke-width: 3;");

        cardList.get(6).getText().setText("Ferry 2");
        cardList.get(6).setProperty(true);
        cardList.get(6).setValue(250);
        cardList.get(6).getRect().setStyle("-fx-fill: deepskyblue; -fx-stroke: black; -fx-stroke-width: 3;");

        cardList.get(10).getText().setText("Ferry 3");
        cardList.get(10).setProperty(true);
        cardList.get(10).setValue(250);
        cardList.get(10).getRect().setStyle("-fx-fill: deepskyblue; -fx-stroke: black; -fx-stroke-width: 3;");

        cardList.get(12).getText().setText("Go Jail");
        cardList.get(12).setProperty(false);
        cardList.get(12).getRect().setStyle("-fx-fill: darkslateblue ; -fx-stroke: black; -fx-stroke-width: 3;");

        cardList.get(14).getText().setText("Ferry 4");
        cardList.get(14).setProperty(true);
        cardList.get(14).setValue(250);
        cardList.get(14).getRect().setStyle("-fx-fill: deepskyblue; -fx-stroke: black; -fx-stroke-width: 3;");

        cardList.get(taxSpaceIndex).getText().setText("Tax Space");
        cardList.get(taxSpaceIndex).setProperty(false);
        cardList.get(taxSpaceIndex).getRect().setStyle("-fx-fill: red ; -fx-stroke: black; -fx-stroke-width: 3;");
        cardList.get(taxSpaceIndex).getText().setText(
                cardList.get(taxSpaceIndex).getText().getText() + "\n" + "-$50");
    }

    private static void addCardsToBoard(GridPane board, List<Card> cardList) {
        board.add(cardList.get(0), 0,4);
        board.add(cardList.get(1), 0,3);
        board.add(cardList.get(2), 0,2);
        board.add(cardList.get(3), 0,1);
        board.add(cardList.get(4), 0,0);
        board.add(cardList.get(5), 1,0);
        board.add(cardList.get(6), 2,0);
        board.add(cardList.get(7), 3,0);
        board.add(cardList.get(8), 4,0);
        board.add(cardList.get(9), 4,1);
        board.add(cardList.get(10), 4,2);
        board.add(cardList.get(11), 4,3);
        board.add(cardList.get(12), 4,4);
        board.add(cardList.get(13), 3,4);
        board.add(cardList.get(14), 2,4);
        board.add(cardList.get(15), 1,4);
    }

    private static void setMiddleImage() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(GameScreen.class.getResource("/backgroundPictures/monopoly_man.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(0.62, 0.62, true, true, false, false));
        board.setBackground(new Background(backgroundImage));
    }
}
