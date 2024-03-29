package com.group11.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group11.client.constants.NetworkConstants;
import com.group11.client.dao.GameDao;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.Optional;

public class LeaderBoardController {
    public LeaderBoardController() {

        Unirest.setObjectMapper(new ObjectMapper() {
            private final com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Unirest.setDefaultHeader("accept", "application/json");
        Unirest.setDefaultHeader("Content-Type", "application/json");
        Unirest.setDefaultHeader("Authorization", "Bearer " + NetworkConstants.jwtToken);
    }

    /**
     * This method sends GET request to /api/leaderboard to retrieve weekly/monthly/all records.
     *
     * @param pageLimit Number of records that will be retrieved
     * @param timeRange Parameter used for wanted time range (weekly, monthly or all)
     * @return The records with given restrictions
     */
    public Optional<GameDao[]> getRecords(String pageLimit, String timeRange) {
        try {
            HttpResponse<GameDao[]> response;
            response = Unirest.get(NetworkConstants.API + "api/leaderboard_" + timeRange + "/")
                    .queryString("pageLimit", pageLimit)
                    .asObject(GameDao[].class);
            return Optional.ofNullable(response.getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * This method sends POST request to /api/record/ to add new record to the database.
     *
     * @param username Username of the current player
     * @param score    Score of the current player
     */
    public void addGameDao(String username, Long score) {
        try {
            Long id = getUserID(username);
            Unirest.post(NetworkConstants.API + "api/record/")
                    .queryString("userID", id)
                    .queryString("score", score)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sends GET request to /api/getPlayerID to get id of the user with given username.
     *
     * @param username Username of the current player
     * @return The id of the user
     */
    public Long getUserID(String username) {
        try {
            HttpResponse<Long> response;
            response = Unirest.get(NetworkConstants.API + "api/getPlayerID/")
                    .queryString("username", username)
                    .asObject(Long.class);
            return response.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return 0L;
        }
    }

}
