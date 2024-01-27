package  com.example.mad_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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


public class PlaceDetailsActivity extends AppCompatActivity {

    private boolean isBookmarked = false;
    private ImageView bookmarkIcon;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Retrieve data from the intent
        Intent intent = getIntent();
        String placeName = intent.getStringExtra("placeName");
        String placeDescription = intent.getStringExtra("placeDescription");

        // Set the place name and description to TextViews
        TextView nameTextView = findViewById(R.id.place_details_name);
         descriptionTextView = findViewById(R.id.place_details_description);

        nameTextView.setText(placeName);
        //descriptionTextView.setText(placeDescription);

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

        String url = "http://192.168.1.199:4000/api/v1/placeInfos";

        fetchPlaceDetails(url);


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

    private void fetchPlaceDetails(String apiUrl) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("API_RESPONSE", response);

                            // Convert the response to a JSONArray
                            JSONArray jsonArray = new JSONArray(response);

                            // Assuming the first entry in the array contains the description
                            if (jsonArray.length() > 0) {
                                JSONObject placeInfo = jsonArray.getJSONObject(0);
                                String placeDescription = placeInfo.getString("place_info_description");

                                // Set the result to TextView
                                descriptionTextView.setText(placeDescription);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("API_ERROR", "Error during API request: " + error.getMessage());
                        // Handle error here
                    }
                });

        requestQueue.add(stringRequest);
    }




}
