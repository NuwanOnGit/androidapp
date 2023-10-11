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


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.event_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = list.get(position);
        holder.title.setText(event.getTitle());
        holder.type.setText(event.getType());
        holder.date.setText(event.getDate());
        holder.status.setText(event.getStatus());
        holder.description.setText(event.getDescription());
        holder.count.setText(event.getCount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, type, date, status, description, count;
        public MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.eventTitle);
            type = v.findViewById(R.id.eventType);
            date = v.findViewById(R.id.eventDate);
            status = v.findViewById(R.id.eventStatus);
            description = v.findViewById(R.id.eventDescription);
            count = v.findViewById(R.id.count);
        }
    }

}
