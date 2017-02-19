package com.capstone.multiplicationwizard.model;

/**
 * Created by Madhuri on 2/16/2017.
 */
public class User {
    public int user_id;
    public String name;
    public Integer level;
    public Integer score;

    public User() {
        this.user_id = 0;
        this.name = null;
        this.level = 1;
        this.score = 0;
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, int level, int score) {
        this.name = name;
        this.level = level;
        this.score = score;
    }

    //setters
    public void setUser_id(int id) {
        this.user_id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        level = new Integer(level);
    }

    public void setScore(int score) {
        score = new Integer(score);
    }

    //getters
    public int getUser_id(){
        return this.user_id;
    }
    public String getName() {
        return this.name;
    }

    public Integer getLevel() {
        return this.level;
    }

    public Integer getScore() {
        return this.score;
    }
}
