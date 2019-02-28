package com.chsLib.deviceTracker.backend;

import com.chsLib.deviceTracker.model.ChsSpeaker;

import java.util.List;

public interface DBInteractListener {

    public void newSpeakerAdded(ChsSpeaker speaker);

    public void speakerUpdated(ChsSpeaker speaker);

    public void speakerListRefreshed();

}
