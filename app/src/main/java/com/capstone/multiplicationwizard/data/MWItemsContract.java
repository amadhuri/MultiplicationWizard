package com.capstone.multiplicationwizard.data;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

/**
 * Created by Madhuri on 2/17/2017.
 */
public class MWItemsContract {

    public static final int MAX_USERS = 5;
    //The authority of the MWContentProvider
    public static final String AUTHORITY = "com.capstone.multiplicationwizard.MWItems";
    //Constant string for Users table
    public static final String USERS_BASE_PATH = "users";
    public static final String USER_NAME = "username";


    //Constant string for Scores table

    public static final String SCORES_BASE_PATH = "scores";
    //The content URI for the top-level users authority
    public static final Uri USERS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + USERS_BASE_PATH);

    public static final String USERS_SCORES_BASE_PATH = "users_scores";
    public static final Uri SCORES_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + SCORES_BASE_PATH);
    public static final Uri USERS_SCORES_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + USERS_SCORES_BASE_PATH);
    public static final String USER_ID = "user_id";
    public static final String SCORE = "score";
    public static final String LEVEL = "level";

    public static final String LEVEL_UP_SCORE = "20";
    public static int out_off = 5 * 5;  //que * marks
    public static int GAMELEVEL = 12;
}

