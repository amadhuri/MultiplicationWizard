package com.capstone.multiplicationwizard.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Madhuri on 2/17/2017.
 */
public class MWContentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.capstone.multiplicationwizard.MWContentProvider";
    private MWSQLiteHelper mHelper = null;

    // used for the UriMacher
    private static final int USERS = 10;
    private static final int USER_ID = 20;
    private static final int USER_LEVELS = 30;
    private static final int USER_LEVEL_ID = 40;

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(MWItemsContract.AUTHORITY, MWItemsContract.USERS_BASE_PATH, USERS);
        sURIMatcher.addURI(MWItemsContract.AUTHORITY, MWItemsContract.USERS_BASE_PATH + "/#", USER_ID);
        sURIMatcher.addURI(MWItemsContract.AUTHORITY, MWItemsContract.USER_LEVEL_BASE_PATH, USER_LEVELS);
        sURIMatcher.addURI(MWItemsContract.AUTHORITY, MWItemsContract.USER_LEVEL_BASE_PATH + "/#", USER_LEVEL_ID);
    }
    @Override
    public boolean onCreate() {
        mHelper = new MWSQLiteHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        switch(sURIMatcher.match(uri)) {
            case USERS:
                return mHelper.getAllUsers();
            default:
                throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        switch(sURIMatcher.match(uri)) {
            case USERS:
                return MWItemsContract.USER_CONTENT_TYPE;
            case USER_ID:
                return MWItemsContract.USER_CONTENT_ITEM_TYPE;
            case USER_LEVELS:
                return MWItemsContract.USER_LEVEL_CONTENT_TYPE;
            case USER_LEVEL_ID:
                return MWItemsContract.USER_LEVEL_CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupport URI:"+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
       /* if( (sURIMatcher.match(uri) != USERS) || (sURIMatcher.match(uri) != USER_LEVELS )) {
            throw new IllegalArgumentException("Unsupported URI for insertion:" + uri);
        }*/

        if (sURIMatcher.match(uri) == USERS) {
            long id = mHelper.createUser(contentValues);
            return getUriForId(id,uri);
        } else { //TODO USERS_LEVELS
            return null;
        }
    }

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            return itemUri;
        }
        throw new SQLException("Problem while inserting into uri:"+uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    /**
     * get datetime
     * */
    @TargetApi(Build.VERSION_CODES.N)
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
