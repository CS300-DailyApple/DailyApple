package com.example.cs300_dailyapple.Fragments;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.example.cs300_dailyapple.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private LinkedList<PersonalInformation> personalInformationList;
    private LayoutInflater inflater;
    private OnCancelButtonClickListener cancelButtonClickListener;

    public HistoryAdapter(Context context, LinkedList<PersonalInformation> personalInformationList, OnCancelButtonClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.personalInformationList = personalInformationList;
        this.cancelButtonClickListener = listener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_body_infor, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        PersonalInformation currentInfo = personalInformationList.get(position);
        holder.bind(currentInfo);
    }

    @Override
    public int getItemCount() {
        return personalInformationList.size();
    }

    public interface OnCancelButtonClickListener {
        void onCancelButtonClick(int position);
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTimeTextView;
        private TextView heightTextView;
        private TextView weightTextView;
        private ImageButton cancelButton;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTimeTextView = itemView.findViewById(R.id.textViewDateTime);
            heightTextView = itemView.findViewById(R.id.textViewHeight);
            weightTextView = itemView.findViewById(R.id.textViewWeight);
            cancelButton = itemView.findViewById(R.id.cancelButton);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        cancelButtonClickListener.onCancelButtonClick(position);
                    }
                }
            });
        }

        public void bind(PersonalInformation personalInformation) {
            // Set today's date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate date = LocalDate.now();
            dateTimeTextView.setText(dtf.format(date));
            heightTextView.setText(String.valueOf(personalInformation.getHeight()) + " cm");
            weightTextView.setText(String.valueOf(personalInformation.getWeight()) + " kg");
        }
    }
}
