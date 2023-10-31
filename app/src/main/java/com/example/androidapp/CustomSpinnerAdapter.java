package com.example.androidapp;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

public class CustomSpinnerAdapter extends ArrayAdapter<String> implements Filterable {
    private List<String> originalData;
    private List<String> filteredData;
    private Filter filter;

    public CustomSpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.originalData = items;
        this.filteredData = new ArrayList<>(items);
        this.filter = (Filter) new CustomFilter();
    }

    @NonNull
    @Override
    public android.widget.Filter getFilter() {
        return (android.widget.Filter) filter;
    }

    private class CustomFilter extends android.widget.Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<String> filteredList = new ArrayList<>();

            if (constraint != null && !constraint.toString().isEmpty()) {
                for (String item : originalData) {
                    if (item.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            } else {
                filteredList.addAll(originalData);
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData.clear();
            filteredData.addAll((List<String>) results.values);
            notifyDataSetChanged();
        }
    }
}
