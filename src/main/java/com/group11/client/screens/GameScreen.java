package com.group11.client.screens;

import com.group11.client.constants.CardColorConstants;
import com.group11.client.constants.SceneConstants;
import com.group11.client.gameObjects.Card;

import com.group11.client.gameObjects.Player;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen {
    private static StackPane root = null;   //the root
    private static GridPane board = null;   //game board on the root
    private static List<Card> cardList = null;  //cards on the board

    private static Circle pawn1;  //pawn1's pawn
    private static Circle pawn2;  //pawn2's pawn

    private static final Player player1 = new Player();
    private static final Player player2 = new Player();

    private static final Label player1Money = new Label();
    private static final Label player2Money = new Label();

    private static final List<Pair<Double, Double>> positions1 = new ArrayList<>();
    private static final List<Pair<Double, Double>> positions2 = new ArrayList<>();

    private static int diceValue;
    private static boolean isFirstPlayerPlaying = true;

    /**
     * This method creates the game board and
     * puts it on the scene
     *
     * @return Scene containing
     * cards for properties and other types.
     */
    public static Scene createScene() {
        Label player1Name = new Label("Player1");
        player1Name.setFont(Font.font("Verdana", 25));
        player1Name.setTextFill(Color.BLACK);

        player1Money.setText("$ " + player1.getMoney().toString());
        player1Money.setFont(Font.font("Verdana", 25));
        player1Money.setTextFill(Color.BLACK);

        Label player2Name = new Label("Player2");
        player2Name.setFont(Font.font("Verdana", 25));
        player2Name.setTextFill(Color.BLACK);

        player2Money.setText("$ " + player2.getMoney().toString());
        player2Money.setFont(Font.font("Verdana", 25));
        player2Money.setTextFill(Color.BLACK);

        VBox player1Box = new VBox();
        player1Box.setAlignment(Pos.TOP_LEFT);
        player1Box.getChildren().addAll(player1Name, player1Money);

        VBox player2Box = new VBox();
        player2Box.setAlignment(Pos.TOP_RIGHT);
        player2Box.getChildren().addAll(player2Name, player2Money);

        board= new GridPane();
        board.setAlignment(Pos.CENTER);

        root = new StackPane();
        root.getChildren().addAll(player1Box, player2Box, board);

        Button menuButton = new Button("Main Menu");

        menuButton.setOnAction(a -> SceneConstants.stage.setScene(MainMenuScreen.createScene()));

        pawn1 = new Circle(20, Color.BLACK);
        pawn2 = new Circle(20, Color.WHITE);

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
                card.getText().setText("Property " + (propertyIndex + 1));
                card.getRect().setStyle(colorList[propertyIndex]);
                propertyIndex++;
            }
        }

        for (Card card:cardList) {
            if (card.isProperty()) {
                String text = card.getText().getText();
                card.getText().setText(text + "\n" + "$" + card.getValue());
            }
        }

        addCardsToBoard(board, cardList);

        setMiddleImage();

        setPositions();

        pawn1.setTranslateX(positions1.get(0).getKey());
        pawn1.setTranslateY(positions1.get(0).getValue());

        pawn2.setTranslateX(positions2.get(0).getKey());
        pawn2.setTranslateY(positions2.get(0).getValue());

        root.getChildren().add(pawn1);
        root.getChildren().add(pawn2);

        rollDice();

        return new Scene(root, SceneConstants.WINDOW_WIDTH, SceneConstants.WINDOW_HEIGHT, Color.GRAY);
    }

    /**
     * This method sets all positions of players
     * on the board with respect to indices
     */
    private static void setPositions() {
        positions1.add(new Pair<>(-300.0,260.0));
        positions1.add(new Pair<>(-300.0,110.0));
        positions1.add(new Pair<>(-300.0,-40.0));
        positions1.add(new Pair<>(-300.0,-190.0));
        positions1.add(new Pair<>(-300.0,-340.0));
        positions1.add(new Pair<>(-150.0,-340.0));
        positions1.add(new Pair<>(0.0,-340.0));
        positions1.add(new Pair<>(150.0,-340.0));
        positions1.add(new Pair<>(300.0,-340.0));
        positions1.add(new Pair<>(300.0,-190.0));
        positions1.add(new Pair<>(300.0,-40.0));
        positions1.add(new Pair<>(300.0,110.0));
        positions1.add(new Pair<>(300.0,260.0));
        positions1.add(new Pair<>(150.0,260.0));
        positions1.add(new Pair<>(0.0,260.0));
        positions1.add(new Pair<>(-150.0,260.0));

        positions1.forEach(p ->
                positions2.add(new Pair<>(p.getKey(), p.getValue()+90)));
    }

    /**
     * This method makes dice rolling animation
     * and calls the makeMovement method
     */
    private static void rollDice(){
        Button rollButton = new Button("Roll");

        VBox vbox = new VBox();
        ImageView imageView = new ImageView(new Image(GameScreen.class.getResource("/dice/1.jpg").toExternalForm(), 250, 250, true, true));
        vbox.getChildren().add(imageView);
        vbox.setAlignment(Pos.CENTER);

        Random random = new Random();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            diceValue = random.nextInt(6) + 1;
            vbox.getChildren().set(0, new ImageView(new Image(
                    GameScreen.class.getResource("/dice/" + diceValue + ".jpg").toExternalForm(), 250, 250, true, true)));
        }));
        timeline.setCycleCount(10);

        if (isFirstPlayerPlaying) {
            rollButton.setPrefSize(100,50);
            vbox.getChildren().add(rollButton);

            rollButton.setOnAction(action -> {
                rollButton.setDisable(true);
                rollButton.setVisible(false);

                timeline.play();
                timeline.setOnFinished(event -> {
                    rollButton.setText("Play");
                    rollButton.setDisable(false);
                    rollButton.setVisible(true);
                    rollButton.setOnAction(a -> {
                        rollButton.setVisible(false);
                        rollButton.setDisable(true);

                        player1.setMoney(player1.getMoney()-100);
                        player1Money.setText("$ " + player1.getMoney().toString());

                        makeMovement(pawn1, positions1, vbox);

                        isFirstPlayerPlaying = false;
                        //root.getChildren().remove(vbox);
                    });
                });
            });

        } else {
            timeline.setDelay(Duration.millis(1500));
            timeline.play();
            timeline.setOnFinished(event -> {
                player2.setMoney(player1.getMoney()-100);
                player2Money.setText("$ " + player1.getMoney().toString());

                makeMovement(pawn2, positions2, vbox);

                isFirstPlayerPlaying = true;
                //root.getChildren().remove(vbox);
            });
        }
        root.getChildren().add(vbox);
    }

    /**
     * This method makes movement animation of a player
     * with respect to rolled dice value
     *
     * @param player Current player
     * @param positions List of pairs of x-y coordinates
     *                  each is a possible position's
     *                  coordinate of indices
     */
    private static void makeMovement(Circle player, List<Pair<Double, Double>> positions, VBox vbox) {
        int currentPositionIndex = getPositionIndex(
                new Pair<>(player.localToScene(player.getCenterX(), player.getCenterY()).getX(),
                        player.localToScene(player.getCenterX(), player.getCenterY()).getY()), positions);
        Polyline polyline = new Polyline();
        polyline.getPoints().addAll(positions.get(currentPositionIndex).getKey(), positions.get(currentPositionIndex).getValue());
        for(int i=1; i<=diceValue; i++) {
            int newPosition = (currentPositionIndex+i) % positions.size();

            polyline.getPoints().addAll(positions.get(newPosition).getKey(), positions.get(newPosition).getValue());
        }
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(diceValue*600));
        pathTransition.setNode(player);
        pathTransition.setPath(polyline);
        pathTransition.setOnFinished(e -> {
                root.getChildren().remove(vbox);
                rollDice();
        });
        if (!isFirstPlayerPlaying) {
            pathTransition.setDelay(Duration.millis(1000));
        }
        pathTransition.play();
    }

    /**
     * This method gets current index of a player
     * on the board.
     *
     * @param position Pair of x-y coordinates of
     *                 local position of player circle
     * @param positions List of pairs of x-y coordinates
     *                  each is a possible position's
     *                  coordinate of indices
     */
    private static int getPositionIndex(Pair<Double, Double> position, List<Pair<Double, Double>> positions) {
        for (int i=0; i<positions.size(); i++) {
            if (((positions.get(i).getKey() + SceneConstants.WINDOW_WIDTH/2.0 == position.getKey())
                    && positions.get(i).getValue() + SceneConstants.WINDOW_HEIGHT/2.0 == position.getValue())) {
                return i;
            }
        }
        return -1;
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
