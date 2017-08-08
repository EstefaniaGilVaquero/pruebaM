package com.symbel.meniere.adapter;

/**
 * Created by ESTEFANIA GIL on 10/06/2017.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "meniereDatabase";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    private static final String TABLE_AUDIO = "audio";
    private static final String TABLE_USERS = "users";

    // Audio Table Columns
    private static final String KEY_AUDIO_ID = "id";
    private static final String KEY_AUDIO_DATE = "date";
    private static final String KEY_AUDIO_LEFT_05_a = "left05_a";
    private static final String KEY_AUDIO_LEFT_1_a = "left1_a";
    private static final String KEY_AUDIO_LEFT_2_a = "left2_a";
    private static final String KEY_AUDIO_LEFT_4_a = "left4_a";
    private static final String KEY_AUDIO_RIGTH_05_a = "rigth05_a";
    private static final String KEY_AUDIO_RIGTH_1_a = "rigth1_a";
    private static final String KEY_AUDIO_RIGTH_2_a = "rigth2_a";
    private static final String KEY_AUDIO_RIGTH_4_a = "rigth4_a";

    // User Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_PWD = "pwd";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*// Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }*/

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE todos (" +
                " _id INTEGER PRIMARY KEY," +
                " name TEXT," +
                " score REAL)";
        db.execSQL(sql);

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY ," +
                KEY_USER_PWD + " INTEGER" +
                ")";

        String CREATE_AUDIO_TABLE = "CREATE TABLE " + TABLE_AUDIO +
                "(" +
                KEY_AUDIO_ID + " INTEGER PRIMARY KEY ," + // Define a primary key
                //KEY_AUDIO_DATE + " INTEGER REFERENCES " + TABLE_USERS + "," + // Define a foreign key
                KEY_AUDIO_DATE + " TEXT, " +
                KEY_AUDIO_LEFT_05_a + " TEXT, " +
                KEY_AUDIO_LEFT_1_a + " TEXT, " +
                KEY_AUDIO_LEFT_2_a + " TEXT, " +
                KEY_AUDIO_LEFT_4_a + " TEXT, " +
                KEY_AUDIO_RIGTH_05_a + " TEXT, " +
                KEY_AUDIO_RIGTH_1_a + " TEXT, " +
                KEY_AUDIO_RIGTH_2_a + " TEXT, " +
                KEY_AUDIO_RIGTH_4_a + " TEXT " +
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
            db.execSQL("DROP TABLE IF EXISTS todos");
            onCreate(db);
        }
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}