package com.chs.devicetracker;

import com.chsLib.deviceTracker.model.ChsSpeaker;

import java.util.List;

public interface MainView {
    public void initViews();

    public void addListeners();

    public void updateSpeakerList(List<ChsSpeaker> speakerList);
}
