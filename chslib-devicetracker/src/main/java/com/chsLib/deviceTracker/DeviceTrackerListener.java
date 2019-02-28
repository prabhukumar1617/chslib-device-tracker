package com.chsLib.deviceTracker;

import com.chsLib.deviceTracker.model.ChsSpeaker;

public interface DeviceTrackerListener {
    public void deviceFound(ChsSpeaker speaker);

    public void deviceDisconnected(ChsSpeaker speaker);

    public void onSpeakerUpdates();

    public void onError(String message);
}
