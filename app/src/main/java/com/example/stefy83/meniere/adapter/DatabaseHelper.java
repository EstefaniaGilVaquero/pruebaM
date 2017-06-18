package com.example.stefy83.meniere.adapter;

/**
 * Created by ESTEFANIA GIL on 10/06/2017.
 */
import android.database.Cursor;
import android.database.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "meniereDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_AUDIO = "audio";
    private static final String TABLE_USERS = "users";

    // Audio Table Columns
    private static final String KEY_AUDIO_ID = "id";
    private static final String KEY_AUDIO_DATE = "date";
    private static final String KEY_AUDIO_LEFT_05 = "left_05";
    private static final String KEY_AUDIO_LEFT_1 = "left_1";
    private static final String KEY_AUDIO_LEFT_2 = "left_2";
    private static final String KEY_AUDIO_LEFT_4 = "left_4";
    private static final String KEY_AUDIO_RIGTH_05 = "rigth_05";
    private static final String KEY_AUDIO_RIGTH_1 = "rigth_1";
    private static final String KEY_AUDIO_RIGTH_2 = "rigth_2";
    private static final String KEY_AUDIO_RIGTH_4 = "rigth_4";

    // User Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_PWD = "id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_PWD + " INTEGER" +
                ")";

        String CREATE_AUDIO_TABLE = "CREATE TABLE " + TABLE_AUDIO +
                "(" +
                KEY_AUDIO_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                //KEY_AUDIO_DATE + " INTEGER REFERENCES " + TABLE_USERS + "," + // Define a foreign key
                KEY_AUDIO_DATE + " TEXT, " +
                KEY_AUDIO_LEFT_05 + " TEXT, " +
                KEY_AUDIO_LEFT_1 + " TEXT, " +
                KEY_AUDIO_LEFT_2 + " TEXT, " +
                KEY_AUDIO_LEFT_4 + " TEXT, " +
                KEY_AUDIO_RIGTH_05 + " TEXT, " +
                KEY_AUDIO_RIGTH_1 + " TEXT, " +
                KEY_AUDIO_RIGTH_2 + " TEXT, " +
                KEY_AUDIO_RIGTH_4 + " TEXT " +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_AUDIO_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUDIO);
            onCreate(db);
        }
    }
}