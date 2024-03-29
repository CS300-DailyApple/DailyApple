package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Models.Nutrition;
import com.example.cs300_dailyapple.R;

public class AddDishDetailsFragment extends Fragment {

    EditText amountKcalEditText;
    EditText amountFatEditText;
    EditText amountCarbsEditText;
    EditText amountProteinEditText;
    Button complete;
    GlobalApplication globalApplication;
    NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        amountKcalEditText = view.findViewById(R.id.amountKcal);
        amountFatEditText = view.findViewById(R.id.amountFat);
        amountCarbsEditText = view.findViewById(R.id.amountCarbs);
        amountProteinEditText = view.findViewById(R.id.amountProtein);
        complete = view.findViewById(R.id.completeButton);
        Context context=this.getContext();
        navController = Navigation.findNavController(view);
        globalApplication = (GlobalApplication) GlobalApplication.getInstance();
        Food food = globalApplication.getCustomDish();
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toNumber(amountCarbsEditText)==-1 || toNumber(amountFatEditText)==-1 || toNumber(amountKcalEditText)==-1 || toNumber(amountProteinEditText)==-1){
                    Toast.makeText(context, "Bạn phải điền đầy đủ các thông tin của món ăn", Toast.LENGTH_SHORT).show();
                } else {
                    //Save data and navigate here
                    Nutrition nutritionPerUnit = new Nutrition();
                    nutritionPerUnit.setKcal(Double.parseDouble(amountKcalEditText.getText().toString()));
                    nutritionPerUnit.setFat(Double.parseDouble(amountFatEditText.getText().toString()));
                    nutritionPerUnit.setCarbs(Double.parseDouble(amountCarbsEditText.getText().toString()));
                    nutritionPerUnit.setProtein(Double.parseDouble(amountProteinEditText.getText().toString()));
                    food.setNutritionPerUnit(nutritionPerUnit);
                    globalApplication.setCustomDish(food);
                    globalApplication.addCustomDish();
                    navController.navigate(R.id.action_addDishDetailsFragment_to_foodFragment);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_dish_details, container, false);





        return view;
    }
    private int toNumber(EditText editText) {
        String textFromEditText = editText.getText().toString();

        if (TextUtils.isEmpty(textFromEditText)) {
            return -1;
        } else {
            try {
                return Integer.parseInt(textFromEditText);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return -1;
            }
        }
    }
}