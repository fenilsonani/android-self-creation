package com.example.display_product;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class leave_list_admin extends AppCompatActivity {

    private ListView leaveListView;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_list_admin);

        leaveListView = findViewById(R.id.leaveListView);
        databaseManager = new DatabaseManager(this);

        displayLeaveRequests();
    }

    private void displayLeaveRequests() {
        try{
        Cursor cursor = databaseManager.getAllLeaveRequests();
        String[] fromColumns = {DatabaseHelper.KEY_ID, DatabaseHelper.KEY_EMPLOYEE_ID, DatabaseHelper.KEY_LEAVE_REASON, DatabaseHelper.KEY_LEAVE_STATUS, DatabaseHelper.KEY_DATE, DatabaseHelper.KEY_TIME};
        int[] toViews = {R.id.leaveIdTextView, R.id.employeeIdTextView, R.id.leaveReasonTextView, R.id.leaveStatusTextView, R.id.leaveDateTextView, R.id.leaveTimeTextView};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.leave_list_item_admin, cursor, fromColumns, toViews, 0);

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.leaveStatusTextView) {
                    String leaveStatus = cursor.getString(columnIndex);
                    ((TextView) view).setText(leaveStatus);

                    // Change text color based on leave status
                    int color = leaveStatus.equals("Pending") ? Color.RED : Color.BLACK;
                    ((TextView) view).setTextColor(color);

                    return true;
                }
                return false;
            }
        });

        leaveListView.setAdapter(adapter);

        leaveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor selectedCursor = (Cursor) parent.getItemAtPosition(position);
                int leaveId = selectedCursor.getInt(selectedCursor.getColumnIndex(DatabaseHelper.KEY_ID));
                String leaveStatus = selectedCursor.getString(selectedCursor.getColumnIndex(DatabaseHelper.KEY_LEAVE_STATUS));

                if (leaveStatus.equals("Pending")) {
                    // Update the status of the selected leave request to "Approved" or "Rejected"
                    boolean isApproved = view.getId() == R.id.approveButton;
                    String newStatus = isApproved ? "Approved" : "Rejected";
                    if (databaseManager.updateLeaveRequestStatus(leaveId, newStatus)) {
                        String message = isApproved ? "Leave request approved" : "Leave request rejected";
                        Toast.makeText(leave_list_admin.this, message, Toast.LENGTH_SHORT).show();
                        // Refresh the leave requests list
                        displayLeaveRequests();
                    } else {
                        Toast.makeText(leave_list_admin.this, "Failed to update leave request status", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(leave_list_admin.this, "Leave request is already processed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(leave_list_admin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
