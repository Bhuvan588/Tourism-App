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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlaceDetailsActivity extends AppCompatActivity {

    private boolean isBookmarked = false;
    private ImageView bookmarkIcon;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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
        TextView descriptionTextView = findViewById(R.id.place_details_description);

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
}
