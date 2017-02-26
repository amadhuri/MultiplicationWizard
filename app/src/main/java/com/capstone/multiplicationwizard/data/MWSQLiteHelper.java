package com.capstone.multiplicationwizard.data;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.provider.BaseColumns;
import android.util.Log;

import com.capstone.multiplicationwizard.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Madhuri on 2/16/2017.
 */
public class MWSQLiteHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG_TAG = MWSQLiteHelper.class.getName();
      // Database Name
    private static final String DATABASE_NAME = "wizard.db";
    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Table Names
    private static final String TABLE_USER = "users";
    private static final String TABLE_USER_LEVEL = "user_level";
    // Common column names
    public static final String KEY_ID = BaseColumns._ID;
    public static final String KEY_CREATED_AT = "created_at";
    // User Table - column names
    public static final String KEY_USERNAME = "username";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_HIGHSCORE = "high_score";
    //User level - column names
    public static final String KEY_LEVEL_SCORE = "level_score";

    // Table Create Statements
    // Users table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME
            + " TEXT NOT NULL UNIQUE," + KEY_LEVEL + " INTEGER," + KEY_HIGHSCORE
            + " INTEGER," + KEY_CREATED_AT + " DATETIME" + ")";


    // User level table create statement
    private static final String CREATE_TABLE_USER_LEVEL = "CREATE TABLE "
            + TABLE_USER_LEVEL + "(" + KEY_ID + " INTEGER,"
            + KEY_LEVEL + " INTEGER," + KEY_HIGHSCORE + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME," + "PRIMARY KEY ("
            + KEY_ID +"," + KEY_LEVEL + "))";

    public MWSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_USER_LEVEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER_LEVEL);
        // create new tables
        onCreate(db);
    }

    //CRUD operations

    //Creating a user
    /*public long createUser(User newUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, newUser.getName());
        values.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_USER, null, values);
        return id;
    }*/
    public long createUser(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_USER, null, values);
        return id;
    }

    public User getUser(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + KEY_ID + " = " + user_id;

        Log.e(LOG_TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        User tu = new User();


        tu.setId(new Integer(c.getInt(c.getColumnIndex(KEY_ID))).toString());
        tu.setUsername(c.getString(c.getColumnIndex(KEY_USERNAME)));
        tu.setLevel(c.getInt(c.getColumnIndex(KEY_LEVEL)));
        tu.setScore(c.getInt(c.getColumnIndex(KEY_HIGHSCORE)));

        return tu;
    }

    //getting all users
    /*
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        Log.e(LOG_TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User tu = new User();
                tu.setUser_id(c.getInt(c.getColumnIndex(KEY_ID)));
                tu.setName(c.getString(c.getColumnIndex(KEY_USERNAME)));
                tu.setLevel(c.getInt(c.getColumnIndex(KEY_LEVEL)));
                tu.setScore(c.getInt(c.getColumnIndex(KEY_HIGHSCORE)));

                // adding to todo list
                allUsers.add(tu);
            } while (c.moveToNext());
        }
        return allUsers;
    } */

    public Cursor getAllUsers() {
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        Log.e(LOG_TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     * getting todo count
     */
    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }



}
