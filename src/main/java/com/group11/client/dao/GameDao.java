package com.group11.client.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDao {
    /**
     * The username of the record owner
     */
    private String username;

    /**
     * Score of the user
     */
    private Integer score;
}
