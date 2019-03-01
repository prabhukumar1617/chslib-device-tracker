package com.chsLib.deviceTracker;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.chsLib.deviceTracker.backend.DBInteract;
import com.chsLib.deviceTracker.backend.DatabaseHelper;
import com.chsLib.deviceTracker.jmdns.JmDNS;
import com.chsLib.deviceTracker.jmdns.ServiceEvent;
import com.chsLib.deviceTracker.jmdns.ServiceInfo;
import com.chsLib.deviceTracker.jmdns.ServiceListener;
import com.chsLib.deviceTracker.model.ChsSpeaker;
import com.chsLib.deviceTracker.util.ThreadExecutor;
import com.chsLib.deviceTracker.util.Utils;

import java.io.IOException;
import java.net.InetAddress;


public class DeviceTracker implements ServiceListener {
    public final static String TAG = DeviceTracker.class.toString();
    private Context mContext;
    public final static String TOUCH_ABLE_TYPE = "_touch-able._tcp.local.";
    public final static String HOSTNAME = "chs_aster";
    private static JmDNS jmdns = null;
    private static WifiManager.MulticastLock mLock = null;
    private DeviceTrackerListener listener;


    public DeviceTracker(Context context, DeviceTrackerListener listener) {
        this.mContext = context;
        this.listener = listener;
        Log.e(TAG, "DeviceTracker object created: ");
    }

    private void startProbe() throws Exception {
        Log.e(TAG, "startProbe: ");
        if (jmdns != null)
            stopDeviceTracking();
        WifiManager wifi = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mLock = wifi.createMulticastLock("CHS_ASTER");
        mLock.setReferenceCounted(true);
        mLock.acquire();

        jmdns = JmDNS.create(InetAddress.getByName(Utils.getFormattedIpAddress(mContext)), HOSTNAME);
        jmdns.addServiceListener(TOUCH_ABLE_TYPE, DeviceTracker.this);
    }

    private void stopProbe() {
        Log.e(TAG, "stopProbe: ");
        if (jmdns != null) {
            jmdns.removeServiceListener(TOUCH_ABLE_TYPE, this);
            try {
                jmdns.close();
                jmdns = null;
            } catch (IOException e) {
                e.printStackTrace();
                if (listener != null)
                    listener.onError(e.getMessage());
            }
        }

        if (mLock != null) {
            mLock.release();
            mLock = null;
        }
    }

    public void startDeviceTracking() {
        ThreadExecutor.runTask(new Runnable() {
            public void run() {
                try {
                    startProbe();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void stopDeviceTracking() {
        ThreadExecutor.runTask(new Runnable() {
            public void run() {
                try {
                    stopProbe();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void serviceAdded(ServiceEvent event) {
        Log.e(TAG, "serviceAdded: " + event.toString());
    }

    @Override
    public void serviceRemoved(ServiceEvent event) {
        Log.e(TAG, "serviceRemoved: " + event.toString());
        String name, id, services, usbStatus, ipAddress, user, dbID, macAddress;
        ServiceInfo serviceInfo = event.getInfo();
        name = serviceInfo.getPropertyString("CtlN");
        id = event.getName();
        macAddress = serviceInfo.getPropertyString("Service");
        services = serviceInfo.getPropertyString("Service");
        usbStatus = serviceInfo.getPropertyString("USB");
        ipAddress = serviceInfo.getHostAddresses()[0];
        user = serviceInfo.getPropertyString("User");
        dbID = serviceInfo.getPropertyString("DbId");
        ChsSpeaker speaker = new ChsSpeaker(id, name, ipAddress, macAddress, services, dbID, user, usbStatus, false);
        if (listener != null) {
            listener.deviceDisconnected(speaker);
        }
    }

    @Override
    public void serviceResolved(ServiceEvent event) {
        Log.e(TAG, "serviceResolved: " + event.toString());
        String name, id, services, usbStatus, ipAddress, user, dbID, macAddress;
        ServiceInfo serviceInfo = event.getInfo();

        name = serviceInfo.getPropertyString("CtlN");
        macAddress = serviceInfo.getPropertyString("Mac_Add");
//        id = event.getName();
        id = macAddress;
        services = serviceInfo.getPropertyString("Service");
        usbStatus = serviceInfo.getPropertyString("USB");
        ipAddress = serviceInfo.getHostAddresses()[0];
        user = serviceInfo.getPropertyString("User");
        dbID = serviceInfo.getPropertyString("DbId");
        ChsSpeaker speaker = new ChsSpeaker(id, name, ipAddress, macAddress, services, dbID, user, usbStatus, true);
        speakerFound(speaker);
    }

    private void speakerFound(ChsSpeaker speaker) {
        if (listener != null) {
            listener.deviceFound(speaker);
        }
    }

}