package com.example.mad_final;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlaceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        // Retrieve data from the intent
        Intent intent = getIntent();
        String placeName = intent.getStringExtra("placeName");
        String placeDescription = intent.getStringExtra("placeDescription");

        // Set the place name and description to TextViews
        TextView nameTextView = findViewById(R.id.place_details_name);
        TextView descriptionTextView = findViewById(R.id.place_details_description);

        nameTextView.setText(placeName);
        descriptionTextView.setText(placeDescription);
    }
}
