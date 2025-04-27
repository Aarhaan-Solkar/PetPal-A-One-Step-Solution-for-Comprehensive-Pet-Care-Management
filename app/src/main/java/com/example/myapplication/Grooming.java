package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Grooming extends AppCompatActivity {

    private TextView groomingTitle, groomingSteps;
    private ImageView groomingImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grooming);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        groomingTitle = findViewById(R.id.groomingTitle);
        groomingSteps = findViewById(R.id.groomingSteps);
        groomingImage = findViewById(R.id.groomingImage);

        SharedPreferences prefs = getSharedPreferences("PetData", MODE_PRIVATE);
        String breed = prefs.getString("PetBreed", "").toLowerCase();

        switch (breed) {
            case "beagle":
                groomingTitle.setText("Beagle Grooming Guide");
                groomingImage.setImageResource(R.drawable.beagle_grooming);
                groomingSteps.setText("1. Brush twice a week.\n2. Clean ears weekly.\n3. Bathe monthly.\n4. Trim nails every 2 weeks.");
                break;

            case "dalmatian":
                groomingTitle.setText("Dalmatian Grooming Guide");
                groomingImage.setImageResource(R.drawable.dalmatian_grooming);
                groomingSteps.setText("1. Daily brushing during shedding.\n2. Bathe only when necessary.\n3. Check ears for infections.\n4. Brush teeth weekly.");
                break;

            case "poodle":
                groomingTitle.setText("Poodle Grooming Guide");
                groomingImage.setImageResource(R.drawable.poodle_grooming);
                groomingSteps.setText("1. Professional grooming every 4-6 weeks.\n2. Daily brushing to avoid matting.\n3. Clean ears and eyes regularly.\n4. Clip nails monthly.");
                break;

            case "husky":
                groomingTitle.setText("Husky Grooming Guide");
                groomingImage.setImageResource(R.drawable.husky_grooming);
                groomingSteps.setText("1. Weekly brushing (more during shedding).\n2. Rare baths (they are clean dogs).\n3. Regular nail trimming.\n4. Keep ears dry and clean.");
                break;

            case "german shepherd":
                groomingTitle.setText("German Shepherd Grooming Guide");
                groomingImage.setImageResource(R.drawable.german_shepherd_grooming);
                groomingSteps.setText("1. Brush 2-3 times a week.\n2. Bath every 3-4 months.\n3. Check for ticks.\n4. Trim nails monthly.");
                break;

            default:
                groomingTitle.setText("Breed Not Recognized");
                groomingSteps.setText("Please update your profile with a valid breed.");
                groomingImage.setImageResource(R.drawable.default_dog);  // Fallback image
                break;
        }
    }
}
