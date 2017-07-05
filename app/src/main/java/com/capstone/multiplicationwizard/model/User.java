package com.capstone.multiplicationwizard.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Madhuri on 2/16/2017.
 */
public class User implements Parcelable {
    public Uri user_id;
    public String name;
    public Integer maxLevel;
    public Integer highScore;
    public ArrayList<LevelScores> levelScores;

    public User() {
        this.user_id = Uri.EMPTY;
        this.name = null;
        this.maxLevel = 1;
        this.highScore = 0;
    }

    /**
     * Use when reconstructing User object from parcel
     * This will be used only by the 'CREATOR'
     *
     * @param in a parcel to read this object
     */
    public User(Parcel in) {
        this.user_id = Uri.parse(in.readString());
        this.name = in.readString();
        this.maxLevel = in.readInt();
        this.highScore = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_id.toString());
        parcel.writeString(name);
        parcel.writeInt(maxLevel);
        parcel.writeInt(highScore);
        //parcel.writeInt(curLevel);
        //parcel.writeInt(curScore);
    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays
     * <p>
     * If you don’t do that, Android framework will through exception
     * Parcelable protocol requires a Parcelable.Creator object called CREATOR
     */
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserId() {
        return user_id.toString();
    }

    public void setId(String id) {
        this.user_id = Uri.parse(id);
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public Integer getMaxLevel() {
        return this.maxLevel;
    }

    public Integer getHighScore() {
        return this.highScore;
    }

    public void setMaxLevel(int level) {
        maxLevel = new Integer(level);
    }

    public void setHighScore(int score) {
        highScore = new Integer(score);
    }


    public class LevelScores {
        public Integer curLevel;
        public Integer curScore;

        public LevelScores(int level, int score) {
            this.curLevel = level;
            this.curScore = score;
        }

        public Integer getCurLevel() {
            return curLevel;
        }

        public void setCurLevel(int curLevel) {
            curLevel = new Integer(curLevel);
        }

        public Integer getCurScore() {
            return curScore;
        }

        public void setCurScore(int score) {
            curScore = new Integer(curScore);
        }
    }
}
