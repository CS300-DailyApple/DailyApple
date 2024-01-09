package com.example.cs300_dailyapple.Fragments;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.BodyInformation;
import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.example.cs300_dailyapple.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    public ArrayList<BodyInformation> historyBI;

    public HistoryAdapter(ArrayList<BodyInformation> historyBI) {
        this.historyBI = historyBI;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_body_infor, parent, false);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        BodyInformation BI = historyBI.get(position);
        holder.heightTextView.setText(String.valueOf(BI.getHeight()));
        holder.weightTextView.setText(String.valueOf(BI.getWeight()));
        holder.dateTimeTextView.setText(BI.getTime());
        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyBI.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyBI.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView dateTimeTextView;
        TextView heightTextView;
        TextView weightTextView;
        ImageButton cancelButton;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTimeTextView = itemView.findViewById(R.id.textViewDateTime);
            heightTextView = itemView.findViewById(R.id.textViewHeight);
            weightTextView = itemView.findViewById(R.id.textViewWeight);
            cancelButton = itemView.findViewById(R.id.cancelButton);
        }
    }
}
