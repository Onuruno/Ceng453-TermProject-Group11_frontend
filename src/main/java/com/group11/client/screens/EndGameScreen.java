package com.group11.client.screens;

import com.group11.client.constants.SceneConstants;
import com.group11.client.controller.EndGameController;
import com.group11.client.gameObjects.Player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EndGameScreen {

    private static GridPane root = null;

    private static final EndGameController controller = new EndGameController();

    /**
     * This method creates an end game scene with
     * total points of player at the end of the game.
     *
     * @param player Player whose total points will be shown.
     * @return End game Scene
     */
    public static void createScene(Player player) {
        int totalPropertyValue = player.getPropertyList().stream().map(p -> p.getValue()).mapToInt(Integer::intValue).sum();
        int totalMoney = player.getMoney();

        root = new GridPane();
        root.setAlignment(Pos.TOP_LEFT);
        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        Label textLabel = new Label("GAME OVER!!!");
        textLabel.setFont(Font.font("Verdana", 45));
        textLabel.setTextFill(Color.BLACK);

        Label pointLabel = new Label("Total property value: " + totalPropertyValue
                + "\n" + "Total money: " + totalMoney
                + "\n" + "Total points in the game: " + (totalPropertyValue+totalMoney));
        pointLabel.setFont(Font.font("Verdana", 25));
        pointLabel.setTextFill(Color.BLACK);

        Button menuButton = new Button("Main menu");
        menuButton.setPrefSize(100, 50);
        menuButton.setOnAction(action -> {
            SceneConstants.stage.setScene(MainMenuScreen.createScene());
        });

        root.add(textLabel, 1, 0);
        root.add(pointLabel, 1, 3);
        root.add(menuButton, 1, 5);

        setBackground();

        saveGame(totalMoney+totalPropertyValue);

        SceneConstants.stage.setScene(new Scene(root, SceneConstants.WINDOW_WIDTH, SceneConstants.WINDOW_HEIGHT));
    }

    private static void saveGame(int score) {
        controller.saveGame(score);
    }

    /**
     * This method sets the background.
     */
    private static void setBackground() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(AuthenticationScreen.class.getResource("/backgroundPictures/monopoly.jpg").toExternalForm()),
                BackgroundRepeat.ROUND,
                BackgroundRepeat.ROUND,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, true, true));

        root.setBackground(new Background(backgroundImage));
    }
}
