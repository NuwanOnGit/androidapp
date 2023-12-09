package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.androidapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    FirebaseAuth mAuth; //auth
    Button button;
    TextView textView;
    FirebaseUser user;
    FloatingActionButton fab;//Creating fab object
    DatabaseReference reference;
    RecyclerView recyclerView;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();//get current user id

        recyclerView=findViewById(R.id.recyclerView);
        //Connecting to firebase
        reference
                = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("event")
                ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Event> list=new ArrayList<>();
        MyAdapter adapter=new MyAdapter(this,list,currentUserId);
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    list.add(event);
                }
                // Reverse the list to display it in reverse order
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });

        //Join button code starts here

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onJoinButtonClick(int position) {
                // Handle the join button click event
                Event selectedEvent = list.get(position);

                // Check if the user is already joined
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String currentUserId = currentUser.getUid();
                    String joinedUid = selectedEvent.getEventJoinedUid();

                    if (TextUtils.isEmpty(joinedUid)) {
                        // No user has joined yet, initialize the field
                        selectedEvent.setEventJoinedUid(currentUserId);
                    } else {
                        // Users have already joined, add the new user to the list
                        selectedEvent.setEventJoinedUid(joinedUid + "," + currentUserId);
                    }

                    // Update the event in the database
                    reference.child(selectedEvent.getEventUid()).setValue(selectedEvent);
                }
            }
        });

        //Bottom Navigation Bar code starts here
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        //Floating action button code starts here
        fab = findViewById(R.id.fab); // Assuming your FAB has the id 'fab' in your XML layout file
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start login activity
                Intent intent = new Intent(getApplicationContext(),AddEvents.class);
                startActivity(intent);
            }
        });
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    //finish();
                    return true;
                } else if (itemId == R.id.talent) {
                    startActivity(new Intent(getApplicationContext(), Talent.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.activities) {
                    startActivity(new Intent(getApplicationContext(), Activities.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
                return false;
            }
        });

    }
}