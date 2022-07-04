package com.group11.client.controller;

import com.group11.client.constants.NetworkConstants;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ResetPasswordController {

    /**
     * This method sends POST request to /api/forgotpassword
     * to send password code to user's mail
     *
     * @param username Username of the current player
     * @return if successful returns empty string.
     * if not returns Network Error or response error message.
     */
    public String sendResetLink(String username, String email) {
        try {
            HttpResponse<String> response
                    = Unirest.post(NetworkConstants.API + "api/forgotpassword")
                    .header("Content-Type", "application/json")
                    .body("{\"username\":\"" + username + "\", \"email\":\"" + email + "\"" + "}")
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

    /**
     * This method sends POST request to /api/resetpassword to reset the password
     *
     * @param username Username of the current player
     * @param password Password of the current player
     * @param code reset code that comes user's mail
     * @return if successful returns empty string.
     * if not returns Network Error or response error message.
     */
    public String savePassword(String username, String password, String email, String code) {
        try {
            HttpResponse<String> response
                    = Unirest.post(NetworkConstants.API + "api/resetpassword")
                    .header("Content-Type", "application/json")
                    .body("{\"username\":\"" + username + "\", " +
                            "\"password\":\"" + password + "\", " +
                            "\"email\":\"" + email + "\", " +
                            "\"resetPasswordToken\":\"" + code +"}")
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
