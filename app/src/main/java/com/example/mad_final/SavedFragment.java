package com.example.mad_final;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class SavedFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public SavedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        LinearLayout savedItemsLayout = view.findViewById(R.id.saved_items_layout);

        // Retrieve saved items from Firestore
        retrieveSavedItems(savedItemsLayout);

        return view;
    }

    private void retrieveSavedItems(LinearLayout savedItemsLayout) {
        String userId = mAuth.getCurrentUser().getUid();

        db.collection("users").document(userId)
                .collection("bookmarks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                String placeName = document.getString("placeName");
                                String bookmarkDateTime = document.getString("bookmarkDateTime");

                                if (placeName != null && bookmarkDateTime != null) {
                                    View savedItemView = getLayoutInflater().inflate(R.layout.fragment_saved_item, savedItemsLayout, false);

                                    TextView nameTextView = savedItemView.findViewById(R.id.saved_item_name);
                                    TextView datetimeTextView = savedItemView.findViewById(R.id.saved_item_datetime);

                                    nameTextView.setText(placeName);
                                    datetimeTextView.setText(bookmarkDateTime);

                                    savedItemsLayout.addView(savedItemView);
                                }
                            }
                        }
                    }
                });
    }
}
