package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.WaterHistoryItem;
import com.example.cs300_dailyapple.R;

import java.util.ArrayList;

public class WaterHistoryAdapter extends RecyclerView.Adapter<WaterHistoryAdapter.WaterHistoryViewHolder>{
    ArrayList<WaterHistoryItem> WaterHistory;
    WaterHistoryAdapter adapter;

    public WaterHistoryAdapter(ArrayList<WaterHistoryItem> WaterHistory) {
        this.WaterHistory = WaterHistory;
    }
    public void setWaterHistory(ArrayList<WaterHistoryItem> WaterHistory) {
        this.WaterHistory = WaterHistory;
    }

    @NonNull
    @Override
    public WaterHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_water, parent, false);
        return new WaterHistoryViewHolder(view, this);
    }
    public WaterHistoryItem getWaterItemAtPosition(int position) {
        return WaterHistory.get(position);
    }
    @Override
    public void onBindViewHolder(@NonNull WaterHistoryViewHolder holder, int position) {
        WaterHistoryItem item = WaterHistory.get(position);
        holder.waterAmount.setText(item.getWaterAmount());
        holder.time.setText(item.get_hm());

    }

    @Override
    public int getItemCount() {
        return WaterHistory.size();
    }

    public void removeItem(int position) {
        WaterHistory.remove(position);
        notifyItemRemoved(position);
    }

    public ArrayList<WaterHistoryItem> getWaterHistory() {
        return WaterHistory;
    }

    static class WaterHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView waterAmount;
        TextView time;
        ImageView removeButton;
        WaterHistoryAdapter adapter;

        public WaterHistoryViewHolder(@NonNull View itemView, WaterHistoryAdapter adapter) {
            super(itemView);
            this.adapter = adapter;

            waterAmount = itemView.findViewById(R.id.water_amount);
            time = itemView.findViewById(R.id.water_time);
            removeButton = itemView.findViewById(R.id.close_button);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.removeItem(getAdapterPosition());
                }
            });
        }

    }

}
