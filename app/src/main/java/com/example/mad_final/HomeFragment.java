package com.example.mad_final;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import android.Manifest;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    PlaceAdapter placeAdapter;
    List<Place> placeList;

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    private TextView currentLocationTextView;







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

        currentLocationTextView = view.findViewById(R.id.currentLocation);

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        Places.initialize(getContext(), "YOUR API KEY. MERA KYU CHAHIYE? :)");



        //FOR SEARCH BAR


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return
        autocompleteFragment.setPlaceFields(Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull com.google.android.libraries.places.api.model.Place place) {
                // Handle the selected place
                String placeName = place.getName();
                String placeId = place.getId();

                Intent searchPlaceIntent = new Intent(getContext(), PlaceDetailsActivity.class);
                searchPlaceIntent.putExtra("placeName", placeName);
                searchPlaceIntent.putExtra("placeDescription", "HardCoded Description for " + placeName);
                startActivity(searchPlaceIntent);
            }

            @Override
            public void onError(@NonNull Status status) {
                // Handle the error
            }
        });





        return view;

    }

    public void onResume() {
        super.onResume();
        // Request location updates when the fragment is resumed
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        // Check location permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Set up the location request
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000); // Update every 10 seconds
        locationRequest.setFastestInterval(5000); // Fastest update interval
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Set up the location callback
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update the UI with the current location
                    updateCurrentLocationUI(location);
                }
            }
        };

        // Request location updates
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void updateCurrentLocationUI(Location location) {
        if (location != null) {
            // Update the TextView with the current location in words
            String address = getAddressFromLocation(getContext(), location.getLatitude(), location.getLongitude());
            currentLocationTextView.setText(address);


        }

    }

    private String getAddressFromLocation(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Location not available";
    }

    private String getPlaceIdFromLocation(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                // Obtain the place ID from the address and store it
                return address.getExtras().getString("place_id");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }






    private void initPlaceList() {



        placeList.add(new Place("Shree Dodda Ganapathi Temple", "Rated 4.5", "Opens at 5:30 AM", R.mipmap.dg2, "The huge Ganesha of this popular temple is 18 ft tall in height and 16 ft wide. The Dodda Ganesha Temple is also known as Shakthi Ganapathi and Satya Ganapathi."));
        placeList.add(new Place("Bengaluru Palace", "Rated 4.2", "Opens at 10 AM", R.mipmap.bp, "Description for Place 2"));
        placeList.add(new Place("Bangalore Fort", "Rated 4.7", "Opens at 8:30 AM", R.mipmap.bf, "Description for Place 3"));
//        placeList.add(new Place("Place 4", "Rated 4.0", "Opens at 10 PM", R.mipmap.image_4, "Description for Place 4"));
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop location updates when the fragment is paused
        if (fusedLocationProviderClient != null && locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }



}
