package com.example.cs300_dailyapple.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs300_dailyapple.R;


public class SettingDishFragment extends Fragment {

    TextView addFood;
    TextView contributeFood;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting_dish, container, false);
        addFood= view.findViewById(R.id.AddFood);
        SpannableString addFoodString = new SpannableString("Thêm món ăn");
        addFoodString.setSpan(new StyleSpan(Typeface.BOLD), 0, addFoodString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        addFoodString.setSpan(new UnderlineSpan(), 0, addFoodString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        addFood.setText(addFoodString);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here to navigate
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
            }
        });
        return view;
    }
}