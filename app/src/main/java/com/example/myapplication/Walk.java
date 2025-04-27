package com.example.myapplication;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class Walk extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 1;
    EditText etPetName, etMobileNumber;
    TextView tvSelectedTime;
    Button btnPickTime, btnSendSMS;

    String selectedTime = "";
    int selectedHour = -1, selectedMinute = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        etPetName = findViewById(R.id.etPetName);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        tvSelectedTime = findViewById(R.id.tvSelectedTime);
        btnPickTime = findViewById(R.id.btnPickTime);
        btnSendSMS = findViewById(R.id.btnSendSMS);

        btnPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog();
            }
        });

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check for SMS permission
                if (ContextCompat.checkSelfPermission(Walk.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Walk.this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
                } else {
                    scheduleSMS();
                }
            }
        });
    }

    // Method to open the time picker dialog
    private void openTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(Walk.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedHour = hourOfDay;
                        selectedMinute = minute;
                        selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        tvSelectedTime.setText("Walk Time: " + selectedTime);
                    }
                }, hour, minute, true);

        timePickerDialog.show();
    }

    // Method to schedule SMS using AlarmManager
    private void scheduleSMS() {
        String petName = etPetName.getText().toString();
        String mobileNumber = etMobileNumber.getText().toString();

        if (petName.isEmpty() || selectedTime.isEmpty() || mobileNumber.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields and pick a time", Toast.LENGTH_SHORT).show();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
            calendar.set(Calendar.MINUTE, selectedMinute);
            calendar.set(Calendar.SECOND, 0);

            // Check if the scheduled time is in the past
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                Toast.makeText(this, "Selected time is in the past, pick a future time.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Set up the AlarmManager
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, SmsBroadcastReceiver.class);
            intent.putExtra("mobileNumber", mobileNumber);
            intent.putExtra("petName", petName);
            intent.putExtra("time", selectedTime);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            Toast.makeText(this, "Reminder Scheduled!", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scheduleSMS();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}