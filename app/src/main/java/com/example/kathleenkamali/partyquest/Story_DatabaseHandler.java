package com.example.kathleenkamali.partyquest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;

/**
 * Created by kathleenkamali on 1/8/17.
 */

public class Story_DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "storyDatabase";
    private static final String TABLE_STORY = "currentPlace";

    private static final String KEY_ID = "id";
    private static final String KEY_TEXT = "story_text";
    private static final String KEY_ACTIONS = "actions";
    private static final String KEY_PLACE = "place";


    public Story_DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STORY_TABLE = "CREATE TABLE " + TABLE_STORY + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TEXT + " TEXT," + KEY_ACTIONS + " TEXT," + KEY_PLACE + " TEXT)";
        db.execSQL(CREATE_STORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORY);
    }

    public void addStory(Story s) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, s.getStory());
        String[] actions = s.getActions();
        String all_actions = "";
        for (int i = 0; i < actions.length; i++) {
            all_actions += actions[i] + ",";
        }
        values.put(KEY_ACTIONS, all_actions);
        values.put(KEY_PLACE, s.getPlace());

        db.insert(TABLE_STORY, null, values);
        db.close();
    }

    public Story getStory(int place) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STORY, new String[]{KEY_TEXT, KEY_ACTIONS, KEY_PLACE}, String.valueOf(place),
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String[] actions = cursor.getString(1).split(",");
        Story s = new Story(cursor.getString(0), actions, Integer.parseInt(cursor.getString(2)));
        return s;
    }
}
