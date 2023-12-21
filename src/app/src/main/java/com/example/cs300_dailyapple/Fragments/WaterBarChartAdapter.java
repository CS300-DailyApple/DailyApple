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

public class WaterBarChartAdapter {
    private Context context;
    private BarChart waterBarChart;

    public WaterBarChartAdapter(Context context, BarChart waterBarChart) {
        this.context = context;
        this.waterBarChart = waterBarChart;
    }

    public void setData(List<Integer> waterConsumptionList, List<String> dateList) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < waterConsumptionList.size(); i++) {
            entries.add(new BarEntry(i, waterConsumptionList.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Water Consumption");
        dataSet.setColor(context.getResources().getColor(R.color.column)); // Set color

        BarData barData = new BarData(dataSet);
        waterBarChart.setData(barData);

        // Customize chart appearance
        waterBarChart.getDescription().setEnabled(false);
        waterBarChart.setPinchZoom(false);
        waterBarChart.setScaleEnabled(false);
        waterBarChart.setDrawBarShadow(false);

        XAxis xAxis = waterBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dateList));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);

        YAxis yAxisRight = waterBarChart.getAxisRight();
        yAxisRight.setEnabled(false);

        waterBarChart.invalidate(); // Refresh chart
    }
}

