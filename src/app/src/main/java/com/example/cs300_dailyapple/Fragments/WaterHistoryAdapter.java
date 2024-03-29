package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.WaterHistoryItem;
import com.example.cs300_dailyapple.R;

import java.util.ArrayList;

public class WaterHistoryAdapter extends RecyclerView.Adapter<WaterHistoryAdapter.WaterHistoryViewHolder>{
    ArrayList<WaterHistoryItem> WaterHistory;
    WaterDrinking fragment = null;

    public WaterHistoryAdapter(ArrayList<WaterHistoryItem> WaterHistory) {
        this.WaterHistory = WaterHistory;
    }

    public WaterHistoryAdapter(ArrayList<WaterHistoryItem> WaterHistory, WaterDrinking fragment) {
        this.WaterHistory = WaterHistory;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public WaterHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_water, parent, false);
        return new WaterHistoryViewHolder(view);
    }
    public WaterHistoryItem getWaterItemAtPosition(int position) {
        return WaterHistory.get(position);
    }
    @Override
    public void onBindViewHolder(@NonNull WaterHistoryViewHolder holder, int position) {
        WaterHistoryItem item = WaterHistory.get(position);
        holder.waterAmount.setText("+ " + item.getWaterAmount() + " ml");
        holder.time.setText(item.get_hm());
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaterHistory.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                fragment.UpdateView();
            }
        });
    }

    @Override
    public int getItemCount() {
        return WaterHistory.size();
    }

    static class WaterHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView waterAmount;
        TextView time;
        ImageView removeButton;

        public WaterHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            waterAmount = itemView.findViewById(R.id.water_amount);
            time = itemView.findViewById(R.id.water_time);
            removeButton = itemView.findViewById(R.id.close_button);
        }

    }

}
