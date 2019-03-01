package com.chsLib.deviceTracker.backend;

import android.content.Context;
import android.util.Log;

import com.chsLib.deviceTracker.model.ChsSpeaker;

import java.util.List;

public class DBInteract {
    private final String TAG = DBInteract.class.toString();

    private Context context;
    private DatabaseHelper db;
    private DBInteractListener listener;

    public DBInteract(Context context, DBInteractListener listener) {
        this.context = context;
        db = DatabaseHelper.createOrOpenDB(context);
        db.setListener(dbInteractListener);
        this.listener = listener;
        Log.e(TAG, "DBInteract: Object Created");
    }

    public void insertOrUpdateSpeaker(ChsSpeaker speakerCache) {
        db.insertOrUpdateSpeaker(speakerCache);
    }

    public List<ChsSpeaker> getAllSpeakers() {
        return db.getAllSpeakers();
    }

    public void deleteAllSpeaker() {
        db.deleteSpeakers();
    }

    public void refreshCachedSpeakers() {
        db.refreshSpeakerStatus();
    }

    DBInteractListener dbInteractListener = new DBInteractListener() {
        @Override
        public void newSpeakerAdded(ChsSpeaker speaker) {
            Log.d(TAG, "newSpeakerAdded: ");
            listener.newSpeakerAdded(speaker);
        }

        @Override
        public void speakerUpdated(ChsSpeaker speaker) {
            Log.d(TAG, "speakerUpdated: ");
            listener.speakerUpdated(speaker);
        }

        @Override
        public void speakerListRefreshed() {
            Log.d(TAG, "speakerListRefreshed: ");
            listener.speakerListRefreshed();
        }
    };
}
