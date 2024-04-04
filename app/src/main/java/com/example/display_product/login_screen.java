package com.example.display_product;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_screen extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        databaseManager = new DatabaseManager(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (authenticateUser(username, password)) {
                    if (isAdmin(username)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        sharedPreferences.edit().putBoolean("isAdmin", true).apply();
                        Toast.makeText(login_screen.this,"Redirecting to admin",Toast.LENGTH_SHORT).show();
                        // Redirect to Admin Dashboard
                        startActivity(new Intent(login_screen.this, admin_dashboard.class));
                    } else {
                        Toast.makeText(login_screen.this,"Redirecting to employee",Toast.LENGTH_SHORT).show();
                        // Redirect to Employee Dashboard
                        startActivity(new Intent(login_screen.this, employee_dashboard.class));
                    }
                } else {
                    Toast.makeText(login_screen.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean authenticateUser(String username, String password) {
        // Open database connection
        SQLiteOpenHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {DatabaseHelper.KEY_ID};

        // Define the selection criteria
        String selection = DatabaseHelper.KEY_USERNAME + " = ? AND " + DatabaseHelper.KEY_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        // Query the database to check if the username and password match any record
        Cursor cursor = db.query(DatabaseHelper.TABLE_EMPLOYEES, columns, selection, selectionArgs, null, null, null);

        // Check if the cursor has any rows
        boolean authenticated = (cursor.getCount() > 0);
//        if auth then save the id into the shared preferences
        if (authenticated) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            // Save the employee id to shared preferences
            getSharedPreferences("MySharedPref", MODE_PRIVATE).edit().putInt("id", id).apply();
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return authenticated;
    }

    private boolean isAdmin(String username) {
        // Check if the user is admin based on username
        return username.equals("admin");
    }
}
