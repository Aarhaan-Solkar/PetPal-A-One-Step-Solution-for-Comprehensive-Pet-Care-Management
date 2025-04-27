package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Health extends AppCompatActivity {

    private TextView breedTitle, healthTips, doctorInfo;
    private ImageView doctorImage;
    private Button callDoctorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        // Initialize views
        breedTitle = findViewById(R.id.breedTitle);
        healthTips = findViewById(R.id.healthTips);
        doctorInfo = findViewById(R.id.doctorInfo);
        doctorImage = findViewById(R.id.doctorImage);
        callDoctorBtn = findViewById(R.id.callDoctorBtn);

        // Get breed from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("PetData", MODE_PRIVATE);
        String breed = prefs.getString("PetBreed", "Unknown").toLowerCase();

        updateHealthData(breed);

        // Call Doctor on button click
        callDoctorBtn.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+919876543210"));
            startActivity(callIntent);
        });
    }

    private void updateHealthData(String breed) {
        switch (breed) {
            case "beagle":
                breedTitle.setText("Breed: Beagle");
                healthTips.setText("• Regular ear cleaning\n• Daily walks\n• Keep weight in check\n• Use vet-approved flea meds\n• Annual checkups");
                doctorInfo.setText("Dr. Sneha Rao\nVeterinary Specialist\nContact: +91 9876543210");
                doctorImage.setImageResource(R.drawable.doctor_placeholder);
                break;

            case "dalmatian":
                breedTitle.setText("Breed: Dalmatian");
                healthTips.setText("• Hydration is key\n• Avoid high purine foods\n• Check for bladder stones\n• Regular vet visits\n• Mental stimulation daily");
                doctorInfo.setText("Dr. Karan Mehta\nUrology Specialist\nContact: +91 9922334455");
                doctorImage.setImageResource(R.drawable.doctor_placeholder);
                break;

            case "poodle":
                breedTitle.setText("Breed: Poodle");
                healthTips.setText("• Clean ears weekly\n• Trim fur regularly\n• Dental checkups\n• Heartworm prevention\n• Eye checkups");
                doctorInfo.setText("Dr. Priya Shah\nCanine Dermatologist\nContact: +91 9988776655");
                doctorImage.setImageResource(R.drawable.doctor_placeholder);
                break;

            case "husky":
                breedTitle.setText("Breed: Husky");
                healthTips.setText("• Daily brushing\n• Joint health monitoring\n• Cool water access\n• Routine exercise\n• Cold weather comfort");
                doctorInfo.setText("Dr. Rakesh Iyer\nOrthopedic Specialist\nContact: +91 9876512345");
                doctorImage.setImageResource(R.drawable.doctor_placeholder);
                break;

            case "german shepherd":
                breedTitle.setText("Breed: German Shepherd");
                healthTips.setText("• Hip dysplasia screening\n• Regular training\n• Active lifestyle\n• Grooming weekly\n• Multivitamin support");
                doctorInfo.setText("Dr. Neha Kulkarni\nVeterinary Surgeon\nContact: +91 9845123412");
                doctorImage.setImageResource(R.drawable.doctor_placeholder);
                break;

            default:
                breedTitle.setText("Breed not recognized");
                healthTips.setText("No health tips available.");
                doctorInfo.setText("No doctor data found.");
                doctorImage.setImageResource(R.drawable.doctor_placeholder);
                break;
        }
    }
}
