package com.group11.client.screens;

import com.group11.client.constants.CardColorConstants;
import com.group11.client.constants.SceneConstants;
import com.group11.client.gameObjects.Card;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen {
    private static StackPane root = null;   //the root
    private static GridPane board = null;   //game board on the root
    private static List<Card> cardList = null;  //cards on the board

    private static Circle player1;  //player1's pawn
    private static Circle player2;  //player2's pawn

    private static int diceValue;
    private static boolean rollingState = true;

    /**
     * This method creates the game board and
     * puts it on the scene
     *
     * @return Scene containing
     * cards for properties and other types.
     */
    public static Scene createScene() {
        board= new GridPane();
        board.setAlignment(Pos.CENTER);

        root = new StackPane();
        root.getChildren().add(board);

        player1 = new Circle(20, Color.BLACK);
        player2 = new Circle(20, Color.WHITE);

        Random rand = new Random();
        int randInt = rand.nextInt(9);
        int[] taxSpaceLocations = {1,3,5,7,8,9,11,13,15};
        int taxSpaceIndex = taxSpaceLocations[randInt];

        String[] colorList = {CardColorConstants.BLANCHEDALMOND, CardColorConstants.CORAL, CardColorConstants.GOLDENROD,
                              CardColorConstants.LIGHTGREEN, CardColorConstants.MEDIUMPURPLE, CardColorConstants.TEAL,
                              CardColorConstants.THISTLE, CardColorConstants.STEELBLUE};

        cardList = new ArrayList<>();
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
                card.getRect().setStyle(colorList[propertyIndex]);
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

        root.getChildren().add(player1);
        player1.setTranslateX(-300);
        player1.setTranslateY(260);

        root.getChildren().add(player2);
        player2.setTranslateX(-300);
        player2.setTranslateY(350);

        rollDice();

        return new Scene(root, SceneConstants.WINDOW_WIDTH, SceneConstants.WINDOW_HEIGHT, Color.GRAY);

    }

    private static void rollDice(){
        Button rollButton = new Button("Roll");
        rollButton.setPrefSize(100,50);
        VBox vbox = new VBox();
        ImageView imageView = new ImageView(new Image(GameScreen.class.getResource("/dice/1.jpg").toExternalForm(), 250, 250, true, true));
        vbox.getChildren().add(imageView);
        vbox.getChildren().add(rollButton);
        vbox.setAlignment(Pos.CENTER);

        rollButton.setOnAction(action -> {
            rollButton.setDisable(true);

            Random random = new Random();
            diceValue = random.nextInt(6)+1;

            for (int i = 0; i < 10; i++) {
                diceValue = random.nextInt(6) + 1;
                vbox.getChildren().set(0, new ImageView(new Image(
                        GameScreen.class.getResource("/dice/" + diceValue + ".jpg").toExternalForm(), 250, 250, true, true)));
            }
            rollButton.setDisable(false);
            rollButton.setText("Play");
            rollButton.setOnAction(a -> {
                root.getChildren().remove(vbox);
            });
        });

        root.getChildren().add(vbox);
    }

    /**
     * This method creates nonProperty cards
     * with their colors and other specs
     *
     * @param cardList list of cards on the board
     * @param taxSpaceIndex Tax Space Card's index generated randomly
     */
    private static void createNonProperties(List<Card> cardList, int taxSpaceIndex) {
        cardList.get(0).getText().setText("Start");
        cardList.get(0).setProperty(false);
        cardList.get(0).getRect().setStyle(CardColorConstants.PINK);

        cardList.get(2).getText().setText("Ferry 1");
        cardList.get(2).setProperty(true);
        cardList.get(2).setValue(250);
        cardList.get(2).getRect().setStyle(CardColorConstants.DEEPSKYBLUE);

        cardList.get(4).getText().setText("Jail");
        cardList.get(4).setProperty(false);
        cardList.get(4).getRect().setStyle(CardColorConstants.DARKSLATEBLUE);

        cardList.get(6).getText().setText("Ferry 2");
        cardList.get(6).setProperty(true);
        cardList.get(6).setValue(250);
        cardList.get(6).getRect().setStyle(CardColorConstants.DEEPSKYBLUE);

        cardList.get(10).getText().setText("Ferry 3");
        cardList.get(10).setProperty(true);
        cardList.get(10).setValue(250);
        cardList.get(10).getRect().setStyle(CardColorConstants.DEEPSKYBLUE);

        cardList.get(12).getText().setText("Go Jail");
        cardList.get(12).setProperty(false);
        cardList.get(12).getRect().setStyle(CardColorConstants.DARKSLATEBLUE);

        cardList.get(14).getText().setText("Ferry 4");
        cardList.get(14).setProperty(true);
        cardList.get(14).setValue(250);
        cardList.get(14).getRect().setStyle(CardColorConstants.DEEPSKYBLUE);

        cardList.get(taxSpaceIndex).getText().setText("Tax Space");
        cardList.get(taxSpaceIndex).setProperty(false);
        cardList.get(taxSpaceIndex).getRect().setStyle(CardColorConstants.RED);
        cardList.get(taxSpaceIndex).getText().setText(
                cardList.get(taxSpaceIndex).getText().getText() + "\n" + "-$50");
    }

    /**
     * This method gets the board and the card list and
     * places the cards on the grid board
     *
     * @param board the GridPane which is board
     * @param cardList list of cards that will be placed on the board
     */
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

    /**
     * This method puts the monopoly man image on middle
     * of the board that is empty.
     */
    private static void setMiddleImage() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(GameScreen.class.getResource("/backgroundPictures/monopoly_man.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(0.62, 0.62, true, true, false, false));
        board.setBackground(new Background(backgroundImage));
    }
}
