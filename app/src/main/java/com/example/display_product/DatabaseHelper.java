package com.example.display_product;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "leave_management.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_EMPLOYEES = "employees";
    public static final String TABLE_LEAVE_REQUESTS = "leave_request_entries";

    // Common column names
    public static final String KEY_ID = "id";

    // Employees Table - column names
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    // Add more employee details if needed

    // Leave Requests Table - column names
    public static final String KEY_EMPLOYEE_ID = "employee_id";
    public static final String KEY_LEAVE_REASON = "reason";
    public static final String KEY_LEAVE_STATUS = "status";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    // Add more leave request details if needed

    // Create Tables
    private static final String CREATE_TABLE_EMPLOYEES = "CREATE TABLE "
            + TABLE_EMPLOYEES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USERNAME + " TEXT," + KEY_PASSWORD + " TEXT" + ")";

    private static final String CREATE_TABLE_LEAVE_REQUESTS = "CREATE TABLE "
            + TABLE_LEAVE_REQUESTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_EMPLOYEE_ID + " INTEGER," + KEY_LEAVE_REASON + " TEXT,"
            + KEY_LEAVE_STATUS + " TEXT," + KEY_DATE + " TEXT," + KEY_TIME + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EMPLOYEES);
        db.execSQL(CREATE_TABLE_LEAVE_REQUESTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAVE_REQUESTS);
        onCreate(db);
    }
}
