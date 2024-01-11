package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import com.example.cs300_dailyapple.R;
import com.github.mikephil.charting.charts.BarChart;


public class ChartFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_chart, container, false);
        Context context= this.getContext();
        BarChart caloriesBarChart = view.findViewById(R.id.caloriesBarChart);
        // Add the list here to display
        List<Integer> caloriesConsumptionList = new ArrayList<>();

        List<String> dateListCalo = new ArrayList<>();
        CaloriesBarChartAdapter caloriesBarChartAdapter = new CaloriesBarChartAdapter(context, caloriesBarChart);
        caloriesBarChartAdapter.setData(caloriesConsumptionList, dateListCalo);

        BarChart waterBarChart = view.findViewById(R.id.waterBarChart);

        // Add the list here to display
        List<Integer> waterConsumptionList = new ArrayList<>();


        List<String> dateListWater = new ArrayList<>();


        WaterBarChartAdapter barChartAdapter = new WaterBarChartAdapter(context, waterBarChart);
        barChartAdapter.setData(waterConsumptionList, dateListWater);
        return view;
    }
}