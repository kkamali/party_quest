package com.example.kathleenkamali.partyquest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kathleenkamali on 12/23/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "heroDatabase";

    // Contacts table name
    private static final String TABLE_HEROES = "heroes";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_HERO_CLASS = "hero_class";
    private static final String KEY_STAT = "stat";
    private static final String KEY_POWER = "power";
    private static final String KEY_HEALTH = "health";
    private static final String KEY_PLACE = "place";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HEROES_TABLE = "CREATE TABLE " + TABLE_HEROES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID + " TEXT,"
                + KEY_NAME + " TEXT," + KEY_HERO_CLASS + " TEXT," + KEY_STAT + " TEXT," + KEY_POWER + " TEXT," +  KEY_HEALTH + " TEXT," +
                KEY_PLACE + " TEXT" + ")";
        db.execSQL(CREATE_HEROES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEROES);

        // Create tables again
        onCreate(db);
    }

    public void addHero (Hero hero) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, hero.getName());
        values.put(KEY_USER_ID, hero.getUserId());
        values.put(KEY_HERO_CLASS, hero.getHeroClass());
        values.put(KEY_STAT, hero.getStat());
        values.put(KEY_POWER, hero.getPower());
        values.put(KEY_HEALTH, hero.getHealth());
        values.put(KEY_PLACE, "0");

        // Inserting Row
        db.insert(TABLE_HEROES, null, values);
        db.close(); // Closing database connection
    }

    public boolean heroExists(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HEROES, new String[]{KEY_USER_ID}, String.valueOf(id), null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public int updateHero(Hero hero) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HERO_CLASS, hero.getHeroClass());
        values.put(KEY_STAT, hero.getStat());
        values.put(KEY_POWER, hero.getPower());
        values.put(KEY_HEALTH, hero.getHealth());

        // updating row
        return db.update(TABLE_HEROES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(hero.getId()) });
    }

    public Hero getHero(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HEROES, new String[]{KEY_NAME, KEY_HERO_CLASS, KEY_STAT, KEY_POWER, KEY_HEALTH, KEY_USER_ID,
        KEY_PLACE}, String.valueOf(id), null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Hero hero = new Hero(cursor.getString(0), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)), cursor.getString(5), Integer.parseInt(cursor.getString(6)));
        return hero;
    }
}
