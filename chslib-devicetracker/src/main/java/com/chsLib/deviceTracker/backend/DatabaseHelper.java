package com.chsLib.deviceTracker.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.util.Log;

import com.chsLib.deviceTracker.model.ChsSpeaker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = DatabaseHelper.class.toString();
    private DBInteractListener listener;
    private Handler handler = null;
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

    public static DatabaseHelper createOrOpenDB(Context context) {
        return new DatabaseHelper(context);
    }

    public void setListener(DBInteractListener listener) {
        this.listener = listener;
    }

    public void setListener(Handler handler) {
        this.handler = handler;
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

    public void insertOrUpdateSpeaker(ChsSpeaker speakerCache) {
        // get writable database as we want to write data
        Log.e(TAG, "insertOrUpdateSpeaker: " + speakerCache.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.COLUMN_ID + " = '" + speakerCache.getId() + "'";
        Log.e(TAG, "insertOrUpdateSpeaker: -----------------       selectQuery : " + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        Log.d(TAG, "insertOrUpdateSpeaker: cursor.getCount() " + count);
        cursor.close();
        db.close();
        Log.d(TAG, "insertOrUpdateSpeaker: check whether we have to insert or update the speaker info");
        if (count == 0) {
            Log.e(TAG, "insertOrUpdateSpeaker: ---------->  new Speaker Found");
            insertNewSpeaker(speakerCache);
        } else {
            Log.e(TAG, "insertOrUpdateSpeaker: ----------> Speaker info Need To update");
            updateSpeakerInfo(speakerCache);
        }
    }

    private void insertNewSpeaker(ChsSpeaker speaker) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.COLUMN_ID, speaker.getMacAddress());
        values.put(Constants.COLUMN_NAME, speaker.getName());
        values.put(Constants.COLUMN_IP_ADDRESS, speaker.getIpAddress());
        values.put(Constants.COLUMN_STATUS, 1);
        long id = db.insert(Constants.TABLE_NAME, null, values);
        if (id != -1) {
            // call listener method
            listener.newSpeakerAdded(speaker);
            Log.e(TAG, "New Speaker inserted to db");
        }
        db.close();
    }

    private void updateSpeakerInfo(ChsSpeaker speaker) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.COLUMN_ID, speaker.getMacAddress());
        values.put(Constants.COLUMN_NAME, speaker.getName());
        values.put(Constants.COLUMN_IP_ADDRESS, speaker.getIpAddress());
        values.put(Constants.COLUMN_STATUS, 1);

        // updating row
        long id = db.update(Constants.TABLE_NAME, values, Constants.COLUMN_ID + " = ?",
                new String[]{speaker.getId()});
        Log.d(TAG, "updateSpeakerInfo: id " + id);

        if (id != -1) {
            // call listener method
            Log.e(TAG, "Speaker Info Updated");
            listener.speakerUpdated(speaker);
        }
        db.close();
    }


    public List<ChsSpeaker> getAllSpeakers() {
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
                int status = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_STATUS));
                if (status == 1)
                    speakerData.setOnline(true);
                else
                    speakerData.setOnline(false);
                speakersList.add(speakerData);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        return speakersList;
    }

    public int getSpeakersCount() {
        String countQuery = "SELECT  * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }

    public void deleteSpeakers() {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(Constants.TABLE_NAME, null, null);
        Log.d(TAG, "deleteSpeakers: count = " + count);
        db.close();
    }

    public void refreshSpeakerStatus() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.COLUMN_STATUS, 0);

        // updating row
        long id = db.update(Constants.TABLE_NAME, values, null, null);

        if (id != -1) {
            // call listener method
            Log.e(TAG, "-------------> Speaker status Info Refreshed");
            listener.speakerListRefreshed();
        }
        db.close();
    }

}
