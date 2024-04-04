package com.example.display_product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class employee_dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_dashboard);
        Button button=findViewById(R.id.applyLeaveButton);
        Button button1=findViewById(R.id.viewLeaveButton);
        Button button2=findViewById(R.id.logoutLeaveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(employee_dashboard.this, leave_application.class));
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(employee_dashboard.this, LeaveList.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Clear the shared preferences
                getSharedPreferences("MySharedPref", MODE_PRIVATE).edit().clear().apply();
                startActivity(new Intent(employee_dashboard.this, EmployeeStart.class));
            }
        });
    }
}