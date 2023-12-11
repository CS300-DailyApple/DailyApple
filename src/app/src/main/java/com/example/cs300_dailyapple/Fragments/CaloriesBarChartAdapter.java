package com.example.cs300_dailyapple.Fragments;

import android.content.Context;

import com.example.cs300_dailyapple.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class CaloriesBarChartAdapter {
    private Context context;
    private BarChart caloriesBarChart;

    public CaloriesBarChartAdapter(Context context, BarChart caloriesBarChart) {
        this.context = context;
        this.caloriesBarChart = caloriesBarChart;
    }

    public void setData(List<Integer> caloriesConsumptionList, List<String> dateList) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < caloriesConsumptionList.size(); i++) {
            entries.add(new BarEntry(i, caloriesConsumptionList.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Calories Consumption");
        dataSet.setColor(context.getResources().getColor(R.color.column)); // Set color

        BarData barData = new BarData(dataSet);
        caloriesBarChart.setData(barData);

        // Customize chart appearance
        caloriesBarChart.getDescription().setEnabled(false);
        caloriesBarChart.setPinchZoom(false);
        caloriesBarChart.setScaleEnabled(false);
        caloriesBarChart.setDrawBarShadow(false);

        XAxis xAxis = caloriesBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dateList));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);

        YAxis yAxisRight = caloriesBarChart.getAxisRight();
        yAxisRight.setEnabled(false);

        caloriesBarChart.invalidate(); // Refresh chart
    }
}

