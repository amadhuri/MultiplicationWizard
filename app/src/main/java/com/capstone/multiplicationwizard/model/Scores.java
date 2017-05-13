package com.capstone.multiplicationwizard.model;

/**
 * Created by Madhuri on 05/04/17.
 */
public class Scores {

    String user_id = "";
    String level = "";
    String score = "";

    public Scores(String user_id, String level, String score) {
        this.user_id = user_id;
        this.level = level;
        this.score = score;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
