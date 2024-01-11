package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Models.SuggestedFood;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.LinkedList;

public class SuggestFoodDetailsFragment extends Fragment {
    GlobalApplication globalApplication;
    SuggestedFood suggestedFood;
    // Views
    private TextView foodNameTextView;
    ImageView foodImageView;
    private TextView caloriesTextView;
    private TextView carbsTextView;
    private TextView proteinTextView;
    private TextView fatTextView;
    private TextView contributorTextView;
    AppCompatButton accept;
    AppCompatButton reject;
    AppCompatButton markViolation;
    PieChart pieChart;

    public SuggestFoodDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalApplication = (GlobalApplication) getActivity().getApplicationContext();

        return inflater.inflate(R.layout.fragment_suggest_food_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        String foodName = arguments.getString("foodName");
        // get suggested food from list by name
        LinkedList<SuggestedFood> suggestedFoodList = globalApplication.getForAdminSuggestedFoodList();
        for (SuggestedFood sf : suggestedFoodList) {
            if (sf.getName().equals(foodName)) {
                suggestedFood = sf;
                break;
            }
        }
        // Initialize views
        foodNameTextView = view.findViewById(R.id.AdminFoodDetailName);
        caloriesTextView = view.findViewById(R.id.AdminFoodDetailCalories);
        foodImageView = view.findViewById(R.id.AdminFoodDetailImage);
        foodImageView.setImageResource(R.drawable.food_photo_placeholder);
        carbsTextView = view.findViewById(R.id.AdminFoodDetailCarbs);
        proteinTextView = view.findViewById(R.id.AdminFoodDetailProtein);
        fatTextView = view.findViewById(R.id.AdminFoodDetailFat);
        accept = view.findViewById(R.id.accept);
        reject = view.findViewById(R.id.ignore);
        markViolation = view.findViewById(R.id.markViolation);
        contributorTextView = view.findViewById(R.id.contributorName);
        // Set up views
        foodNameTextView.setText(foodName);
        caloriesTextView.setText(Math.round(suggestedFood.getNutritionPerUnit().getKcal()) + " kcals");
        carbsTextView.setText(Math.round(suggestedFood.getNutritionPerUnit().getCarbs()) + " g");
        proteinTextView.setText(Math.round(suggestedFood.getNutritionPerUnit().getProtein()) + " g");
        fatTextView.setText(Math.round(suggestedFood.getNutritionPerUnit().getFat()) + " g");
        contributorTextView.setText("Người đóng góp: " + suggestedFood.getContributorName());
        pieChart = view.findViewById(R.id.NutritionPieChart);
        pieChart.clearChart();
        Long protein = Math.round(suggestedFood.getNutritionPerUnit().getProtein());
        Long fat = Math.round(suggestedFood.getNutritionPerUnit().getFat());
        Long carbs = Math.round(suggestedFood.getNutritionPerUnit().getCarbs());
        pieChart.addPieSlice(new PieModel("Protein", protein, getResources().getColor(R.color.protein)));
        pieChart.addPieSlice(new PieModel("Fat", fat, getResources().getColor(R.color.fat)));
        pieChart.addPieSlice(new PieModel("Carbs", carbs, getResources().getColor(R.color.carbs)));

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add food to shared food list
                Food food = new Food(suggestedFood);
                LinkedList<Food> sharedFoodList = globalApplication.getForAdminFoodList();
                sharedFoodList.add(food);
                globalApplication.setForAdminFoodList(sharedFoodList);
                // Remove food from suggested food list
                LinkedList<SuggestedFood> suggestedFoodList = globalApplication.getForAdminSuggestedFoodList();
                suggestedFoodList.remove(suggestedFood);
                globalApplication.setForAdminSuggestedFoodList(suggestedFoodList);
                // Go back to suggested food list
                Navigation.findNavController(v).popBackStack(R.id.suggestedFoodFragment, false);
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove food from suggested food list
                LinkedList<SuggestedFood> suggestedFoodList = globalApplication.getForAdminSuggestedFoodList();
                suggestedFoodList.remove(suggestedFood);
                globalApplication.setForAdminSuggestedFoodList(suggestedFoodList);
                // Go back to suggested food list
                Navigation.findNavController(v).popBackStack(R.id.suggestedFoodFragment, false);
            }
        });

        markViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // minus 1 point from contributor
                ArrayList<User> userList = globalApplication.getForAdminUserList();
                for (User user : userList) {
                    if (user.getUsername().equals(suggestedFood.getContributorName())) {
                        user.setCreditPoints(user.getCreditPoints() - 1);
                        break;
                    }
                }
                globalApplication.setForAdminUserList(userList);
                // Remove food from suggested food list
                LinkedList<SuggestedFood> suggestedFoodList = globalApplication.getForAdminSuggestedFoodList();
                suggestedFoodList.remove(suggestedFood);
                globalApplication.setForAdminSuggestedFoodList(suggestedFoodList);
                // Go back to suggested food list
                Navigation.findNavController(v).popBackStack(R.id.suggestedFoodFragment, false);
            }
        });
    }
}
