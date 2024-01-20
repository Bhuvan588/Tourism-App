package com.example.mad_final;

// CityAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private Context context;
    private List<CityModel> cityList;

    public CityAdapter(Context context, List<CityModel> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_card_layout, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        CityModel city = cityList.get(position);

        holder.cityImage.setImageResource(city.getCityImage());
        holder.cityName.setText(city.getCityName());
        holder.cityLandmark.setText(city.getCityLandmark());
        holder.cityAirport.setText(city.getCityAirport());
        holder.cityTourists.setText(city.getCityTourists());

        holder.cityDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click if needed
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {
        ImageView cityImage;
        TextView cityName, cityLandmark, cityAirport, cityTourists;
        Button cityDetailsBtn;
        CardView cardView;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityImage = itemView.findViewById(R.id.city_image);
            cityName = itemView.findViewById(R.id.city_name);
            cityLandmark = itemView.findViewById(R.id.city_landmark);
            cityAirport = itemView.findViewById(R.id.city_airport);
            cityTourists = itemView.findViewById(R.id.city_tourists);
            cityDetailsBtn = itemView.findViewById(R.id.city_details_btn);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
