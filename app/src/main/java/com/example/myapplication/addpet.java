package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;

public class addpet extends AppCompatActivity {

    private EditText editTextName, editTextAge;
    private ImageView imageViewPet;
    private Button buttonSubmit;
    private Bitmap petImageBitmap;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpet);

        editTextName = findViewById(R.id.pet_name);
        editTextAge = findViewById(R.id.pet_age);
        imageViewPet = findViewById(R.id.pet_image);
        buttonSubmit = findViewById(R.id.btn_submit);

        imageViewPet.setOnClickListener(view -> pickImage());
        buttonSubmit.setOnClickListener(view -> savePetData());
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                petImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageViewPet.setImageBitmap(petImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getBreedByAge(int age) {
        if (age >= 1 && age <= 4) return "beagle";
        else if (age >= 5 && age <= 7) return "dalmatian";
        else if (age >= 8 && age <= 11) return "poodle";
        else if (age >= 12 && age <= 15) return "husky";
        else if (age >= 16 && age <= 20) return "german shepherd";
        else return "Unknown";
    }

    private void savePetData() {
        String name = editTextName.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("Enter pet name");
            return;
        }

        if (ageStr.isEmpty()) {
            editTextAge.setError("Enter pet age");
            return;
        }

        int age = Integer.parseInt(ageStr);
        String breed = getBreedByAge(age);

        if (petImageBitmap == null) {
            Toast.makeText(this, "Select a pet image", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Save image to internal storage
            String filename = name + "_image.png";
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            petImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            // Save data using SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("PetData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("PetName", name);
            editor.putInt("PetAge", age);
            editor.putString("PetBreed", breed);
            editor.putString("PetImageFilename", filename);
            editor.apply();

            Toast.makeText(this, "Pet registered successfully!", Toast.LENGTH_SHORT).show();

            // Navigate to PetProfile
            Intent intent = new Intent(addpet.this, Home.class);
            intent.putExtra("PetName", name);
            intent.putExtra("PetAge", age);
            intent.putExtra("PetBreed", breed);
            intent.putExtra("PetImageFilename", filename);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }
}
