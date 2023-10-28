package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Event> list;

    public MyAdapter(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    public void setFilteredList(ArrayList<Event> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.event_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = list.get(position);
        holder.eventTitle.setText(event.getEventTitle());
        holder.eventType.setText(event.getEventType());
        holder.eventDate.setText(event.getEventDate());
        holder.eventLocation.setText(event.getEventLocation());
        holder.eventDescription.setText(event.getEventDescription());
        holder.eventExpertise.setText(event.getEventExpertise());
        holder.eventTalentCount.setText(event.getEventTalentCount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eventTitle, eventType, eventDate, eventLocation, eventDescription, eventExpertise,eventTalentCount;
        public MyViewHolder(View v) {
            super(v);
            eventTitle = v.findViewById(R.id.eventTitle);
            eventType = v.findViewById(R.id.eventType);
            eventDate = v.findViewById(R.id.eventDate);
            eventLocation = v.findViewById(R.id.eventLocation);
            eventDescription = v.findViewById(R.id.eventDescription);
            eventExpertise = v.findViewById(R.id.lookingfor);
            eventTalentCount = v.findViewById(R.id.eventTalentCount);
        }
    }

}
