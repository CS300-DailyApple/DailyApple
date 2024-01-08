package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cs300_dailyapple.R;

public class AdminFoodSetting extends Fragment {
    private TextView adminAddFood;
    private TextView adminSuggestedFood;

    public AdminFoodSetting() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_food_setting, container, false);

        adminAddFood = view.findViewById(R.id.adminAddFood);
        adminSuggestedFood = view.findViewById(R.id.adminSuggestedFood);

        adminSuggestedFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_adminFoodSetting_to_suggestedFoodFragment);
            }
        });

        adminAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_adminFoodSetting_to_adminCreateNewFoodFirst);
            }
        });

        return view;
    }
}
