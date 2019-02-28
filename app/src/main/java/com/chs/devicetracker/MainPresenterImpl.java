package com.chs.devicetracker;

import android.content.Context;
import android.util.Log;

import com.chsLib.deviceTracker.SpeakerTracker;
import com.chsLib.deviceTracker.SpeakerTrackerListener;
import com.chsLib.deviceTracker.backend.DBInteractListener;
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
    //    private SpeakerTracker speakerTracker;
    private DeviceTracker deviceTracker;
    private DBInteract dbInteract;
    private List<ChsSpeaker> dBSpeakersCache = new ArrayList<ChsSpeaker>();
//    private List<ChsSpeaker> dBSpeakers = new ArrayList<ChsSpeaker>();


    public MainPresenterImpl(Context context) {
        this.context = context;
//        speakerTracker = new SpeakerTracker(context, speakerTrackerListener);
        dbInteract = new DBInteract(context, dbInteractListener);
        deviceTracker = new DeviceTracker(context, deviceTrackerListener);
    }

    @Override
    public void setView(MainView v) {
        Log.d(TAG, "setView: called");
        view = v;
        view.initViews();
        view.addListeners();
        Log.d(TAG, "setView: Listeners added");
//        Log.d(TAG, "setView: speakerTracker " + speakerTracker);
//        speakerTracker.refreshCachedSpeakers();
        //  dBSpeakers = dbInteracter.getAllSpeakers();
//        Log.d(TAG, "setView ---------: " + dBSpeakers.size());
    }

    @Override
    public void startSpeakerDiscovery() {
        deviceTracker.startDeviceTracking();
    }

    @Override
    public void stopSpeakerDiscovery() {
        deviceTracker.startDeviceTracking();

    }

    @Override
    public void clearSpeakerList() {
        dbInteract.deleteAllSpeaker();
    }

    /*SpeakerTrackerListener speakerTrackerListener = new SpeakerTrackerListener() {
        @Override
        public void speakerListCache(List<ChsSpeaker> speakerList) {
            Log.d(TAG, "speakerListCache: " + speakerList.toString());
            view.updateSpeakerList(speakerList);
        }

        @Override
        public void newSpeakerAdded(List<ChsSpeaker> speakerList, ChsSpeaker newSpeaker) {
            Log.d(TAG, "newSpeakerAdded: " + newSpeaker.toString());
            view.updateSpeakerList(speakerList);
        }

        @Override
        public void speakerUpdated(List<ChsSpeaker> speakerList, ChsSpeaker updatedSpeaker) {
            Log.d(TAG, "speakerUpdated: " + updatedSpeaker.toString());
        }

        @Override
        public void speakerListRefreshed() {
            Log.d(TAG, "speakerListRefreshed: ");
            speakerTracker.getAllCachedSpeakerList();
        }
    };*/

    DeviceTrackerListener deviceTrackerListener = new DeviceTrackerListener() {
        @Override
        public void deviceFound(ChsSpeaker speaker) {

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
            Log.d(TAG, "speakerUpdated: ");
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
}
