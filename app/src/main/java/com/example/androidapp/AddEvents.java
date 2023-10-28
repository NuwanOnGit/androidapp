package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddEvents extends AppCompatActivity {

    FirebaseAuth mAuth;
    Spinner spinnerV;
    DatabaseReference reference,databaseReference;
    Button publishButton;
    ArrayList<String> spinnerList;
    ArrayAdapter<String> adapter;
    TextInputLayout date;
    TextInputEditText dateEditText,editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);
        date = findViewById(R.id.textInputLayout);
        dateEditText = findViewById(R.id.textInputDate);

        // Get references to your TextInputEditText fields and the button
        TextInputEditText eventTitleEditText = findViewById(R.id.textInputEventTitle);
        TextInputEditText eventTypeEditText = findViewById(R.id.textInputEventType);
        TextInputEditText dateEditText = findViewById(R.id.textInputDate);
        TextInputEditText eventDescriptionEditText = findViewById(R.id.textInputEventDescription);
        TextInputEditText expertiseEditText = findViewById(R.id.textInputExpertice);
        TextInputEditText talentCountEditText = findViewById(R.id.textInputTalentCount);
        TextInputEditText locationEditText = findViewById(R.id.textInputLocation);
        Button publishButton = findViewById(R.id.button_publish);

        // Assuming you have your Event class defined and imported

        publishButton = findViewById(R.id.button_publish);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the TextInputEditText fields
                String eventTitle = eventTitleEditText.getText().toString();
                String eventType = eventTypeEditText.getText().toString();
                String eventDate = dateEditText.getText().toString();
                String eventStatus = "Open to Join";
                String eventDescription = eventDescriptionEditText.getText().toString();
                String eventExpertise = expertiseEditText.getText().toString();
                String eventTalentCount = talentCountEditText.getText().toString();
                String eventLocation = locationEditText.getText().toString();
                // Get the current user's ID
                String eventUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String eventJoinedUid="";



                // Get a reference to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                // Get a reference to the events node
                DatabaseReference eventsRef = database.getReference("event");

                // Create a new Event object
                Events event = new Events(eventTitle,eventType,eventDate,eventStatus,eventDescription,eventExpertise,eventTalentCount,eventLocation,eventUid,eventJoinedUid);

                // Push the event object to the database under a unique ID
                eventsRef.push().setValue(event)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Data successfully stored in Firebase database
                                    Toast.makeText(AddEvents.this, "Event details saved successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Failed to store data in Firebase database
                                    Toast.makeText(AddEvents.this, "Failed to save event details", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        // Initialize spinner and adapter
        spinnerV = findViewById(R.id.spinner);
        editText = findViewById(R.id.textInputExpertice);
        reference = FirebaseDatabase.getInstance().getReference().child("expertice");
        spinnerList = new ArrayList<>();

        // Add an empty string as the default value
        spinnerList.add(""); // or you can add a placeholder like "Select an Expertice Area"
        adapter = new ArrayAdapter<>(AddEvents.this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinnerV.setAdapter(adapter);


        spinnerV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the Spinner
                String selectedItem = (String) parent.getItemAtPosition(position);

                // Set the selected item to the EditText
                editText.setText(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected, if needed
            }
        });
        ShowData();
    }
    private void ShowData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    spinnerList.add(item.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

        // on below line we are adding click listener for our pick date button
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        AddEvents.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                dateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });



    }
}