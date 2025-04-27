package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Step 1: Get references to TextViews
        TextView textViewUserName = findViewById(R.id.textViewUserName);
        TextView textViewUserEmail = findViewById(R.id.textViewUserEmail);
        TextView textViewPetName = findViewById(R.id.textViewPetName);
        TextView textViewPetBreed = findViewById(R.id.textViewPetBreed);
        TextView textViewPetAge = findViewById(R.id.textViewPetAge);

        // Step 2: Load saved data from SharedPreferences
        SharedPreferences userPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences petPrefs = getSharedPreferences("PetData", MODE_PRIVATE);

        String userName = userPrefs.getString("Name", "Unknown User");
        String userEmail = userPrefs.getString("Email", "no_email@domain.com");

        String petName = petPrefs.getString("PetName", "Unknown Pet");
        String petBreed = petPrefs.getString("PetBreed", "Unknown Breed");

        // ✅ Fix: Retrieve pet age as integer, then convert to string
        int age = petPrefs.getInt("PetAge", -1);
        String petAge = (age == -1) ? "Not Entered" : String.valueOf(age);

        // Step 3: Display the data
        textViewUserName.setText(userName);
        textViewUserEmail.setText(userEmail);
        textViewPetName.setText(petName);
        textViewPetBreed.setText(petBreed);
        textViewPetAge.setText(petAge);

        // Navigation Buttons
        ImageButton button11 = findViewById(R.id.imageButton11);
        ImageButton button12 = findViewById(R.id.imageButton12);
        ImageButton button13 = findViewById(R.id.imageButton13);
        Button button3 = findViewById(R.id.settingsBtn);
        Button button4 = findViewById(R.id.logoutBtn);

        button3.setOnClickListener(v -> startActivity(new Intent(Profile.this, Privacy.class)));
        button4.setOnClickListener(v -> startActivity(new Intent(Profile.this, login.class)));
        button12.setOnClickListener(v -> startActivity(new Intent(Profile.this, Home.class)));
        button13.setOnClickListener(v -> startActivity(new Intent(Profile.this, Service.class)));
    }
}
