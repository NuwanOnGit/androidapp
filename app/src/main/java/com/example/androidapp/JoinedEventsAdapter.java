package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.JoinedEvent;
import com.example.androidapp.R;

import java.util.ArrayList;

// JoinedEventsAdapter.java
public class JoinedEventsAdapter extends RecyclerView.Adapter<JoinedEventsAdapter.JoinedEventViewHolder> {

    private Context context;
    private ArrayList<JoinedEvent> joinedEventsList;

    public JoinedEventsAdapter(Context context, ArrayList<JoinedEvent> joinedEventsList) {
        this.context = context;
        this.joinedEventsList = joinedEventsList;
    }

    @NonNull
    @Override
    public JoinedEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.joined_event_card, parent, false);
        return new JoinedEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinedEventViewHolder holder, int position) {
        JoinedEvent joinedEvent = joinedEventsList.get(position);

        // Bind data to views
        holder.eventTitle.setText(joinedEvent.getEventTitle());
        holder.eventType.setText(joinedEvent.getEventType());



        // Add onClickListeners or other actions as needed
    }

    @Override
    public int getItemCount() {
        return joinedEventsList.size();
    }

    static class JoinedEventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        TextView eventType;


        public JoinedEventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventType = itemView.findViewById(R.id.eventType);
            
        }
    }
}
