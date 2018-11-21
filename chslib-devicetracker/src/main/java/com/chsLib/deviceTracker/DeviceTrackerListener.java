package com.chsLib.deviceTracker;

public interface DeviceTrackerListener {
    public void DeviceFound(Speaker speaker);

    public void onError(String message);
}
