package com.group11.client.screens;

import com.group11.client.constants.CardColorConstants;
import com.group11.client.constants.SceneConstants;
import com.group11.client.enums.PropertyType;
import com.group11.client.gameObjects.Card;

import com.group11.client.gameObjects.Player;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import java.util.stream.Collectors;

public class GameScreen {

    private static final Player player1 = new Player();
    private static final Player player2 = new Player();

    private static final Label player1Money = new Label();
    private static final Label player2Money = new Label();

    private static final List<Pair<Double, Double>> positions1 = new ArrayList<>();
    private static final List<Pair<Double, Double>> positions2 = new ArrayList<>();

    private static final Color player1Color = Color.RED;
    private static final Color player2Color = Color.GOLDENROD;

    private static StackPane root = null;   //the root
    private static GridPane board = null;   //game board on the root
    private static List<Card> cardList = null;  //cards on the board

    private static Circle pawn1;  //pawn1's pawn
    private static Circle pawn2;  //pawn2's pawn

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
        setInitials();

        createCards();

        addCardsToBoard(board, cardList);

        setMiddleImage();

        setPositions();

        createPawns();

        rollDice();

        return setCheat();
    }

    /**
     * This method creates final scene and adds
     * a cheat to it.
     */
    public static Scene setCheat() {
        Scene scene = new Scene(root, SceneConstants.WINDOW_WIDTH, SceneConstants.WINDOW_HEIGHT, Color.GRAY);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if ((event.getCode() == KeyCode.DIGIT9 || event.getCode() == KeyCode.NUMPAD9) && event.isControlDown()) {
                endGame();
            }
        });
        return scene;
    }

    /**
     * This method creates player name-money boxes
     * and puts them on root with board
     */
    private static void setInitials() {
        Label player1Name = new Label("Player1");
        player1Name.setFont(Font.font("Verdana", 25));
        player1Name.setTextFill(player1Color);

        player1Money.setText("$ " + player1.getMoney().toString());
        player1Money.setFont(Font.font("Verdana", 25));
        player1Money.setTextFill(player1Color);

        Label player2Name = new Label("Player2");
        player2Name.setFont(Font.font("Verdana", 25));
        player2Name.setTextFill(player2Color);

        player2Money.setText("$ " + player2.getMoney().toString());
        player2Money.setFont(Font.font("Verdana", 25));
        player2Money.setTextFill(player2Color);

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
    }

    /**
     * This method creates cards, their values
     * and names
     */
    private static void createCards() {
        Random rand = new Random();
        int randInt = rand.nextInt(9);
        int[] taxSpaceLocations = {1,3,5,7,8,9,11,13,15};
        int taxSpaceIndex = taxSpaceLocations[randInt];

        String[] colorList = {CardColorConstants.BLANCHEDALMOND, CardColorConstants.BLANCHEDALMOND,
                CardColorConstants.LIGHTGREEN, CardColorConstants.LIGHTGREEN, CardColorConstants.TEAL,
                CardColorConstants.TEAL, CardColorConstants.THISTLE, CardColorConstants.STEELBLUE};

        cardList = new ArrayList<>();
        for (int i=0; i<16; i++) {
            cardList.add(new Card(""));
        }

        createNonProperties(cardList, taxSpaceIndex);

        int propertyIndex = 0;
        int[] propertyValues = {100, 100, 200, 200, 300, 300, 400, 500};
        for (Card card:cardList) {
            if (card.getText().getText().isEmpty()) {
                card.setPropertyType(PropertyType.PROPERTY);
                card.setValue(propertyValues[propertyIndex]);
                card.getText().setText("Property " + (propertyIndex + 1));
                card.getRect().setStyle(colorList[propertyIndex]);
                propertyIndex++;
            }
        }

        for (Card card:cardList) {
            if (card.getPropertyType().equals(PropertyType.PROPERTY) || card.getPropertyType().equals(PropertyType.FERRY)) {
                String text = card.getText().getText();
                card.getText().setText(text + "\n" + "$" + card.getValue());
            }
        }
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
        cardList.get(0).setPropertyType(PropertyType.START);
        cardList.get(0).getRect().setStyle(CardColorConstants.PINK);

        cardList.get(2).getText().setText("Ferry 1");
        cardList.get(2).setPropertyType(PropertyType.FERRY);
        cardList.get(2).setValue(250);
        cardList.get(2).getRect().setStyle(CardColorConstants.DEEPSKYBLUE);

        cardList.get(4).getText().setText("Jail");
        cardList.get(4).setPropertyType(PropertyType.JAIL);
        cardList.get(4).getRect().setStyle(CardColorConstants.DARKSLATEBLUE);

        cardList.get(6).getText().setText("Ferry 2");
        cardList.get(6).setPropertyType(PropertyType.FERRY);
        cardList.get(6).setValue(250);
        cardList.get(6).getRect().setStyle(CardColorConstants.DEEPSKYBLUE);

        cardList.get(10).getText().setText("Ferry 3");
        cardList.get(10).setPropertyType(PropertyType.FERRY);
        cardList.get(10).setValue(250);
        cardList.get(10).getRect().setStyle(CardColorConstants.DEEPSKYBLUE);

        cardList.get(12).getText().setText("Go Jail");
        cardList.get(12).setPropertyType(PropertyType.GO_JAIL);
        cardList.get(12).getRect().setStyle(CardColorConstants.DARKSLATEBLUE);

        cardList.get(14).getText().setText("Ferry 4");
        cardList.get(14).setPropertyType(PropertyType.FERRY);
        cardList.get(14).setValue(250);
        cardList.get(14).getRect().setStyle(CardColorConstants.DEEPSKYBLUE);

        cardList.get(taxSpaceIndex).getText().setText("Tax Space");
        cardList.get(taxSpaceIndex).setPropertyType(PropertyType.TAX);
        cardList.get(taxSpaceIndex).getRect().setStyle(CardColorConstants.TOMATO);
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
     * This method creates a pawn for each player
     */
    private static void createPawns() {
        pawn1 = new Circle(20, player1Color);
        pawn2 = new Circle(20, player2Color);

        pawn1.setTranslateX(positions1.get(0).getKey());
        pawn1.setTranslateY(positions1.get(0).getValue());

        pawn2.setTranslateX(positions2.get(0).getKey());
        pawn2.setTranslateY(positions2.get(0).getValue());

        root.getChildren().add(pawn1);
        root.getChildren().add(pawn2);
    }

    /**
     * This method makes dice rolling animation
     * and calls the makeMovement method
     */
    private static void rollDice(){
        Button rollButton = new Button("Roll");

        VBox vbox = new VBox();
        ImageView imageView = new ImageView(new Image(GameScreen.class.getResource("/dice/1.jpg").toExternalForm(), 175, 175, true, true));
        vbox.getChildren().add(imageView);
        vbox.setAlignment(Pos.CENTER);

        Random random = new Random();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
            diceValue = random.nextInt(6) + 1;
            vbox.getChildren().set(0, new ImageView(new Image(
                    GameScreen.class.getResource("/dice/" + diceValue + ".jpg").toExternalForm(), 175, 175, true, true)));
        }));
        timeline.setCycleCount(10);

        if (isFirstPlayerPlaying) {
            if (player1.getJailValue() > 0) {
                player1.decreaseJail();
                player1.resetSixDice();
                isFirstPlayerPlaying = !isFirstPlayerPlaying;
                rollDice();
                return;
            } else {
                rollButton.setPrefSize(100, 50);
                vbox.getChildren().add(rollButton);

                rollButton.setOnAction(action -> {
                    rollButton.setDisable(true);
                    rollButton.setVisible(false);

                    timeline.setOnFinished(event -> {
                        if ((player1.getSixDice() == 1) && (diceValue == 6)) {
                            thirdSix(pawn1, player1, positions1, vbox);
                        } else {
                            rollButton.setText("Play");
                            rollButton.setDisable(false);
                            rollButton.setVisible(true);
                            rollButton.setOnAction(a -> {
                                rollButton.setVisible(false);
                                rollButton.setDisable(true);
                                makeMovement(pawn1, positions1, vbox);
                            });
                        }
                    });
                    timeline.play();
                });
            }
        } else {
            if (player2.getJailValue() > 0) {
                player2.decreaseJail();
                player1.resetSixDice();
                isFirstPlayerPlaying = !isFirstPlayerPlaying;
                rollDice();
                return;
            } else {
                timeline.play();
                timeline.setOnFinished(event -> {
                    if ((player2.getSixDice() == 1) && (diceValue == 6)) {
                        thirdSix(pawn2, player2, positions2, vbox);
                    } else {
                        makeMovement(pawn2, positions2, vbox);
                    }
                });
            }
        }
        root.getChildren().add(vbox);
    }

    /**
     * This method moves player to jail after it
     * rolls third consecutive dice with value of 6.
     *
     * @param pawn Player's pawn
     * @param player Player's itself
     * @param positions Possible positions of player on the board
     * @param vbox Vbox that contains Dice view
     */
    private static void thirdSix(Circle pawn, Player player, List<Pair<Double, Double>> positions, VBox vbox) {
        int currentPositionIndex = getPositionIndex(pawn, positions);

        Polyline polyline = new Polyline();
        polyline.getPoints().addAll(positions.get(currentPositionIndex).getKey(), positions.get(currentPositionIndex).getValue(),
                positions.get(4).getKey(), positions.get(4).getValue());

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setNode(pawn);
        pathTransition.setPath(polyline);
        pathTransition.setOnFinished(e -> {
            player.sendJail();
            player.resetSixDice();
            isFirstPlayerPlaying = !isFirstPlayerPlaying;
            root.getChildren().remove(vbox);
            rollDice();
        });
        pathTransition.play();
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
        int currentPositionIndex = getPositionIndex(player, positions);
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
            if (currentPositionIndex+diceValue >= cardList.size()) {
                if (isFirstPlayerPlaying) {
                    player1.setMoney(player1.getMoney()+100);
                    player1Money.setText("$ " + player1.getMoney().toString());
                } else {
                    player2.setMoney(player2.getMoney()+100);
                    player2Money.setText("$ " + player2.getMoney().toString());
                }
            }
            root.getChildren().remove(vbox);
            afterMoveProcess((currentPositionIndex+diceValue)%cardList.size());
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
     * @param player Player's pawn
     * @param positions List of pairs of x-y coordinates
     *                  each is a possible position's
     *                  coordinate of indices
     */
    private static int getPositionIndex(Circle player, List<Pair<Double, Double>> positions) {
        Pair<Double, Double> position = new Pair<>(player.localToScene(player.getCenterX(), player.getCenterY()).getX(),
                player.localToScene(player.getCenterX(), player.getCenterY()).getY());
        for (int i=0; i<positions.size(); i++) {
            if (((positions.get(i).getKey() + SceneConstants.WINDOW_WIDTH/2.0 == position.getKey())
                    && positions.get(i).getValue() + SceneConstants.WINDOW_HEIGHT/2.0 == position.getValue())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method checks all possibilities after moving a
     * new board position.
     *
     * @param positionIndex Player's position on the board
     */
    private static void afterMoveProcess(int positionIndex) {
        Card currentCard = cardList.get(positionIndex);
        int propertyValue = currentCard.getValue();

        if (currentCard.getPropertyType().equals(PropertyType.JAIL)) {
            if (isFirstPlayerPlaying) {
                player1.sendJail();
            } else {
                player2.sendJail();
            }
        } else if (currentCard.getPropertyType().equals(PropertyType.GO_JAIL)) {
            Polyline polyline = new Polyline();

            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(3000));
            pathTransition.setDelay(Duration.millis(1000));
            if (isFirstPlayerPlaying) {
                polyline.getPoints().addAll(positions1.get(12).getKey(), positions1.get(12).getValue(),
                        positions1.get(4).getKey(), positions1.get(4).getValue());
                pathTransition.setNode(pawn1);
                pathTransition.setOnFinished(event -> {
                    player1.sendJail();
                    rollDice();
                });
            } else {
                polyline.getPoints().addAll(positions2.get(12).getKey(), positions2.get(12).getValue(),
                        positions2.get(4).getKey(), positions2.get(4).getValue());
                pathTransition.setNode(pawn2);
                pathTransition.setOnFinished(event -> {
                    player2.sendJail();
                    rollDice();
                });
            }
            pathTransition.setPath(polyline);
            pathTransition.play();
            isFirstPlayerPlaying = !isFirstPlayerPlaying;
            return;
        } else if (currentCard.getPropertyType().equals(PropertyType.TAX)) {
            if (isFirstPlayerPlaying) {
                if (player1.getMoney() < 50) {
                    endGame();
                }
                player1.setMoney(player1.getMoney()-50);
                player1Money.setText("$ " + player1.getMoney().toString());
            } else {
                if (player2.getMoney() < 50) {
                    endGame();
                }
                player2.setMoney(player2.getMoney()-50);
                player2Money.setText("$ " + player2.getMoney().toString());
            }
        } else if (currentCard.getPropertyType().equals(PropertyType.PROPERTY) || currentCard.getPropertyType().equals(PropertyType.FERRY)) {
            if (!currentCard.isHasBought()) {
                if (isFirstPlayerPlaying && (player1.getMoney() >= currentCard.getValue())) {
                    Label questionLabel = new Label("Would you like to buy this \n"
                            + currentCard.getPropertyType().toString().toLowerCase() + " for " + currentCard.getValue() + " dollars?");
                    questionLabel.setStyle("-fx-font: 24 arial;");
                    questionLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    questionLabel.setBorder(new Border(
                            new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

                    Button yesButton = new Button("Yes");
                    yesButton.setPrefSize(100, 50);
                    yesButton.setStyle("-fx-font: 24 arial;");

                    Button noButton = new Button("No");
                    noButton.setPrefSize(100, 50);
                    noButton.setStyle("-fx-font: 24 arial;");

                    HBox buttonBox = new HBox(5);
                    buttonBox.setAlignment(Pos.CENTER);
                    buttonBox.getChildren().addAll(yesButton, noButton);

                    VBox questionBox = new VBox(5);
                    questionBox.setPrefSize(250, 100);
                    questionBox.setAlignment(Pos.CENTER);
                    questionBox.getChildren().addAll(questionLabel, buttonBox);

                    yesButton.setOnAction(a -> {
                        player1.setMoney(player1.getMoney() - currentCard.getValue());
                        player1Money.setText("$ " + player1.getMoney().toString());
                        player1.getPropertyList().add(currentCard);

                        currentCard.setHasBought(true);
                        currentCard.getText().setFill(player1Color);
                        root.getChildren().remove(questionBox);
                        diceCheckAndContinue();
                    });
                    noButton.setOnAction(a -> {
                        root.getChildren().remove(questionBox);
                        diceCheckAndContinue();
                    });
                    root.getChildren().add(questionBox);
                    return;
                } else if (!isFirstPlayerPlaying && (player2.getMoney() >= currentCard.getValue())) {
                    player2.setMoney(player2.getMoney() - currentCard.getValue());
                    player2Money.setText("$ " + player2.getMoney().toString());
                    player2.getPropertyList().add(currentCard);

                    currentCard.setHasBought(true);
                    currentCard.getText().setFill(player2Color);
                }
            } else if (isFirstPlayerPlaying && player2.getPropertyList().contains(currentCard)) {
                if (currentCard.getPropertyType().equals(PropertyType.PROPERTY)) {
                    if (player1.getMoney() < (propertyValue / 10)) {
                        endGame();
                    }
                    player1.setMoney(player1.getMoney() - propertyValue / 10);
                    player2.setMoney(player2.getMoney() + propertyValue / 10);
                } else {
                    int numberOfFerries = player2.getPropertyList().stream().filter(p ->
                            p.getPropertyType().equals(PropertyType.FERRY)).collect(Collectors.toList()).size();
                    if (player1.getMoney() < ((numberOfFerries * propertyValue) / 10)) {
                        endGame();
                    }
                    player1.setMoney(player1.getMoney() - (numberOfFerries * propertyValue) / 10);
                    player2.setMoney(player2.getMoney() + (numberOfFerries * propertyValue) / 10);
                }
                player1Money.setText("$ " + player1.getMoney().toString());
                player2Money.setText("$ " + player2.getMoney().toString());
            } else if (!isFirstPlayerPlaying && player1.getPropertyList().contains(currentCard)) {
                if (currentCard.getPropertyType().equals(PropertyType.PROPERTY)) {
                    if (player2.getMoney() < (propertyValue / 10)) {
                        endGame();
                    }
                    player1.setMoney(player1.getMoney() + propertyValue / 10);
                    player2.setMoney(player2.getMoney() - propertyValue / 10);
                } else {
                    int numberOfFerries = player1.getPropertyList().stream().filter(p ->
                            p.getPropertyType().equals(PropertyType.FERRY)).collect(Collectors.toList()).size();
                    if (player2.getMoney() < ((numberOfFerries * propertyValue) / 10)) {
                        endGame();
                    }
                    player1.setMoney(player1.getMoney() + (numberOfFerries * propertyValue) / 10);
                    player2.setMoney(player2.getMoney() - (numberOfFerries * propertyValue) / 10);
                }
                player1Money.setText("$ " + player1.getMoney().toString());
                player2Money.setText("$ " + player2.getMoney().toString());
            }
        }
        diceCheckAndContinue();
    }

    /**
     * This method checks whether last rolled dice
     * is 6 in order to double rolling rule.
     */
    private static void diceCheckAndContinue() {
        if (diceValue != 6) {
            if (isFirstPlayerPlaying) {
                player1.resetSixDice();
            } else {
                player2.resetSixDice();
            }
            isFirstPlayerPlaying = !isFirstPlayerPlaying;
        } else {
            if (isFirstPlayerPlaying) {
                player1.decreaseSixDice();
            } else {
                player2.decreaseSixDice();
            }
        }
        rollDice();
    }

    /**
     * This method sets the scene as EndGameScreen.
     */
    private static void endGame() {
        EndGameScreen.createScene(player1);
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
