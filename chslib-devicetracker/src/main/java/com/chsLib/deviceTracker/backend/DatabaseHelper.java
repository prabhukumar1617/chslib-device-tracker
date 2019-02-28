package com.chsLib.deviceTracker.backendLib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.chsLib.deviceTracker.ChsSpeaker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = DatabaseHelper.class.toString();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "chs_speaker_db";
    public static final String FILE_DIR = "prabhu";


    private DatabaseHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR
                + File.separator + DATABASE_NAME, null);
    }

    protected static DatabaseHelper createOrOpenDB(Context context) {
        return new DatabaseHelper(context);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(Constants.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    protected long insertSpeaker(ChsSpeaker speakerCache) {
        // get writable database as we want to write data

        Log.e(TAG, "insertSpeaker : " + speakerCache.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.COLUMN_ID, speakerCache.getMacAddress());
        values.put(Constants.COLUMN_NAME, speakerCache.getName());
        values.put(Constants.COLUMN_IP_ADDRESS, speakerCache.getIpAddress());
        // insert row
        long id = db.insert(Constants.TABLE_NAME, null, values);
        Log.e(TAG, "insertSpeaker: id :" + id);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }


    protected List<ChsSpeaker> getAllSpeakers() {
        List<ChsSpeaker> speakersList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "getAllSpeakers: cursor count : " + cursor.getCount());
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChsSpeaker speakerData = new ChsSpeaker();
                speakerData.setId(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ID)));
                speakerData.setMacAddress(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ID)));
                speakerData.setName(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME)));
                speakerData.setIpAddress(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_IP_ADDRESS)));
//                speakerData.put(speakerdata.getId(), speakerdata);
                speakersList.add(speakerData);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        return speakersList;
    }

    protected int getSpeakersCount() {
        String countQuery = "SELECT  * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }

    protected void deleteSpeakers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, null, null);
        db.close();
    }

}
