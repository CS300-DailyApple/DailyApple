package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.DataService;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class AdminFoodDetail extends Fragment {
    private TextView adminFoodDetailName;
    private ImageView adminFoodDetailImage;
    private TextView adminFoodDetailAmount;
    private TextView adminFoodDetailKcal;
    private TextView adminFoodDetailProtein;
    private TextView adminFoodDetailFat;
    private TextView adminFoodDetailCarbs;
    private PieChart adminFoodDetailPieChart;
    private AppCompatButton adminFoodDetailEditBtn;

    public AdminFoodDetail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_food_detail, container, false);

        adminFoodDetailName = view.findViewById(R.id.AdminFoodDetailName);
        adminFoodDetailImage = view.findViewById(R.id.AdminFoodDetailImage);
        adminFoodDetailAmount = view.findViewById(R.id.AdminFoodDetailNutritionTitle2);
        adminFoodDetailKcal = view.findViewById(R.id.AdminFoodDetailCalories);
        adminFoodDetailProtein = view.findViewById(R.id.AdminFoodDetailProtein);
        adminFoodDetailFat = view.findViewById(R.id.AdminFoodDetailFat);
        adminFoodDetailCarbs = view.findViewById(R.id.AdminFoodDetailCarbs);
        adminFoodDetailPieChart = view.findViewById(R.id.NutritionPieChart);
        adminFoodDetailEditBtn = view.findViewById(R.id.AdminFoodDetailEdit);

        // get data from bundle
        Bundle bundle = getArguments();
        String foodId = bundle.getString("foodId");
        Food food = DataService.getInstance().getFoodById(foodId);

        // set data
        adminFoodDetailName.setText(food.getName());
        adminFoodDetailImage.setImageResource(R.drawable.food_photo_placeholder);
        adminFoodDetailAmount.setText("Khẩu phần " + food.getAmount());
        adminFoodDetailKcal.setText(food.getKcal() + " kcals");

        // set pie chart
        Long protein = food.getProtein();
        Long fat = food.getFat();
        Long carbs = food.getCarbs();
        adminFoodDetailFat.setText(fat + "g");
        adminFoodDetailProtein.setText(protein + "g");
        adminFoodDetailCarbs.setText(carbs + "g");

        adminFoodDetailPieChart.clearChart();
        adminFoodDetailPieChart.addPieSlice(new PieModel("Protein", protein, getResources().getColor(R.color.protein)));
        adminFoodDetailPieChart.addPieSlice(new PieModel("Fat", fat, getResources().getColor(R.color.fat)));
        adminFoodDetailPieChart.addPieSlice(new PieModel("Carbs", carbs, getResources().getColor(R.color.carbs)));

        return view;
    }
}
