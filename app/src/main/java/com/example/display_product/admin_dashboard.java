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

public class admin_dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        Button button=findViewById(R.id.leaveListButton);
        Button button1=findViewById(R.id.logoutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_dashboard.this, leave_list_admin.class));
            }
        });

        button1.setOnClickListener(v -> {
            // Clear the shared preferences
            getSharedPreferences("MySharedPref", MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(admin_dashboard.this, EmployeeStart.class));
        });
    }
}