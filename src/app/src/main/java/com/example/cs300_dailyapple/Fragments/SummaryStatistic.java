package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;
import com.example.cs300_dailyapple.Services.DataService;

import java.time.format.DateTimeFormatter;

public class SummaryStatistic extends Fragment {
    private TextView caloriesCount;
    private TextView textBMI;
    private TextView textDateTime;
    private TextView height;
    private TextView weight;
    private TextView weightEvaluation;
    private TextView waterText;
    private AppCompatButton continueButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary_statistic, container, false);
        return view;
    }
    // OnViewCreated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textBMI = view.findViewById(R.id.textBMI);
        textDateTime = view.findViewById(R.id.textDateTime);
        height = view.findViewById(R.id.Height);
        weight = view.findViewById(R.id.Weight);
        weightEvaluation = view.findViewById(R.id.weightEvaluation);
        waterText = view.findViewById(R.id.Water);
        continueButton = view.findViewById(R.id.Continue);
        String uid = AuthService.getInstance().getCurrentUser().getUid();
        DataService db = DataService.getInstance();
        User user = db.getUser(uid);
        // get today's date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        textDateTime.setText(dtf.format(java.time.LocalDate.now()));
        // get height and weight
        height.setText(user.getPersonalInformation().getHeight().toString() + "cm");
        weight.setText(user.getPersonalInformation().getWeight().toString() + "kg");
        // get BMI, round to integer
        textBMI.setText(String.valueOf(Math.round(user.getPersonalInformation().calculateBMI())));
        // get weight evaluation
        weightEvaluation.setText(getWeightEvaluation(user.getPersonalInformation().calculateBMI()));
        // get water
        waterText.setText(String.valueOf(Math.round(user.getPersonalInformation().calculateWater())));
        // continue button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_summaryStatistic_to_homeScreenUserFragment);
            }
        });
    }
    private String getWeightEvaluation(Double BMI){
        if(BMI<18.5){
            return "Thiếu cân";
        }
        else if(BMI<24.9){
            return "Bình thường";
        }
        else if(BMI<29.9){
            return "Thừa cân";
        }
        else{
            return "Béo phì";
        }
    }
}