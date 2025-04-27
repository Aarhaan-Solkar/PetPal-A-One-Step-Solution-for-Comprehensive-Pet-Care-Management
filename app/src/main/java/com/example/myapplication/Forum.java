package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Forum extends AppCompatActivity {

    private Handler handler = new Handler();
    private int index = 0;
    private LinearLayout forumContainer;

    private final String[][] dummyData = {
            {"John Doe", "How often should I bathe my Labrador?"},
            {"Priya Shah", "What are the best food options for senior dogs?"},
            {"Amit Mehra", "Any tips for training a new puppy?"},
            {"Sara Khan", "Why is my dog shedding excessively?"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        // Setup edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize navigation buttons
        ImageButton button7 = findViewById(R.id.imageButton7);
        ImageButton button14 = findViewById(R.id.imageButton14);
        ImageButton button9 = findViewById(R.id.imageButton9);

        button7.setOnClickListener(v -> startActivity(new Intent(Forum.this, Home.class)));
        button14.setOnClickListener(v -> startActivity(new Intent(Forum.this, Service.class)));
        button9.setOnClickListener(v -> startActivity(new Intent(Forum.this, Profile.class)));

        // Get the forum container layout
        forumContainer = findViewById(R.id.forum_container);

        // Start showing forum cards dynamically
        startCardAnimation();
    }

    private void startCardAnimation() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (index < dummyData.length) {
                    String name = dummyData[index][0];
                    String question = dummyData[index][1];

                    CardView card = createForumCard(name, question);
                    forumContainer.addView(card);

                    // Fade in animation
                    card.setAlpha(0f);
                    card.setVisibility(View.VISIBLE);
                    card.animate().alpha(1f).setDuration(500).start();

                    index++;
                    handler.postDelayed(this, 2000); // 2 seconds delay between cards
                }
            }
        };
        handler.post(runnable);
    }

    private CardView createForumCard(String name, String question) {
        CardView cardView = new CardView(this);
        cardView.setRadius(16f);
        cardView.setCardElevation(8f);
        cardView.setUseCompatPadding(true);
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setVisibility(View.INVISIBLE);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        TextView userName = new TextView(this);
        userName.setText(name);
        userName.setTextSize(18);
        userName.setTextColor(Color.BLACK);
        userName.setTypeface(null, Typeface.BOLD);

        TextView userQuestion = new TextView(this);
        userQuestion.setText(question);
        userQuestion.setTextSize(16);
        userQuestion.setTextColor(Color.BLACK);
        userQuestion.setPadding(0, 20, 0, 0);

        layout.addView(userName);
        layout.addView(userQuestion);
        cardView.addView(layout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 20, 20, 20);
        cardView.setLayoutParams(params);

        return cardView;
    }
}
