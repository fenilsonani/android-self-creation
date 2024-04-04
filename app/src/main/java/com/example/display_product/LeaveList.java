package com.example.display_product;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LeaveList extends AppCompatActivity {

    private ListView leaveListView;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_list);

        leaveListView = findViewById(R.id.leaveListView);
        databaseManager = new DatabaseManager(this);

        try {
            displayLeaveRequests();
        } catch (Exception e) {
            Toast.makeText(this, "Error displaying leave requests"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void displayLeaveRequests() {
        // Retrieve employee ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int employeeId = sharedPreferences.getInt("id", 0);

        // Check if employee ID is valid
        if (employeeId != 0) {
            // Retrieve leave requests for the employee from the database
            List<String> leaveRequests = databaseManager.getLeaveRequestsForEmployee(employeeId);

//            check if the cursor is empty
            if (leaveRequests.isEmpty()) {
                Toast.makeText(this, "No leave requests found", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(this, "Leave requests + " + leaveRequests, Toast.LENGTH_SHORT).show();
            }

            // Create a SimpleCursorAdapter to display the leave requests in a ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leaveRequests);
            leaveListView.setAdapter(adapter);
        } else {
            // Employee ID not found in SharedPreferences
            // Display a message or handle the situation accordingly
            Toast.makeText(this, "Employee ID not found", Toast.LENGTH_SHORT).show();
        }
    }


}
