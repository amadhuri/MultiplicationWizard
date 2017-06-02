package com.capstone.multiplicationwizard.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.capstone.multiplicationwizard.model.Scores;
import com.capstone.multiplicationwizard.model.User;

import java.util.ArrayList;

/**
 * Created by Madhuri on 05/04/17.
 */
public class MWSQLiteHelperNew extends SQLiteOpenHelper
{

    public static int out_off = 5 * 5;  //que * marks
    public static  String name = "wizardNew.db";
    public static  int version = 2;


    private static final String TABLE_USER = "users";
    public static final String KEY_ID = "ID";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_CREATED_AT = "created_at";



    private static final String TABLE_SCORE = "scores";
    private static final String KEY_SCORE = "score";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_USER = "user_id";



    public MWSQLiteHelperNew(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_USER = "CREATE TABLE "
                + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME
                + " TEXT NOT NULL UNIQUE," + KEY_CREATED_AT + " DATETIME" + ")";


        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_SCORE = "CREATE TABLE "
                + TABLE_SCORE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER +" INTEGER ,"
                + KEY_LEVEL +" INTEGER ,"
                + KEY_SCORE +" INTEGER ,"
                + KEY_CREATED_AT + " DATETIME" + ")";

        db.execSQL(CREATE_TABLE_SCORE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);

        onCreate(db);
    }


    public long createUser(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME,name);
        long id = db.insert(TABLE_USER, null, values);

        return id;
    }

    public Cursor getUsers() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,null,null,null,null,null,null);
        return cursor;
    }

    public int deleteUser(String id)
    {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(TABLE_USER,KEY_ID+"=?",new String[]{id});

    }

    public long addScore(Scores score)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER,score.getUser_id());
        values.put(KEY_LEVEL,score.getLevel());
        values.put(KEY_SCORE,score.getScore());
        long id = db.insert(TABLE_SCORE, null, values);

        return id;
    }


    public int updateScore(Scores score)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LEVEL,score.getLevel());
        values.put(KEY_SCORE,score.getScore());

        int id = db.update(TABLE_SCORE, values,KEY_USER+"=? AND "+KEY_LEVEL+"=?",new String[]{score.getUser_id(),score.getLevel()});

        return id;
    }

    public boolean isPlayedLevel(String user_id,String level)
    {
        boolean result = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_SCORE, null,KEY_USER+"= ? AND "+KEY_LEVEL+"=?", new String[]{user_id,level},null, null,null);

        if(c != null)
        {
            if(c.getCount() > 0)
            {
                result = true;
            }
            else{
                result = false;
            }
        }
        else {
            result = false;
        }

        return result;
    }


    public int getMaxLevel(String user_id)
    {
        int level = -1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT MAX("+KEY_LEVEL+") as mLevel from "+TABLE_SCORE+" WHERE "+KEY_USER+" = '"+user_id+"'",null);

        if(c != null)
        {
            c.moveToFirst();

            for (int i = 0;i < c.getCount() ; i++)
            {
                level = c.getInt(c.getColumnIndex("mLevel"));
                c.moveToNext();
            }
            c.close();
        }

        return level;
    }

    public int getScore(String user_id,String level)
    {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_SCORE, null,KEY_USER+"= ? AND "+KEY_LEVEL+"=?", new String[]{user_id,level},null, null,null);

        if(c != null)
        {
            c.moveToFirst();

            for (int i = 0;i < c.getCount() ; i++)
            {
                result = c.getInt(c.getColumnIndex(KEY_SCORE));
                c.moveToNext();
            }
            c.close();
        }

        return result;
    }

    public int allScore(String user_id)
    {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_SCORE, null,KEY_USER+"= ?", new String[]{user_id},null, null,null);

        if(c != null)
        {
            c.moveToFirst();

            for (int i = 0;i < c.getCount() ; i++)
            {
                result += c.getInt(c.getColumnIndex(KEY_SCORE));
                c.moveToNext();
            }
            c.close();
        }

        return result;
    }


    public ArrayList<Scores> getLevelScores(String user_id) {

        ArrayList<Scores>  arr_scores = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_SCORE, null,KEY_USER+"=? and "+KEY_SCORE+" > 20", new String[]{user_id},null, null,null);

        if(c != null)
        {
            c.moveToFirst();

            for (int i = 0;i < c.getCount() ; i++)
            {
                int userid = c.getInt(c.getColumnIndex(KEY_USER));
                int level = c.getInt(c.getColumnIndex(KEY_LEVEL));
                int score = c.getInt(c.getColumnIndex(KEY_SCORE));

                arr_scores.add(new Scores(""+userid,""+level,""+score));
                c.moveToNext();
            }
            c.close();
        }

        return arr_scores;
    }
}
