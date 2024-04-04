package com.example.display_product;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registration extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button registerButton;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        databaseManager = new DatabaseManager(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the username already exists
                    if (databaseManager.isUsernameExists(username)) {
                        Toast.makeText(registration.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        // Add new employee to the database
                        long result = databaseManager.addEmployee(username, password);
                        if (result != -1) {
                            Toast.makeText(registration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            finish(); // Close registration activity after successful registration
                        } else {
                            Toast.makeText(registration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
