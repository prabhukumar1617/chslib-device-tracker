package com.chsLib.deviceTracker;

/**
 * Created by prabhu on 16/11/17.
 */

public class Speaker {
    public final static String TAG = Speaker.class.toString();

    private String id;
    private String name;
    private String ipAddress;
    private String macAddress;
    private String service;// Ex: DLNA, Spotify, SHOUTcast etc...
    private String dBId;
    private String user;
    private String usbStatus;
    private boolean isExDiskConnected;

    public Speaker(String id, String name, String ipAddress, String macAddress, String service, String dBId, String user, String usbStatus) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.service = service;
        this.dBId = dBId;
        this.user = user;
        this.usbStatus = usbStatus;
        this.isExDiskConnected = isExDiskConnected;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getService() {
        return service;
    }

    public String getdBId() {
        return dBId;
    }

    public String getUser() {
        return user;
    }

    public String getUsbStatus() {
        return usbStatus;
    }

    public boolean isExDiskConnected() {
        return isExDiskConnected;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setExDiskConnected(boolean exDiskConnected) {
        isExDiskConnected = exDiskConnected;
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", service='" + service + '\'' +
                ", dBId='" + dBId + '\'' +
                ", user='" + user + '\'' +
                ", isExDiskConnected=" + isExDiskConnected +
                '}';
    }
}