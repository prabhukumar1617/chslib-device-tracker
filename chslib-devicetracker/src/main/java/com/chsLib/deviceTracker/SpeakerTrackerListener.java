package com.chsLib.deviceTracker;

import com.chsLib.deviceTracker.model.ChsSpeaker;

import java.util.List;

public interface SpeakerTrackerListener {

    public void speakerListCache(List<ChsSpeaker> speakerList);

    public void newSpeakerAdded(List<ChsSpeaker> speakerList, ChsSpeaker newSpeaker);

    public void speakerUpdated(List<ChsSpeaker> speakerList, ChsSpeaker updatedSpeaker);

    public void speakerListRefreshed();

    public void speakerSearchingStarted();

    public void speakerSearchingStopped();

    public void speakerSearchingFailed(String message);
}
