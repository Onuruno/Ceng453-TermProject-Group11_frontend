package com.group11.client.controller;

import com.group11.client.constants.NetworkConstants;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class RegisterController {
    /**
     * This method sends POST request to /api/register to add given credentials to the database.
     *
     * @param username Username of the current player
     * @param password Password of the current player
     * @return if successful returns empty string.
     * if not returns Network Error or response error message.
     */
    public String register(String username, String password, String email) {
        try {
            HttpResponse<String> response
                    = Unirest.post(NetworkConstants.API + "api/register")
                    .header("Content-Type", "application/json")
                    .body("{\"username\":\"" + username + "\", \"password\":\"" +
                            password + "\", \"email:\":\"" + email + "\"" + "}")
                    .asString();
            if (response.getStatus() == 200) {
                return "";
            } else {
                return response.getBody();
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
