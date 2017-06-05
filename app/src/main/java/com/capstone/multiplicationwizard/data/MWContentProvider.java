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
    private MWSQLiteHelperNew mHelper = null;

    // used for the UriMacher
    private static final int USERS = 10;
    private static final int SCORES = 20;
    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(MWItemsContract.AUTHORITY, MWItemsContract.USERS_BASE_PATH, USERS);
        sURIMatcher.addURI(MWItemsContract.AUTHORITY, MWItemsContract.SCORES_BASE_PATH,SCORES);
    }
    @Override
    public boolean onCreate() {
        mHelper = new MWSQLiteHelperNew(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        switch(sURIMatcher.match(uri)) {
            case USERS:
                return mHelper.getUsers();
            case SCORES:
                return mHelper.
            default:
                throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        switch(sURIMatcher.match(uri)) {
            case USERS:
                return MWItemsContract.USER_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unsupport URI:"+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        if (sURIMatcher.match(uri) == USERS) {
            String name = contentValues.getAsString(MWItemsContract.USER_NAME);
            long id = mHelper.createUser(name);
            return getUriForId(id,uri);
        } else { //TODO USERS_LEVELS
            return null;
        }
    }

    private Uri getUriForId(long id, Uri uri) {
        Uri itemUri = ContentUris.withAppendedId(uri, id);
        return itemUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {

        if(sURIMatcher.match(uri) == -1) {
            return -1;
        }
        mHelper.deleteUser(strings[0]);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        return 0;
    }


}
