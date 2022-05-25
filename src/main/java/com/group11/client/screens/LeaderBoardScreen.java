package com.group11.client.screens;

import com.group11.client.constants.NetworkConstants;
import com.group11.client.constants.SceneConstants;
import com.group11.client.controller.LeaderBoardController;
import com.group11.client.dao.GameDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.group11.client.constants.SceneConstants.*;

public class LeaderBoardScreen {

    private static GridPane root = null;

    private static ScrollPane scrollMenuBox;

    private static VBox menuBox;

    private static final LeaderBoardController leaderBoardController = new LeaderBoardController();

    /**
     * This method creates a scene to see the leaderboards.
     *
     * @return Scene containing weekly and monthly leaderboards
     * and buttons for them.
     */
    public static Scene createScene() {
        menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);

        scrollMenuBox = new ScrollPane(menuBox);

        root = new GridPane();

        root.setAlignment(Pos.TOP_LEFT);

        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        Button weeklyButton = new Button("Weekly");
        weeklyButton.setStyle("-fx-font-size: 15pt;");
        weeklyButton.setMaxSize(250,100);

        Button monthlyButton = new Button("Monthly");
        monthlyButton.setStyle("-fx-font-size: 15pt;");
        monthlyButton.setMaxSize(250,100);

        HBox hBoxButtons = new HBox(15);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.getChildren().add(monthlyButton);
        hBoxButtons.getChildren().add(weeklyButton);

        Label state = new Label();
        state.setFont(Font.font("Verdana", 25));
        state.setTextFill(Color.BLACK);

        HBox stateBox = new HBox();
        stateBox.setAlignment(Pos.CENTER);
        stateBox.getChildren().add(state);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 10pt;");

        root.add(hBoxButtons, 1,0);
        root.add(stateBox,1,1);
        root.add(backButton,1,3);

        weeklyButton.setOnAction(a -> {
            state.setText(WEEKLY_STATE);
            Optional<GameDao[]> records = leaderBoardController.getRecords("20", NetworkConstants.WEEKLY);
            records.ifPresent(LeaderBoardScreen::placeRecords);
        });

        monthlyButton.setOnAction(a -> {
            state.setText(MONTHLY_STATE);
            Optional<GameDao[]> records = leaderBoardController.getRecords("20", NetworkConstants.MONTHLY);
            records.ifPresent(LeaderBoardScreen::placeRecords);
        });

        backButton.setOnAction(a -> {
            SceneConstants.stage.setScene(MainMenuScreen.createScene());
        });

        setBackground();

        return new Scene(root, SceneConstants.WINDOW_WIDTH, SceneConstants.WINDOW_HEIGHT);
    }

    private static void placeRecords(GameDao[] records) {
        menuBox.getChildren().clear();
        root.getChildren().remove(scrollMenuBox);
        int i=1;
        for (GameDao record:records) {

            Label rankLabel = new Label(String.valueOf(i));
            rankLabel.setFont(Font.font("Verdana", 20));

            Label usernameLabel = new Label(record.getUsername());
            usernameLabel.setFont(Font.font("Verdana", 20));

            Label scoreLabel = new Label(record.getScore().toString());
            scoreLabel.setFont(Font.font("Verdana", 20));

            HBox recordRow = new HBox();
            recordRow.setAlignment(Pos.CENTER_LEFT);
            recordRow.setSpacing(30);
            recordRow.getChildren().add(rankLabel);
            recordRow.getChildren().add(usernameLabel);
            recordRow.getChildren().add(scoreLabel);

            menuBox.getChildren().add(recordRow);

            i++;
        }
        root.add(scrollMenuBox,1,2);
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
