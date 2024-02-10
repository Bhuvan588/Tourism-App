package  com.example.mad_final;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Period;
import com.google.android.libraries.places.api.model.TimeOfWeek;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.IsOpenRequest;
import com.google.android.libraries.places.api.net.IsOpenResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.Review;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.rpc.Status;


public class PlaceDetailsActivity extends AppCompatActivity {

    private boolean isBookmarked = false;
    private ImageView bookmarkIcon;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    TextView descriptionTextView;

    private TextView place_rating;

    private TextView place_timing;

    private PlacesClient placesClient;

    private String placeId;

    @NonNull
    Calendar isOpenCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        Places.initialize(getApplicationContext(), "YOUR API KEY . MERA KYU CHAHIYE? :)");

        placesClient = Places.createClient(this);

        // Retrieve data from the intent
        Intent intent = getIntent();
        String placeName = intent.getStringExtra("placeName");
        String placeDescription = intent.getStringExtra("placeDescription");

        place_rating = findViewById(R.id.place_detail_rating);
        place_timing = findViewById(R.id.place_details_timing);

        // Set the place name and description to TextViews
        TextView nameTextView = findViewById(R.id.place_details_name);
         descriptionTextView = findViewById(R.id.place_details_description);

        nameTextView.setText(placeName);
        descriptionTextView.setText(placeDescription);

        // Set up bookmark icon
        bookmarkIcon = findViewById(R.id.bookmark_icon);
        updateBookmarkIcon();

        // Set click listener for the bookmark icon
        bookmarkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle bookmark state
                isBookmarked = !isBookmarked;

                // Update the bookmark icon
                updateBookmarkIcon();


                if (isBookmarked) {
                    savePlaceToFirestore(placeName);
                }
            }
        });
        getPlaceIdByName(placeName);


    }

    private void getPlaceIdByName(String placeName) {
        // Set up the request
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(placeName)
                .build();

        // Fetch predictions
        placesClient.findAutocompletePredictions(request).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FindAutocompletePredictionsResponse predictions = task.getResult();
                if (predictions != null) {
                    for (AutocompletePrediction prediction : predictions.getAutocompletePredictions()) {
                        // Extract the place ID from the prediction
                        placeId = prediction.getPlaceId();

                        // Now you have the place ID
                        // Use it as needed (e.g., call fetchPlaceDetails(placeId))
                        fetchPlaceDetails(placeId);

                        // Break out of the loop since we only need one prediction
                        break;
                    }
                }
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    // Handle the error using the exception
                    int status = ((ApiException) exception).getStatusCode();
                    // Handle the status code as needed
                }
            }
        });
    }

    private void updateBookmarkIcon() {
        bookmarkIcon.setImageResource(
                isBookmarked ? R.drawable.fav_orange : R.drawable.fav_grey
        );
    }

    private void savePlaceToFirestore(String placeName) {
        String userId = mAuth.getCurrentUser().getUid();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String bookmarkDateTime = dateFormat.format(new Date());

        Map<String, Object> bookmarkData = new HashMap<>();
        bookmarkData.put("placeName", placeName);
        bookmarkData.put("bookmarkDateTime", bookmarkDateTime);

        db.collection("users").document(userId)
                .collection("bookmarks")
                .add(bookmarkData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(PlaceDetailsActivity.this, "Place bookmarked!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PlaceDetailsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Firestore", "Error adding document", e);
                    }
                });
    }

    private void fetchPlaceDetails(String placeId) {
        List<Place.Field> placeFields = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.RATING,
                Place.Field.USER_RATINGS_TOTAL,
                Place.Field.OPENING_HOURS,
                Place.Field.PHOTO_METADATAS,
                Place.Field.REVIEWS
        );

        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();

        Places.createClient(this).fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();

            // Access additional details
            double rating = place.getRating();
            place_rating.setText(Double.toString(rating));

            int userRatingsTotal = place.getUserRatingsTotal();
            OpeningHours openingHours = place.getOpeningHours();

            if (openingHours != null) {
                // Get the periods during which the place is open
                List<Period> periods = openingHours.getPeriods();

                if (periods != null) {
                    for (Period period : periods) {
                        int openHour = period.getOpen().getTime().getHours();  // Use getTime() to get hours
                        int openMinute = period.getOpen().getTime().getMinutes();
                        int closeHour = period.getClose().getTime().getHours();  // Use getTime() for close hours
                        int closeMinute = period.getClose().getTime().getMinutes();

                        // Build and display the opening hour string
                        String openingHoursString = "Open from " + openHour + ":" + openMinute + " to " + closeHour + ":" + closeMinute;
                        place_timing.setText(openingHoursString);
                    }
                } else {
                    // Opening hours information is not available
                }
            } else {
                // Opening hours information is not available
            }


            List<Review> reviews = place.getReviews();
            List<PhotoMetadata> photoMetadatas = place.getPhotoMetadatas();

            // Handle or store these details as needed

            // For simplicity, let's fetch the first photo if available
            if (photoMetadatas != null && !photoMetadatas.isEmpty()) {
                PhotoMetadata photoMetadata = photoMetadatas.get(0);
                fetchAndDisplayPhoto(photoMetadata);
            }

        }).addOnFailureListener((exception) -> {
            // Handle failure
            Log.e("PlaceDetails", "Fetch place details request failed: " + exception.getMessage());
        });
    }

    private void fetchAndDisplayPhoto(PhotoMetadata photoMetadata) {
        FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                .setMaxHeight(200) // Set your desired photo height
                .setMaxWidth(200)  // Set your desired photo width
                .build();

        Places.createClient(this).fetchPhoto(photoRequest).addOnSuccessListener((response) -> {
            Bitmap bitmap = response.getBitmap();
            // Display the bitmap as needed
        }).addOnFailureListener((exception) -> {
            // Handle failure
            Log.e("PlaceDetails", "Fetch photo request failed: " + exception.getMessage());
        });
    }












}
