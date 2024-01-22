package com.example.mad_final;

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
    List<Place> placeList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize placeList and RecyclerView
        placeList = new ArrayList<>();
        initPlaceList();

        recyclerView = view.findViewById(R.id.home_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter and set it to RecyclerView
        placeAdapter = new PlaceAdapter(getContext(), placeList);
        recyclerView.setAdapter(placeAdapter);

        return view;
    }

    private void initPlaceList() {
        placeList.add(new Place("Place 1", "Rated 4.5", "Opens at 8 PM", R.mipmap.image_1, "Description for Place 1"));
        placeList.add(new Place("Place 2", "Rated 4.2", "Opens at 9 PM", R.mipmap.image_2, "Description for Place 2"));
        placeList.add(new Place("Place 3", "Rated 4.7", "Opens at 7 PM", R.mipmap.image_3, "Description for Place 3"));
        placeList.add(new Place("Place 4", "Rated 4.0", "Opens at 10 PM", R.mipmap.image_4, "Description for Place 4"));
    }
}
