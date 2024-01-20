package com.example.mad_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class JK_Explore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jk_explore);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CityModel> cityList = new ArrayList<>();
        // Add your city data to the list
        cityList.add(new CityModel(R.mipmap.image_1, "Srinagar", "Dal Lake", "Srinagar Airport", "12000 tourists annually"));
        cityList.add(new CityModel(R.mipmap.image_2, "Leh", "Shanti Stupa", "Leh Airport", "12880 tourists annually"));
        cityList.add(new CityModel(R.mipmap.image_3, "GulMarg", "St Mary’s Church", "50km from Srinagar", "6588 tourists annually"));
        cityList.add(new CityModel(R.mipmap.image_4, "Pahalgam", "Sheshnag Lake", "95km from Srinagar", "9880 tourists annually"));

        // Add more cities as needed

        CityAdapter adapter = new CityAdapter(this, cityList);
        recyclerView.setAdapter(adapter);
    }
}