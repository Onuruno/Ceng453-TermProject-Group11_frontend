package com.group11.client.controller;

import com.group11.client.constants.ErrorConstants;
import com.group11.client.constants.GameConstants;
import com.group11.client.constants.NetworkConstants;
import com.group11.client.constants.SceneConstants;
import com.group11.client.screens.MainMenuScreen;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class LoginController {
    /**
     * This method sends POST request to /api/login to give authentication to the current player.
     *
     * @param username Username of the current player
     * @param password Password of the current player
     * @return if successful returns the JwtToken of the current session.
     * if not returns Network Error message.
     */
    public String login(String username, String password) {
        try {
            HttpResponse<String> response = Unirest
                    .post(NetworkConstants.API + "api/login")
                    .header("Content-Type", "application/json")
                    .body("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}")
                    .asString();
            if (response.getStatus() == 200) {
                setUserId(username);
                GameConstants.username = username;
                NetworkConstants.jwtToken = response.getBody();
                SceneConstants.stage.setScene(MainMenuScreen.createScene());
                return response.getBody();
            } else if (response.getStatus() == 403) {
                return ErrorConstants.USERNAME_PASSWORD_ERROR;
            } else
                return response.getBody();
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    /**
     * This method sends a get request and sets the userId of current user
     * @param username username of current user
     */
    private void setUserId(String username) {
        try {
            HttpResponse<String> response = Unirest
                    .get(NetworkConstants.API + "api/getPlayerID" + "?username=" + username)
                    .header("Content-Type", "application/json")
                    .asString();
            if (response.getStatus() == 200) {
                GameConstants.UserId = Long.valueOf(response.getBody());
            }
            else {
            }
        } catch (Exception e) {}
    }
}
