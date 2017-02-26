package com.capstone.multiplicationwizard.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Madhuri on 2/16/2017.
 */
public class User implements Parcelable {
    public Uri user_id;
    public String name;
    public Integer level;
    public Integer score;

    public User() {
        this.user_id = Uri.EMPTY;
        this.name = null;
        this.level = 1;
        this.score = 0;
    }

    /**
     * Use when reconstructing User object from parcel
     * This will be used only by the 'CREATOR'
     * @param in a parcel to read this object
     */
    public User(Parcel in) {
        this.user_id = Uri.parse(in.readString());
        this.name = in.readString();
        this.level = in.readInt();
        this.score = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_id.toString());
        parcel.writeString(name);
        parcel.writeInt(level);
        parcel.writeInt(score);
    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays
     *
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

    public Integer getLevel() {
        return this.level;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setLevel(int level) {
        level = new Integer(level);
    }

    public void setScore(int score) {
        score = new Integer(score);
    }

}
