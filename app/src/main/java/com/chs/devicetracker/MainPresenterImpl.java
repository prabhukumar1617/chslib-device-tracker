package com.chs.devicetracker;

import android.content.Context;
import android.util.Log;

import com.chsLib.deviceTracker.SpeakerTracker;
import com.chsLib.deviceTracker.SpeakerTrackerListener;
import com.chsLib.deviceTracker.model.ChsSpeaker;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {
    private final String TAG = MainPresenterImpl.class.toString();
    private MainView view;
    private Context context;
    private SpeakerTracker speakerTracker;
    private List<ChsSpeaker> dBSpeakers = new ArrayList<ChsSpeaker>();


    public MainPresenterImpl(Context context) {
        this.context = context;
        speakerTracker = new SpeakerTracker(context, speakerTrackerListener);
    }

    @Override
    public void setView(MainView v) {
        Log.d(TAG, "setView: called");
        view = v;
        view.initViews();
        view.addListeners();
        Log.d(TAG, "setView: Listeners added");
        Log.d(TAG, "setView: speakerTracker " + speakerTracker);
        speakerTracker.refreshCachedSpeakers();
        Log.d(TAG, "setView ---------: " + dBSpeakers.size());
    }

    @Override
    public void startSpeakerDiscovery() {
        speakerTracker.startSpeakerDiscovery();
    }

    @Override
    public void stopSpeakerDiscovery() {
        speakerTracker.stopSpeakerDiscovery();

    }

    @Override
    public void clearSpeakerList() {
        speakerTracker.deleteCachedSpeakers();
    }

    SpeakerTrackerListener speakerTrackerListener = new SpeakerTrackerListener() {
        @Override
        public void speakerListCache(List<ChsSpeaker> speakerList) {
            Log.d(TAG, "speakerListCache: " + speakerList.toString());
            view.updateSpeakerList(speakerList);
            startSpeakerDiscovery();
        }

        @Override
        public void newSpeakerAdded(List<ChsSpeaker> speakerList, ChsSpeaker newSpeaker) {
            Log.d(TAG, "newSpeakerAdded: " + newSpeaker.toString());
            view.updateSpeakerList(speakerList);
        }

        @Override
        public void speakerUpdated(List<ChsSpeaker> speakerList, ChsSpeaker updatedSpeaker) {
            Log.d(TAG, "speakerUpdated: " + updatedSpeaker.toString());
            view.updateSpeakerList(speakerList);

        }

        @Override
        public void speakerListRefreshed() {
            Log.d(TAG, "speakerListRefreshed: ");
            speakerTracker.getAllCachedSpeakerList();
        }
    };

}
