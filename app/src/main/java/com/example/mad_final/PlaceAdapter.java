package com.example.mad_final;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mad_final.R;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

     List<Place> placeList;
     Context context;

    public PlaceAdapter(Context context, List<Place> placeList) {
        this.context = context;
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.place_card_layout, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = placeList.get(position);

        // Bind data to views
        holder.placeImageView.setImageResource(place.getImage());
        holder.placeNameTextView.setText(place.getPlace_name());
        holder.ratingTextView.setText(place.getPlace_rating());
        holder.statusTextView.setText(place.getPlace_status());

        // Handle button click
        holder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the new activity and pass the place name and description
                Intent intent = new Intent(context, PlaceDetailsActivity.class);
                intent.putExtra("placeName", place.getPlace_name());
                intent.putExtra("placeDescription", place.getPlace_description());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList != null ? placeList.size() : 0;
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImageView;
        TextView placeNameTextView;
        TextView ratingTextView;
        TextView statusTextView;
        Button detailsButton;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            placeImageView = itemView.findViewById(R.id.place_image);
            placeNameTextView = itemView.findViewById(R.id.place_name);
            ratingTextView = itemView.findViewById(R.id.place_rating);
            statusTextView = itemView.findViewById(R.id.place_status);
            detailsButton = itemView.findViewById(R.id.place_details_button);
        }
    }


}
