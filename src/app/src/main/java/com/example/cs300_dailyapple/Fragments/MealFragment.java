package com.example.cs300_dailyapple.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.FoodCompound;
import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;

import java.util.LinkedList;
import java.util.Objects;


public class MealFragment extends Fragment {
    private RecyclerView recyclerViewDish;
    private DishAdapter dishAdapter;
    TextView totalCaloriesTextView, mealView;
    NavController navController;

    User currentUser;
    GlobalApplication globalApplication;
    AppCompatButton addButton;

    String currentMealChoosing;
    FoodCompound meal;

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        globalApplication = (GlobalApplication) this.getActivity().getApplication();
        currentMealChoosing = globalApplication.getCurrentMealChoosing();
        Context context = this.getContext();
        LinkedList<Food> foodList = new LinkedList<>();
        mealView = view.findViewById(R.id.MealText);
        recyclerViewDish = view.findViewById(R.id.recyclerViewDish);
        currentUser = globalApplication.getUser();
        System.out.println(currentMealChoosing);
        if (currentMealChoosing.equals("breakfast")) {
            meal = currentUser.getNutritionOverall().getMealHistory().getBreakfast();
            mealView.setText("Bữa sáng");
        }
        else if (currentMealChoosing.equals("lunch")) {
            meal = currentUser.getNutritionOverall().getMealHistory().getLunch();
            mealView.setText("Bữa trưa");
        }
        else if (currentMealChoosing.equals("dinner")) {
            meal = currentUser.getNutritionOverall().getMealHistory().getDinner();
            mealView.setText("Bữa tối");
        }
        else{
            meal = currentUser.getNutritionOverall().getMealHistory().getSnack();
            mealView.setText("Bữa ăn nhẹ");
        }


        dishAdapter = new DishAdapter(new LinkedList<>(meal.getFoodsList()),context,this);
        recyclerViewDish.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewDish.setAdapter(dishAdapter);
        totalCaloriesTextView= view.findViewById(R.id.TotalCalories);
        totalCaloriesTextView.setText(meal.getNutrition().getKcal().intValue()+ " calo");
        addButton = view.findViewById(R.id.Add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_mealFragment_to_foodFragment);
            }
        });

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onNavigationFragment();
            }
        };
    }

    private void onNavigationFragment(){
        globalApplication.setMeal(currentMealChoosing, meal);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        return view;
    }
    private int getTotalCalories(LinkedList<Food> dishList){
        int total=0;
        for (Food dish : dishList) {
            total += dish.getNutritionPerUnit().getKcal();
        }
        return total;
    }
    public void updateTotalCalories(LinkedList<Food> dishList) {
        int totalCalories = getTotalCalories(dishList);
        totalCaloriesTextView.setText(String.valueOf(totalCalories) + " calo");
    }
}