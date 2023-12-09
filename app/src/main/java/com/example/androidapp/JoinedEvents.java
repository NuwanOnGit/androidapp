package com.example.androidapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinedEvents extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JoinedEventsAdapter adapter;
    private ArrayList<JoinedEvent> joinedEventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_events);

//        // Initialize RecyclerView
////        recyclerView = findViewById(R.id.recyclerViewJoinedEvents);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Initialize joined events list
//        joinedEventsList = new ArrayList<>();
//
//        // Initialize adapter and set it to RecyclerView
//        adapter = new JoinedEventsAdapter(this, joinedEventsList);
//        recyclerView.setAdapter(adapter);
//
//        // Load joined events data (e.g., from Firebase) and update the list
//        loadJoinedEventsData();

    }
    private void loadJoinedEventsData() {
        // Assuming you have a reference to the joined events in the Firebase Database
        DatabaseReference joinedEventsRef = FirebaseDatabase.getInstance().getReference().child("eventJoinedUid");

        // Add a ValueEventListener to retrieve data
        joinedEventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing list
                joinedEventsList.clear();

                // Iterate through the dataSnapshot to get each joined event
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Assuming you have a JoinedEvent class with appropriate constructor and getter methods
                    JoinedEvent joinedEvent = snapshot.getValue(JoinedEvent.class);

                    if (joinedEvent != null) {
                        // Add the joined event to the list
                        joinedEventsList.add(joinedEvent);
                    }
                }

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during the data retrieval
                Log.e(TAG, "Error loading joined events: " + databaseError.getMessage());
            }
        });
    }

}