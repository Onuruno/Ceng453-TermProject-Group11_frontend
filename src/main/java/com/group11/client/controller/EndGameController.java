package com.group11.client.controller;

import com.group11.client.constants.GameConstants;
import com.group11.client.constants.NetworkConstants;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class EndGameController {

    public void saveGame(int score) {
        try {
            HttpResponse<String> response
                    = Unirest.post(NetworkConstants.API + "api/game"+ "?playerID="
                            + GameConstants.UserId + "&score=" + score)
                    .header("Content-Type", "application/json")
                    .asString();
        } catch (Exception e) {}
    }
}
