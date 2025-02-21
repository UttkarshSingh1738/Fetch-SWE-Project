package com.example.fetchrewardsproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FetchItemAdapter extends RecyclerView.Adapter<FetchItemAdapter.FetchItemViewHolder> {

    private List<FetchItem> fetchItemList;

    public FetchItemAdapter(List<FetchItem> fetchItemList) {
        this.fetchItemList = fetchItemList;
    }

    @NonNull
    @Override
    public FetchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fetch, parent, false);
        return new FetchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FetchItemViewHolder holder, int position) {
        FetchItem item = fetchItemList.get(position);
        holder.listIdTextView.setText("List ID: " + item.getListId());
        holder.nameTextView.setText("Name: " + item.getName());
    }

    @Override
    public int getItemCount() {
        return fetchItemList.size();
    }

    public static class FetchItemViewHolder extends RecyclerView.ViewHolder {

        TextView listIdTextView;
        TextView nameTextView;

        public FetchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            listIdTextView = itemView.findViewById(R.id.listIdTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }

}
