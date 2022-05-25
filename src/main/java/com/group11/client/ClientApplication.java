package com.group11.client;

import com.group11.client.constants.SceneConstants;
import com.group11.client.screens.AuthenticationScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        SceneConstants.stage = stage;

        Scene scene = AuthenticationScreen.createScene();
        stage.setTitle("Monopoly");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}