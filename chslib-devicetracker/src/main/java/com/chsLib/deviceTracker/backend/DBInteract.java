package com.chsLib.deviceTracker.backendLib;

import android.content.Context;

import com.chsLib.deviceTracker.ChsSpeaker;

import java.util.List;

public class DBInteract implements DBInteractListener {

    private Context context;
    private DatabaseHelper db;

    public DBInteract(Context context) {
        this.context = context;
        db = DatabaseHelper.createOrOpenDB(context);
    }

    public boolean insertSpeaker(ChsSpeaker speaker) {
        db.insertSpeaker(speaker);
        return true;
    }

    public boolean updateSpeakerInfo(ChsSpeaker speaker) {
//        db.insertSpeaker(speaker);
        return true;
    }

    public boolean deleteAllSpeaker() {
        db.deleteSpeakers();
        return true;
    }

    public List<ChsSpeaker> getAllSpeakers() {
        return db.getAllSpeakers();
    }


}
