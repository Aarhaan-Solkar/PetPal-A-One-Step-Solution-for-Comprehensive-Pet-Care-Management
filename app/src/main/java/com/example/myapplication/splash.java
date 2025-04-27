package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        // 🧹 Clear all previous app data from SharedPreferences
        clearAllAppData();

        // ⏳ Launch register screen after 2 seconds
        new Handler().postDelayed(() -> {
            startActivity(new Intent(splash.this, register.class));
            finish();
        }, 2000);

        // Edge-to-edge styling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void clearAllAppData() {
        getSharedPreferences("UserPrefs", MODE_PRIVATE).edit().clear().apply();
        getSharedPreferences("PetData", MODE_PRIVATE).edit().clear().apply();
        getSharedPreferences("BreedPrefs", MODE_PRIVATE).edit().clear().apply();
        // 🔁 Add any more preferences you want to reset here
    }
}
