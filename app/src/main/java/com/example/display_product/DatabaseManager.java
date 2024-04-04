package com.example.display_product;

import static com.example.display_product.DatabaseHelper.KEY_DATE;
import static com.example.display_product.DatabaseHelper.KEY_EMPLOYEE_ID;
import static com.example.display_product.DatabaseHelper.KEY_ID;
import static com.example.display_product.DatabaseHelper.KEY_LEAVE_REASON;
import static com.example.display_product.DatabaseHelper.KEY_LEAVE_STATUS;
import static com.example.display_product.DatabaseHelper.KEY_TIME;
import static com.example.display_product.DatabaseHelper.TABLE_LEAVE_REQUESTS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Add methods for CRUD operations on employees and leave requests tables


    public boolean isUsernameExists(String username) {
        // Open database connection
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {KEY_ID};

        // Define the selection criteria
        String selection = DatabaseHelper.KEY_USERNAME + " = ?";
        String[] selectionArgs = {username};

        // Query the database to check if the username exists
        Cursor cursor = db.query(DatabaseHelper.TABLE_EMPLOYEES, columns, selection, selectionArgs, null, null, null);

        // Check if the cursor has any rows
        boolean exists = (cursor.getCount() > 0);

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return exists;
    }


    public long addEmployee(String username, String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_USERNAME, username);
        values.put(DatabaseHelper.KEY_PASSWORD, password);

        // Open database connection
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the new employee record
        long result = db.insert(DatabaseHelper.TABLE_EMPLOYEES, null, values);

        // Close database connection
        db.close();

        return result;
    }

    public List<String> getLeaveRequestsForEmployee(int employeeId) {
        // Open database connection
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {KEY_LEAVE_REASON, KEY_LEAVE_STATUS};

        // Define the selection criteria
        String selection = KEY_EMPLOYEE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(employeeId)};

        // Query the database to get leave requests for the employee
        Cursor cursor = db.query(TABLE_LEAVE_REQUESTS, columns, selection, selectionArgs, null, null, null);

        // Process the cursor to get the leave requests
        List<String> leaveRequests = new ArrayList<>();
        while (cursor.moveToNext()) {
            String reason = cursor.getString(0);
            String status = cursor.getString(1);
            leaveRequests.add("Reason: " + reason + ", Status: " + status);
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return leaveRequests;
    }

    public boolean rejectLeaveRequest(int leaveId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LEAVE_STATUS, "Rejected");
        String selection = KEY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(leaveId)};
        int rowsAffected = db.update(TABLE_LEAVE_REQUESTS, values, selection, selectionArgs);
        return rowsAffected > 0;
    }
    public boolean approveLeaveRequest(int leaveId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LEAVE_STATUS, "Approved");
        String selection = KEY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(leaveId)};
        int rowsAffected = db.update(TABLE_LEAVE_REQUESTS, values, selection, selectionArgs);
        return rowsAffected > 0;
    }


    public Cursor getAllLeaveRequests() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " + KEY_ID + " AS _id, " + KEY_EMPLOYEE_ID + ", " + KEY_LEAVE_REASON + ", " + KEY_LEAVE_STATUS + ", " + KEY_DATE + ", " + KEY_TIME + " FROM " + TABLE_LEAVE_REQUESTS;
        return db.rawQuery(query, null);
    }

    public boolean updateLeaveRequestStatus(int leaveId, String newStatus) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LEAVE_STATUS, newStatus);
        String selection = KEY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(leaveId)};
        int rowsAffected = db.update(TABLE_LEAVE_REQUESTS, values, selection, selectionArgs);
        return rowsAffected > 0;
    }


    public long addLeaveRequest(int employeeId, String reason, String status,String date,String time) {
        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYEE_ID, employeeId);
        values.put(KEY_LEAVE_REASON, reason);
        values.put(KEY_LEAVE_STATUS, status);

        // Open database connection
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the new leave request record
        long result = db.insert(TABLE_LEAVE_REQUESTS, null, values);

        // Close database connection
        db.close();
        return result;
    }

}