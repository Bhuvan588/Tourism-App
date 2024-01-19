package com.example.mad_final;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    PlaceAdapter placeAdapter;
    List<Place> placeList ;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView
        initRecyclerView(view);

        // Populate placeList
        initPlaceList();

        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.home_recyclerView);


        // Initialize adapter and set it to RecyclerView
        placeAdapter = new PlaceAdapter(getContext(), placeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(placeAdapter);
    }


    private void initPlaceList() {
        placeList = new ArrayList<>();
        placeList.add(new Place("Place 1", "4.5", "8 PM", R.drawable.temple, "Description for Place 1"));
        placeList.add(new Place("Place 2", "4.2", "9 PM", R.drawable.temple, "Description for Place 2"));
        placeList.add(new Place("Place 3", "4.7", "7 PM", R.drawable.temple, "Description for Place 3"));
        placeList.add(new Place("Place 4", "4.0", "10 PM", R.drawable.temple, "Description for Place 4"));

        // Notify the adapter that the data set has changed

        placeAdapter.notifyDataSetChanged();



    }
}
