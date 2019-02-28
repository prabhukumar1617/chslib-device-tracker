package com.chs.devicetracker;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chsLib.deviceTracker.SpeakerTracker;
import com.chsLib.deviceTracker.SpeakerTrackerListener;
import com.chsLib.deviceTracker.backend.DBInteractListener;
import com.chsLib.deviceTracker.backend.DatabaseHelper;
import com.chsLib.deviceTracker.model.ChsSpeaker;
import com.chsLib.deviceTracker.DeviceTracker;
import com.chsLib.deviceTracker.DeviceTrackerListener;
import com.chsLib.deviceTracker.backend.DBInteract;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {
    private final String TAG = MainPresenterImpl.class.toString();
    private MainView view;
    private Context context;
    private List<ChsSpeaker> dBSpeakersCache = new ArrayList<ChsSpeaker>();
    DeviceTracker deviceTracker;
    DatabaseHelper db;

    public MainPresenterImpl(Context context) {
        this.context = context;
        deviceTracker = new DeviceTracker(context, deviceTrackerListener);
        db = DatabaseHelper.createOrOpenDB(context);
        db.setListener(playerStatusUpdate);
        db.setListener(dbInteractListener);
    }

    @Override
    public void setView(MainView v) {
        Log.d(TAG, "setView: called");
        view = v;
        view.initViews();
        view.addListeners();
        Log.d(TAG, "setView: Listeners added");

    }

    @Override
    public void startSpeakerDiscovery() {
        deviceTracker.startDeviceTracking();
    }

    @Override
    public void stopSpeakerDiscovery() {
        deviceTracker.stopDeviceTracking();

    }

    @Override
    public void clearSpeakerList() {
        db.deleteSpeakers();
    }


    DeviceTrackerListener deviceTrackerListener = new DeviceTrackerListener() {
        @Override
        public void deviceFound(ChsSpeaker speaker) {
            db.insertOrUpdateSpeaker(speaker);
        }

        @Override
        public void deviceDisconnected(ChsSpeaker speaker) {

        }

        @Override
        public void onSpeakerUpdates() {

        }

        @Override
        public void onError(String message) {
            Log.e(TAG, "onError: " + message);
        }
    };

    DBInteractListener dbInteractListener = new DBInteractListener() {
        @Override
        public void newSpeakerAdded(ChsSpeaker speaker) {
            dBSpeakersCache.add(speaker);
            Log.d(TAG, "speakerListCache: " + speaker.toString());
            view.updateSpeakerList(dBSpeakersCache);
//            listener.newSpeakerAdded(dBSpeakersCache, speaker);
        }

        @Override
        public void speakerUpdated(ChsSpeaker speaker) {
            Log.d(TAG, "speakerUpdated: " + speaker.toString());
            for (int i = 0; i <= dBSpeakersCache.size(); i++) {
                if (dBSpeakersCache.get(i).getId().equalsIgnoreCase(speaker.getId())) {
                    dBSpeakersCache.get(i).setOnline(true);
                    break;
                }
            }
            view.updateSpeakerList(dBSpeakersCache);
//            listener.newSpeakerAdded(dBSpeakersCache, speaker);
        }

        @Override
        public void speakerListRefreshed() {
            Log.d(TAG, "speakerListRefreshed: ");

//            listener.speakerListRefreshed();
        }
    };

    private Handler playerStatusUpdate = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d(TAG, "handleMessage: 1");
                    break;
                case 2:
                    Log.d(TAG, "handleMessage: 2 : Speaker updated");
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    };

}
