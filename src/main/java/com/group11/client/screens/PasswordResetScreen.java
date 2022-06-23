package com.group11.client.screens;

import com.group11.client.constants.SceneConstants;
import com.group11.client.controller.ResetPasswordController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PasswordResetScreen {

    private static GridPane root = null;

    private static ResetPasswordController controller = new ResetPasswordController();

    public static Scene createScene() {
        root = new GridPane();

        root.setAlignment(Pos.TOP_LEFT);
        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        Label userName = new Label("Username");
        userName.setFont(Font.font("Verdana", 15));
        userName.setTextFill(Color.BLACK);
        userName.setPadding(new Insets(0, 0, 0, 20));
        root.add(userName, 0, 1);

        TextField userNameTextField = new TextField();
        root.add(userNameTextField, 1, 1);

        Label email = new Label("Email");
        email.setFont(Font.font("Verdana", 15));
        email.setTextFill(Color.BLACK);
        email.setPadding(new Insets(0, 0, 0, 20));
        root.add(email, 0, 2);

        TextField emailTextField = new TextField();
        root.add(emailTextField, 1, 2);

        Button sendButton = new Button("Send reset link");
        Button backButton = new Button("Back");
        Button saveButton = new Button("Save");

        HBox hBoxButtons = new HBox(15);
        hBoxButtons.setAlignment(Pos.BOTTOM_CENTER);
        hBoxButtons.getChildren().addAll(backButton, sendButton);

        root.add(hBoxButtons, 1, 3);

        Label password = new Label("New Password");
        password.setFont(Font.font("Verdana", 15));
        password.setTextFill(Color.BLACK);
        password.setPadding(new Insets(0, 0, 0, 20));

        TextField passwordField = new TextField();

        Label code = new Label("Code Sent");
        code.setFont(Font.font("Verdana", 15));
        code.setTextFill(Color.BLACK);
        code.setPadding(new Insets(0, 0, 0, 20));

        TextField codeField = new TextField();

        backButton.setOnAction(a -> {
            SceneConstants.stage.setScene(AuthenticationScreen.createScene());
        });

        sendButton.setOnAction(e -> {
            if (!emailTextField.getText().isEmpty() && !userNameTextField.getText().isEmpty()) {
                controller.sendResetLink(userNameTextField.getText(), emailTextField.getText());

                root.getChildren().remove(hBoxButtons);
                root.add(password, 0, 3);
                root.add(passwordField, 1, 3);
                root.add(code, 0, 4);
                root.add(codeField, 1, 4);

                hBoxButtons.getChildren().remove(sendButton);
                hBoxButtons.getChildren().add(saveButton);
                root.add(hBoxButtons, 1, 5);
            }
        });

        saveButton.setOnAction(e -> {
            if (!userNameTextField.getText().isEmpty() && !emailTextField.getText().isEmpty()
                    && !passwordField.getText().isEmpty() && !codeField.getText().isEmpty()) {
                String response = controller.savePassword(userNameTextField.getText(), passwordField.getText(),
                        emailTextField.getText(), codeField.getText());
                System.out.println(response);
            }
        });

        setBackground();

        return new Scene(root, SceneConstants.LOGIN_WINDOW_WIDTH, SceneConstants.LOGIN_WINDOW_HEIGHT);
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
