package com.chsLib.deviceTracker.backend;

public class Constants {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IP_ADDRESS = "ipAddress";
    public static final String COLUMN_STATUS = "status";
    public static final String TABLE_NAME = "Speakers";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " TEXT PRIMARY KEY,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_IP_ADDRESS + " TEXT,"
                    + COLUMN_STATUS + " INTEGER"
                    + ")";
}
