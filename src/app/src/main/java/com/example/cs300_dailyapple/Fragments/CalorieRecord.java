package com.example.cs300_dailyapple.Fragments;

import android.annotation.SuppressLint;
import android.health.connect.datatypes.NutritionRecord;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cs300_dailyapple.Models.Nutrition;
import com.example.cs300_dailyapple.Models.NutritionOverall;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;
import com.example.cs300_dailyapple.Services.DataService;
import com.google.rpc.context.AttributeContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalorieRecord extends Fragment {
    RelativeLayout breakfastView, lunchView, dinnerView, snackView;
    TextView caloriesIntakeView, caloriesNeededView, carbsView, proteinView, fatView, dateView, waterDrinkingView, breakfastCaloriesView, lunchCaloriesView, dinnerCaloriesView, snackCaloriesView;
    ProgressBar progressView;
    AuthService authService;
    DataService dataService;
    User currentUser;
    String id;
    Date date;

    NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calorie_record, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView(view);
        loadDataForViews();
        setOnClickListenerForViews();
    }
    @SuppressLint("SetTextI18n")
    public void loadDataForViews(){
        authService = AuthService.getInstance();
        dataService = DataService.getInstance();
        id = authService.getCurrentUser().getUid();
        currentUser = dataService.getUser(id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM");
        dateView.setText(dateFormat.format(new Date()));
        Nutrition nutritionAbsorbed = currentUser.getNutritionOverall().getNutritionAbsorbed();
        Nutrition nutritionTarget = currentUser.getNutritionOverall().getNutritionTarget();
        carbsView.setText(String.valueOf(nutritionAbsorbed.getCarbs()) + "/" + String.valueOf(nutritionTarget.getCarbs()));
        proteinView.setText(String.valueOf(nutritionAbsorbed.getProtein()) + "/" + String.valueOf(nutritionTarget.getProtein()));
        fatView.setText(String.valueOf(nutritionAbsorbed.getFat()) + "/" + String.valueOf(nutritionTarget.getFat()));
        caloriesIntakeView.setText(String.valueOf(nutritionAbsorbed.getKcal()));
        caloriesNeededView.setText(String.valueOf(nutritionTarget.getKcal()));
        waterDrinkingView.setText(String.valueOf(currentUser.getWaterInformation().getTotalWaterDrank()) + "/" + String.valueOf(currentUser.getWaterInformation().getWaterTarget()));
        breakfastCaloriesView.setText(String.valueOf(currentUser.getNutritionOverall().getMealHistory().getBreakfast().getNutrition().getKcal()));
        lunchCaloriesView.setText(String.valueOf(currentUser.getNutritionOverall().getMealHistory().getLunch().getNutrition().getKcal()));
        dinnerCaloriesView.setText(String.valueOf(currentUser.getNutritionOverall().getMealHistory().getDinner().getNutrition().getKcal()));
        snackCaloriesView.setText(String.valueOf(currentUser.getNutritionOverall().getMealHistory().getSnack().getNutrition().getKcal()));

    }

    public void setOnClickListenerForViews(){
        breakfastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lunchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dinnerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        snackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_calorieRecord_to_chartFragment);
            }
        });

        waterDrinkingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_calorieRecord_to_waterDrinking);
            }
        });
    }

//    public void saveData(){
//        dataService.saveUserInformation(currentUser);
//    }


    public void setView(View view){
        navController = Navigation.findNavController(view);
        caloriesIntakeView = view.findViewById(R.id.calories_intake);
        caloriesNeededView = view.findViewById(R.id.calories_needed);
        carbsView = view.findViewById(R.id.carbs);
        proteinView = view.findViewById(R.id.protein);
        fatView = view.findViewById(R.id.fat);
        dateView = view.findViewById(R.id.date);
        waterDrinkingView = view.findViewById(R.id.water_drinking);
        breakfastView = view.findViewById(R.id.breakfast);
        breakfastCaloriesView = view.findViewById(R.id.breakfast_calories);
        lunchView = view.findViewById(R.id.lunch);
        lunchCaloriesView = view.findViewById(R.id.lunch_calories);
        dinnerView = view.findViewById(R.id.dinner);
        dinnerCaloriesView = view.findViewById(R.id.dinner_calories);
        snackView = view.findViewById(R.id.snack);
        snackCaloriesView = view.findViewById(R.id.snack_calories);
        progressView = view.findViewById(R.id.progressBar);
    }
}