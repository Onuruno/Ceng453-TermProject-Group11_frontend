package com.group11.client.screens;

import com.group11.client.constants.ErrorConstants;
import com.group11.client.constants.NetworkConstants;
import com.group11.client.constants.SceneConstants;
import com.group11.client.controller.LoginController;
import com.group11.client.controller.RegisterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.group11.client.constants.SceneConstants.LOGIN_STATE;
import static com.group11.client.constants.SceneConstants.REGISTER_STATE;
import static javafx.scene.text.TextAlignment.CENTER;

public class AuthenticationScreen {

    private static GridPane root = null;
    private static final LoginController loginController = new LoginController();
    private static final RegisterController registerController = new RegisterController();
    private static boolean isLoginState = true;

    /**
     * This method creates a scene for the authentication part.
     *
     * @return Scene containing username, password email for the
     * login and register processes.
     */
    public static Scene createScene() {
        root = new GridPane();

        root.setAlignment(Pos.TOP_LEFT);
        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        Label state = new Label(LOGIN_STATE);
        state.setFont(Font.font("Verdana", 25));
        state.setTextFill(Color.BLACK);

        HBox hBoxState = new HBox(15);
        hBoxState.setAlignment(Pos.BOTTOM_CENTER);
        hBoxState.setPadding(new Insets(0, 0, 5, 0));
        hBoxState.getChildren().add(state);

        root.add(hBoxState, 1, 0);

        Label userName = new Label("Username");
        userName.setFont(Font.font("Verdana", 15));
        userName.setTextFill(Color.BLACK);
        userName.setPadding(new Insets(0, 0, 0, 20));
        root.add(userName, 0, 1);

        TextField userNameTextField = new TextField();
        root.add(userNameTextField, 1, 1);

        Label password = new Label("Password");
        password.setFont(Font.font("Verdana", 15));
        password.setTextFill(Color.BLACK);
        password.setPadding(new Insets(0, 0, 0, 20));
        root.add(password, 0, 2);

        PasswordField passwordField = new PasswordField();
        root.add(passwordField, 1, 2);

        Label email = new Label("Email");
        email.setFont(Font.font("Verdana", 15));
        email.setTextFill(Color.BLACK);
        email.setPadding(new Insets(0,0,0,20));

        TextField emailTextField = new TextField();

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        HBox hBoxButtons = new HBox(15);
        hBoxButtons.setAlignment(Pos.BOTTOM_CENTER);
        hBoxButtons.getChildren().add(loginButton);
        hBoxButtons.getChildren().add(registerButton);

        root.add(hBoxButtons, 1, 3);

        Hyperlink hyperlink = new Hyperlink("Forgot password?");
        hyperlink.setTextFill(Color.BLACK);

        root.add(hyperlink, 1, 4);

        Text errorMessage = new Text();
        errorMessage.setWrappingWidth(SceneConstants.LOGIN_WINDOW_WIDTH / 4.0);
        errorMessage.setFill(Color.BLACK);
        errorMessage.setTextAlignment(CENTER);
        root.add(errorMessage, 1, 5);

        hyperlink.setOnAction(event -> {
            SceneConstants.stage.setScene(PasswordResetScreen.createScene());
        });

        loginButton.setOnAction(a -> {
            if (isLoginState) { /* In Login State Login Pressed */
                if (userNameTextField.getText().isEmpty()) {
                    errorMessage.setText(ErrorConstants.USERNAME_ERROR);
                } else if (passwordField.getText().isEmpty()) {
                    errorMessage.setText(ErrorConstants.PASSWORD_ERROR);
                } else {
                    String response = loginController.login(userNameTextField.getText(), passwordField.getText());
                    if (response.equals(ErrorConstants.USERNAME_PASSWORD_ERROR)) {
                        errorMessage.setText(response);
                    } else {
                        errorMessage.setText("");
                    }
                }
            } else { /* In Register State Back(Login) Pressed */
                isLoginState = true;
                state.setText(LOGIN_STATE);
                loginButton.setText("Login");

                root.getChildren().remove(email);
                root.getChildren().remove(emailTextField);
                root.getChildren().remove(hBoxButtons);
                root.add(hBoxButtons, 1, 3);
                root.add(hyperlink, 1, 4);

                errorMessage.setText("");
            }

        });

        registerButton.setOnAction(e -> {
            if (isLoginState) {
                /* In Login State Register Pressed */
                isLoginState = false;
                state.setText(REGISTER_STATE);
                loginButton.setText("Back");

                root.getChildren().remove(hBoxButtons);
                root.add(email, 0, 3);
                root.add(emailTextField, 1, 3);
                root.add(hBoxButtons, 1, 4);
                root.getChildren().remove(hyperlink);

                errorMessage.setText("");
            }
            /* In Register State Register Pressed */
            else if (!userNameTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                String response = registerController.register(userNameTextField.getText(), passwordField.getText(), emailTextField.getText());
                if (response.isEmpty()) {
                    errorMessage.setText(NetworkConstants.REGISTERED);
                } else {
                    errorMessage.setText(response);
                }
            } else {
                errorMessage.setText(ErrorConstants.USERNAME_PASSWORD_EMPTY_ERROR);
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
