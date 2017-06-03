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

    //The authority of the MWContentProvider
    public static final String AUTHORITY = "com.capstone.multiplicationwizard.MWItems";
    //Constant string for Users table
    public static final String USERS_BASE_PATH = "users";
    //The content URI for the top-level users authority
    public static final Uri USERS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + USERS_BASE_PATH);

    public static final String USER_NAME = "username";

    public static final String USER_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/users";
    public static final String USER_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/user";

    //Constant string for User_level table
    public static final String USER_LEVEL_BASE_PATH = "user_level";
    //The content URI for the top-level user_level authority
    public static final Uri USER_LEVEL_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + USER_LEVEL_BASE_PATH);
    public static final String USER_LEVEL_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/user_levels";
    public static final String USER_LEVEL_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/user_level";

}

