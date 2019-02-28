package com.chsLib.deviceTracker;

/**
 * Created by prabhu on 16/11/17.
 */

public class ChsSpeaker {
    public final static String TAG = ChsSpeaker.class.toString();

    private String id;
    private String name;
    private String ipAddress;
    private String macAddress;
    private String service;// Ex: DLNA, Spotify, SHOUTcast etc...
    private String dBId;
    private String user;
    private String usbStatus;
    private boolean isExDiskConnected;
    private boolean isOnline;

    public ChsSpeaker() {
    }

    public ChsSpeaker(String id, String name, String ipAddress, String macAddress, String service, String dBId, String user, String usbStatus, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.macAddress = id;    // For now MAc add And Speaker Id both are same
        this.service = service;
        this.dBId = dBId;
        this.user = user;
        this.usbStatus = usbStatus;
        if (usbStatus.equalsIgnoreCase("usb ready"))
            this.isExDiskConnected = true;
        else
            this.isExDiskConnected = false;
        this.isOnline = isOnline;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setdBId(String dBId) {
        this.dBId = dBId;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUsbStatus(String usbStatus) {
        this.usbStatus = usbStatus;
    }

    public void setExDiskConnected(boolean exDiskConnected) {
        isExDiskConnected = exDiskConnected;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public String toString() {
        return "ChsSpeaker{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", service='" + service + '\'' +
                ", dBId='" + dBId + '\'' +
                ", user='" + user + '\'' +
                ", usbStatus='" + usbStatus + '\'' +
                ", isExDiskConnected=" + isExDiskConnected +
                ", isOnline=" + isOnline +
                '}';
    }
}