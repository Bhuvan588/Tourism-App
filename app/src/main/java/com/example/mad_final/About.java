package com.example.mad_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        TextView alink = findViewById(R.id.aditya_linkedin);
        TextView aslink = findViewById(R.id.ashutosh_linkedin);
        TextView blink = findViewById(R.id.bhuvan_linkedin);

        alink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.linkedin.com/in/aditya-kishore-8913991b7");
            }
        });

        aslink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.linkedin.com/in/ashutosh-p-kulkarni-0b1663236");
            }
        });

        blink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.linkedin.com/in/bhuvan-savant-72449522a");
            }
        });

    }

    private void openWebPage(String url) {
        // Create an implicit intent with ACTION_VIEW action
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));



        startActivity(intent);
    }
}