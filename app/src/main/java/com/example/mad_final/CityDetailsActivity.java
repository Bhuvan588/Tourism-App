package com.example.mad_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CityDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);

        String cityName = getIntent().getStringExtra("cityName");

        // Find the TextView in your layout and set the city name
        TextView cityNameTextView = findViewById(R.id.city_details_name);
        cityNameTextView.setText(cityName);

        Button mapBtn = findViewById(R.id.open_map_btn);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapsUri = Uri.parse("https://www.google.com/maps/search/" + Uri.encode(cityName));
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, mapsUri);
                startActivity(websiteIntent);
            }
        });
    }
}