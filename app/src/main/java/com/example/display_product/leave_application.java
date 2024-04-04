package com.example.display_product;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class leave_application extends Activity {

    private EditText leaveReasonEditText;

    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button applyLeaveButton;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);

//        check that the id is present in the shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);
        if (id == 0) {
            Toast.makeText(this, "Employee ID not found", Toast.LENGTH_SHORT).show();
//            finish(); // Close activity if employee id is not found
        }

        leaveReasonEditText = findViewById(R.id.leaveReasonEditText);
        applyLeaveButton = findViewById(R.id.applyLeaveButton);
        datePicker=findViewById(R.id.datePicker);
        timePicker=findViewById(R.id.timePicker);
        databaseManager = new DatabaseManager(this);

        applyLeaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = leaveReasonEditText.getText().toString();
                String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                String time = timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();


                if (reason.isEmpty()) {
                    Toast.makeText(leave_application.this, "Please enter reason for leave", Toast.LENGTH_SHORT).show();
                } else {
                    // Get employee id from session or wherever it's stored
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    int employeeId = sharedPreferences.getInt("id", 0);

                    // Add leave request to database
                    long result = databaseManager.addLeaveRequest(employeeId, reason, "Pending",date,time);
                    if (result != -1) {
                        Toast.makeText(leave_application.this, "Leave applied successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity after applying leave
                    } else {
                        Toast.makeText(leave_application.this, "Failed to apply leave", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
