package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cs300_dailyapple.Fragments.Dish;
import com.example.cs300_dailyapple.Fragments.DishAdapter;
import com.example.cs300_dailyapple.R;

import java.util.LinkedList;


public class MealFragment extends Fragment {
    private RecyclerView recyclerViewDish;
    private DishAdapter dishAdapter;
    ImageButton backButton;
    TextView totalCaloriesTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        backButton=view.findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Context context = this.getContext();
        LinkedList<Dish> dishList = Dish.loadDishList(context);
        recyclerViewDish = view.findViewById(R.id.recyclerViewDish);
        dishAdapter = new DishAdapter(Dish.loadDishList(context),context,this);
        recyclerViewDish.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewDish.setAdapter(dishAdapter);
        totalCaloriesTextView= view.findViewById(R.id.TotalCalories);
        totalCaloriesTextView.setText(getTotalCalories(dishList)+ " calo");

        return view;
    }
    private int getTotalCalories(LinkedList<Dish> dishList){
        int total=0;
        for (Dish dish : dishList) {
            total += dish.getCalo();
        }
        return total;
    }
    public void updateTotalCalories(LinkedList<Dish> dishList) {
        int totalCalories = getTotalCalories(dishList);
        totalCaloriesTextView.setText(totalCalories + " calo");
    }
}