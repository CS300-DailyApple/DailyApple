package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
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

import java.util.LinkedList;


public class AdminFoodEditingSecond extends Fragment {
    GlobalApplication globalApplication;
    LinkedList<Food> foodList;
    EditText amountKcalEditText;
    EditText amountFatEditText;
    EditText amountCarbsEditText;
    EditText amountProteinEditText;
    Button complete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_food_editing_second, container, false);
        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
        foodList = globalApplication.getForAdminFoodList();
        amountKcalEditText = view.findViewById(R.id.amountKcal);
        amountFatEditText = view.findViewById(R.id.amountFat);
        amountCarbsEditText = view.findViewById(R.id.amountCarbs);
        amountProteinEditText = view.findViewById(R.id.amountProtein);
        complete = view.findViewById(R.id.completeButton);
        Context context=this.getContext();
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toNumber(amountCarbsEditText)==-1 || toNumber(amountFatEditText)==-1 || toNumber(amountKcalEditText)==-1 || toNumber(amountProteinEditText)==-1){
                    Toast.makeText(context, "Bạn phải điền đầy đủ các thông tin của món ăn", Toast.LENGTH_SHORT).show();
                } else {
                    Double amountKcal = Double.parseDouble(amountKcalEditText.getText().toString());
                    Double amountFat = Double.parseDouble(amountFatEditText.getText().toString());
                    Double amountCarbs = Double.parseDouble(amountCarbsEditText.getText().toString());
                    Double amountProtein = Double.parseDouble(amountProteinEditText.getText().toString());
                    // Create new food
                    Bundle bundle = getArguments();
                    Food food = new Food();
                    food.setName(bundle.getString("foodName"));
                    food.setUnit(bundle.getString("unit"));
                    food.setNumberOfUnits(bundle.getInt("numberOfUnits"));
                    Nutrition nutrition = new Nutrition();
                    nutrition.setKcal(amountKcal);
                    nutrition.setFat(amountFat);
                    nutrition.setCarbs(amountCarbs);
                    nutrition.setProtein(amountProtein);
                    food.setNutritionPerUnit(nutrition);
                    // update the food list
                    Food foodToUpdate = null;
                    for (Food f : foodList) {
                        if (f.getName().equals(food.getName())) {
                            foodToUpdate = f;
                            break;
                        }
                    }
                    if (foodToUpdate != null) {
                        foodList.remove(foodToUpdate);
                    }
                    Log.d("FoodEditingSecond", "onClick: " + food.getName());
                    Log.d("FoodEditingSecond", "onClick: " + food.getNutritionPerUnit().getKcal());
                    foodList.add(food);
                    globalApplication.setForAdminFoodList(foodList);
                    Toast.makeText(context, "Chỉnh sửa món ăn thành công", Toast.LENGTH_SHORT).show();
                    // pop back to adminFoodSetting
                    Navigation.findNavController(view).popBackStack(R.id.adminFoodList, false);
                }
            }
        });




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