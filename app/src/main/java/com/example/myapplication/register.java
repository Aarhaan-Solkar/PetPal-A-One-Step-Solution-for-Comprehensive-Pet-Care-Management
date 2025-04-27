package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextUsername, editTextPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI elements
        editTextName = findViewById(R.id.signup_name);
        editTextEmail = findViewById(R.id.signup_email);
        editTextUsername = findViewById(R.id.signup_username);
        editTextPassword = findViewById(R.id.signup_password);
        buttonRegister = findViewById(R.id.btnRegister);

        // Set up the register button click listener
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData();
            }
        });
    }

    private void saveUserData() {
        // Get user input
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            return;
        }
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Username is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters long");
            return;
        }

        // Save data to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Name", name);
        editor.putString("Email", email);
        editor.putString("Username", username);
        editor.putString("Password", password);

        // Commit the changes
        editor.apply();

        // Notify the user
        Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();

        // Clear the input fields after successful registration
        editTextName.setText("");
        editTextEmail.setText("");
        editTextUsername.setText("");
        editTextPassword.setText("");

        // Navigate to the login page
        Intent intent = new Intent(register.this, login.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }
}