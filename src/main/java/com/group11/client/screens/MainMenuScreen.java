package com.group11.client.screens;

import com.group11.client.constants.SceneConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class MainMenuScreen {

    private static GridPane root = null;

    /**
     * This method creates a scene for the authentication part.
     *
     * @return Scene containing new game and leaderboard buttons.
     */
    public static Scene createScene() {
        root = new GridPane();

        root.setAlignment(Pos.TOP_LEFT);

        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        Button newGameButton = new Button("New Game");
        newGameButton.setStyle("-fx-font-size: 30pt;");
        newGameButton.setMaxSize(250,100);

        Button leaderboardButton = new Button("Leaderboard");
        leaderboardButton.setStyle("-fx-font-size: 15pt;");
        leaderboardButton.setMaxSize(250,100);

        VBox vBoxButtons = new VBox(15);
        vBoxButtons.setAlignment(Pos.CENTER);
        vBoxButtons.getChildren().add(newGameButton);
        vBoxButtons.getChildren().add(leaderboardButton);

        root.add(vBoxButtons, 1,0);

        leaderboardButton.setOnAction(a -> {
            SceneConstants.stage.setScene(LeaderBoardScreen.createScene());
        });

        newGameButton.setOnAction(a -> {
            SceneConstants.stage.setScene(GameScreen.createScene());
        });

        setBackground();

        return new Scene(root, SceneConstants.WINDOW_WIDTH, SceneConstants.WINDOW_HEIGHT);
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
