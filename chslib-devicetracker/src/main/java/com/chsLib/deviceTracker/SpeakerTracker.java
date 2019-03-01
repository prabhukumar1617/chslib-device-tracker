package com.chsLib.deviceTracker;

import android.content.Context;
import android.util.Log;

import com.chsLib.deviceTracker.backend.DBInteract;
import com.chsLib.deviceTracker.backend.DBInteractListener;
import com.chsLib.deviceTracker.model.ChsSpeaker;

import java.util.ArrayList;
import java.util.List;

public class SpeakerTracker {
    private final String TAG = SpeakerTracker.class.toString();
    private Context context;
    private DeviceTracker deviceTracker;
    private DBInteract dbInteract;
    private List<ChsSpeaker> dBSpeakersCache = new ArrayList<ChsSpeaker>();
    private SpeakerTrackerListener listener;


    public SpeakerTracker(Context context, SpeakerTrackerListener listener) {
        this.context = context;
        this.listener = listener;
        dbInteract = new DBInteract(context, dbInteractListener);
        deviceTracker = new DeviceTracker(context, deviceTrackerListener);
    }

    public void startSpeakerDiscovery() {
        deviceTracker.startDeviceTracking();
    }

    public void stopSpeakerDiscovery() {
        deviceTracker.stopDeviceTracking();
    }

    public void getAllCachedSpeakerList() {
        Log.d(TAG, "getAllCachedSpeakerList: ");
        dBSpeakersCache = dbInteract.getAllSpeakers();
        listener.speakerListCache(dBSpeakersCache);
    }

    public void refreshCachedSpeakers() {
        Log.e(TAG, "refreshCachedSpeakers: ");
        dbInteract.refreshCachedSpeakers();
    }

    public void deleteCachedSpeakers() {
        Log.e(TAG, "refreshCachedSpeakers: ");
        dbInteract.deleteAllSpeaker();
    }


    DeviceTrackerListener deviceTrackerListener = new DeviceTrackerListener() {
        @Override
        public void deviceFound(ChsSpeaker speaker) {
            dbInteract.insertOrUpdateSpeaker(speaker);
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
            listener.newSpeakerAdded(dBSpeakersCache, speaker);
        }

        @Override
        public void speakerUpdated(ChsSpeaker speaker) {
            Log.d(TAG, "speakerUpdated: ");
            for (int i = 0; i <= dBSpeakersCache.size(); i++) {
                if (dBSpeakersCache.get(i).getId().equalsIgnoreCase(speaker.getId())) {
                    dBSpeakersCache.get(i).setOnline(true);
                    break;
                }
            }
            listener.newSpeakerAdded(dBSpeakersCache, speaker);
        }

        @Override
        public void speakerListRefreshed() {
            listener.speakerListRefreshed();
        }
    };

}
