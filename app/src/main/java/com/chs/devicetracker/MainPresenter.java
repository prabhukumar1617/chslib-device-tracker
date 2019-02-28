package com.chs.devicetracker;

public interface MainPresenter {
    public void setView(MainView v);

    public void startSpeakerDiscovery();

    public void stopSpeakerDiscovery();

    public void clearSpeakerList();
}
