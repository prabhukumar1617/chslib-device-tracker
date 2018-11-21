package com.chs.devicetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chsLib.deviceTracker.DeviceTracker;
import com.chsLib.deviceTracker.DeviceTrackerListener;
import com.chsLib.deviceTracker.Speaker;

public class MainActivity extends AppCompatActivity implements DeviceTrackerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DeviceTracker(this, this).startDeviceTracking();
    }

    @Override
    public void DeviceFound(Speaker speaker) {

    }

    @Override
    public void onError(String message) {

    }
}
