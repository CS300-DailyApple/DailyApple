package com.example.cs300_dailyapple.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.R;


public class SettingDishFragment extends Fragment {

    TextView addFood;
    TextView contributeFood;
    TextView resetFoodList;

    NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addFood= view.findViewById(R.id.AddFood);
        navController = Navigation.findNavController(view);
        SpannableString addFoodString = new SpannableString("Thêm món ăn");
        addFoodString.setSpan(new StyleSpan(Typeface.BOLD), 0, addFoodString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        addFoodString.setSpan(new UnderlineSpan(), 0, addFoodString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        addFood.setText(addFoodString);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here to navigate
                navController.navigate(R.id.action_settingDishFragment_to_addDishFragment);
            }
        });
        contributeFood= view.findViewById(R.id.ContributeFood);
        SpannableString contributeFoodString = new SpannableString("Đóng góp món ăn");
        contributeFoodString.setSpan(new StyleSpan(Typeface.BOLD), 0, contributeFoodString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        contributeFoodString.setSpan(new UnderlineSpan(), 0, contributeFoodString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        contributeFood.setText(contributeFoodString);
        contributeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here to navigate
                navController.navigate(R.id.action_settingDishFragment_to_dishContributeFragment);
            }
        });
        resetFoodList= view.findViewById(R.id.ResetFoodList);
        SpannableString resetFoodListString = new SpannableString("Khôi phục danh sách món ăn");
        resetFoodListString.setSpan(new StyleSpan(Typeface.BOLD), 0, resetFoodListString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        resetFoodListString.setSpan(new UnderlineSpan(), 0, resetFoodListString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        resetFoodList.setText(resetFoodListString);
        resetFoodList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here to navigate
                GlobalApplication globalApplication = (GlobalApplication) GlobalApplication.getInstance();
                globalApplication.resetFoodList();
                navController.navigate(R.id.action_settingDishFragment_to_foodFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_dish, container, false);
        return view;
    }
}