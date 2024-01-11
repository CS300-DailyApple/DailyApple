package com.example.cs300_dailyapple.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs300_dailyapple.Models.DailyMeal;
import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.FoodCompound;
import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;



public class CustomFoodinMealFragment extends Fragment {

    GlobalApplication globalApplication;
    FoodCompound meal;

    Food food;
    TextView foodDetailNameView, foodDetailUnitView, foodDetailCarbsView, foodDetailProteinView, foodDetailFatView;
    ImageView foodDetailImageView, editView;
    PieChart pieChart;

    NavController navController;
    AppCompatButton editFoodButton;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        food = globalApplication.getCurrentFoodChoosing();
        String currentMealChoosing = globalApplication.getCurrentMealChoosing();
        navController = Navigation.findNavController(view);
        // set views
        pieChart = view.findViewById(R.id.NutritionPieChart);
        foodDetailNameView = view.findViewById(R.id.FoodDetailName);
        foodDetailCarbsView = view.findViewById(R.id.FoodDetailCarbs);
        foodDetailFatView = view.findViewById(R.id.FoodDetailFat);
        foodDetailProteinView = view.findViewById(R.id.FoodDetailProtein);
        foodDetailImageView = view.findViewById(R.id.FoodDetailImage);
        foodDetailUnitView = view.findViewById(R.id.NumberOfUnit);
        editView = view.findViewById(R.id.edit_button);
        editFoodButton = view.findViewById(R.id.custom);

        // set data for views
        foodDetailNameView.setText(food.getName());
        updateViewForFood();
        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Carbs", (Long)food.getCarbs(), getResources().getColor(R.color.carbs)));
        pieChart.addPieSlice(new PieModel("Protein", (Long)food.getProtein(), getResources().getColor(R.color.protein)));
        pieChart.addPieSlice(new PieModel("Fat", (Long)food.getFat(), getResources().getColor(R.color.fat)));


        // set on click listener
        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowPopupWindow();
            }
        });
        editFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalApplication.setMealNutrition(currentMealChoosing);
                navController.navigate(R.id.action_customFoodinMealFragment_to_mealFragment);
            }
        });
    }

    private void onShowPopupWindow(){
        View view = View.inflate(this.getContext(), R.layout.popup_window_food_detail, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(view, width, height, false);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
        TextView unitNameView = view.findViewById(R.id.UnitName);
        EditText numberOfUnitsView = view.findViewById(R.id.numberOfUnit);
        AppCompatButton cancelButton = view.findViewById(R.id.cancelButton);
        AppCompatButton applyButton = view.findViewById(R.id.applyButton);
        unitNameView.setText("số khẩu phần ăn(" + food.getUnit() + ") là: ");
        numberOfUnitsView.setEnabled(true);
        numberOfUnitsView.setInputType(InputType.TYPE_CLASS_NUMBER);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String inputText = numberOfUnitsView.getText().toString().trim();
                    int numberOfUnits = Integer.parseInt(inputText);
                    food.setNumberOfUnits(numberOfUnits);
                    popupWindow.dismiss();
                    updateViewForFood();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Cannot apply! Please input a number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void updateViewForFood(){
        foodDetailFatView.setText(String.valueOf(food.getNumberOfUnits()*food.getFat()) + " g");
        foodDetailProteinView.setText(String.valueOf(food.getNumberOfUnits()*food.getProtein()) + " g");
        foodDetailCarbsView.setText(String.valueOf(food.getNumberOfUnits()*food.getCarbs()) + " g");
        foodDetailUnitView.setText("Phần ăn " + food.getNumberOfUnits() + " " + food.getUnit());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalApplication = (GlobalApplication) GlobalApplication.getInstance();
        return inflater.inflate(R.layout.fragment_custom_foodin_meal, container, false);
    }
}