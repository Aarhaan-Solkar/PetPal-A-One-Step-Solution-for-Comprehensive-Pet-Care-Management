package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Diet extends AppCompatActivity {

    private TextView dogNameBreed, morningDietText, afternoonDietText, nightDietText;
    private CardView morningDietCard, afternoonDietCard, nightDietCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        // Initialize views
        dogNameBreed = findViewById(R.id.dogNameBreed);
        morningDietText = findViewById(R.id.morningDietText);
        afternoonDietText = findViewById(R.id.afternoonDietText);
        nightDietText = findViewById(R.id.nightDietText);
        morningDietCard = findViewById(R.id.morningDietCard);
        afternoonDietCard = findViewById(R.id.afternoonDietCard);
        nightDietCard = findViewById(R.id.nightDietCard);

        // Access saved dog breed from internal storage
        SharedPreferences petPrefs = getSharedPreferences("PetData", MODE_PRIVATE);
        String breed = petPrefs.getString("PetBreed", "").toLowerCase().trim();

        // Show diet if breed is supported
        switch (breed) {
            case "beagle":
                setDogDiet("Beagle", "1 cup dry food", "1 cup wet food + veggies", "Dry food + chicken");
                break;
            case "dalmatian":
                setDogDiet("Dalmatian", "1/2 cup dry food", "1 cup wet food + fish", "Dry food + egg");
                break;
            case "german shepherd":
                setDogDiet("German Shepherd", "1.5 cups dry food", "2 cups wet food + rice", "Dry food + meat");
                break;
            case "husky":
                setDogDiet("Husky", "1 cup dry food + boiled eggs", "Wet food with rice", "Dry food + fish oil");
                break;
            case "poodle":
                setDogDiet("Poodle", "1/2 cup dry food", "Steamed veggies + meat", "Dry food + yogurt");
                break;
            default:
                resetDietViews();
                Toast.makeText(this, "No diet info available for your dog breed.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void setDogDiet(String breed, String morningDiet, String afternoonDiet, String nightDiet) {
        dogNameBreed.setText("Dog's Breed: " + breed);
        morningDietText.setText("Morning Diet: " + morningDiet);
        afternoonDietText.setText("Afternoon Diet: " + afternoonDiet);
        nightDietText.setText("Night Diet: " + nightDiet);

        dogNameBreed.setVisibility(View.VISIBLE);
        morningDietCard.setVisibility(View.VISIBLE);
        afternoonDietCard.setVisibility(View.VISIBLE);
        nightDietCard.setVisibility(View.VISIBLE);
    }

    private void resetDietViews() {
        dogNameBreed.setVisibility(View.GONE);
        morningDietCard.setVisibility(View.GONE);
        afternoonDietCard.setVisibility(View.GONE);
        nightDietCard.setVisibility(View.GONE);
    }
}
