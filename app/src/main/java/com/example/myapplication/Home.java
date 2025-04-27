package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;

public class Home extends AppCompatActivity {

    private ImageView petImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Handle window insets for immersive UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Load pet image
        petImageView = findViewById(R.id.image2);
        loadPetImage();

        // Set up button navigation
        findViewById(R.id.imageButton5).setOnClickListener(this::openForum);
        findViewById(R.id.imageButton3).setOnClickListener(this::openProfile);
        findViewById(R.id.imageButton4).setOnClickListener(this::openService);
        findViewById(R.id.button2).setOnClickListener(this::openAddPet);
        findViewById(R.id.imageButton18).setOnClickListener(this::openNotifi);

        // Amazon and Flipkart buttons
        findViewById(R.id.button7).setOnClickListener(v -> openAmazon());
        findViewById(R.id.button8).setOnClickListener(v -> openFlipkart());
    }

    /**
     * Load image from internal storage and display in image2 (petImageView).
     */
    private void loadPetImage() {
        try {
            SharedPreferences prefs = getSharedPreferences("PetData", MODE_PRIVATE);
            String filename = prefs.getString("PetImageFilename", null);

            if (filename != null) {
                FileInputStream fis = openFileInput(filename);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                petImageView.setImageBitmap(bitmap);
                fis.close();
            } else {
                petImageView.setImageResource(R.drawable.default_pet);
            }
        } catch (Exception e) {
            e.printStackTrace();
            petImageView.setImageResource(R.drawable.default_pet);
        }
    }

    // Retrieve breed from SharedPreferences
    private String getStoredBreed() {
        SharedPreferences prefs = getSharedPreferences("PetData", MODE_PRIVATE);
        return prefs.getString("PetBreed", "Unknown");
    }

    // Open Amazon with breed-based search
    private void openAmazon() {
        String breed = getStoredBreed();
        String query = breed.replace(" ", "+") + "+dog+products";
        String url = "https://www.amazon.in/s?k=" + query;
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    // Open Flipkart with breed-based search
    private void openFlipkart() {
        String breed = getStoredBreed();
        String query = breed.replace(" ", "+") + "+dog+products";
        String url = "https://www.flipkart.com/search?q=" + query;
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    // Navigation methods
    private void openForum(View view) {
        startActivity(new Intent(Home.this, Forum.class));
    }

    private void openProfile(View view) {
        startActivity(new Intent(Home.this, Profile.class));
    }

    private void openService(View view) {
        startActivity(new Intent(Home.this, Service.class));
    }

    private void openAddPet(View view) {
        startActivity(new Intent(Home.this, addpet.class));
    }

    private void openNotifi(View view) {
        startActivity(new Intent(Home.this, Notification.class));
    }
}
