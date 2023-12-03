package com.example.cs300_dailyapple.Models;

import java.util.ArrayList;

public class WaterInformation {
    // Attributes
    private int waterTarget;
    private int totalWaterDrank;
    private int containerCapacity;
    private ArrayList<WaterHistoryItem> waterHistory;

    // Methods
    public WaterInformation(int waterTarget, int totalWaterDrank, int containerCapacity, ArrayList<WaterHistoryItem> waterHistory) {
        this.waterTarget = waterTarget;
        this.totalWaterDrank = totalWaterDrank;
        this.containerCapacity = containerCapacity;
        this.waterHistory = waterHistory;
    }

    public int getWaterTarget() {
        return waterTarget;
    }

    public int getTotalWaterDrank() {
        return totalWaterDrank;
    }

    public int getContainerCapacity() {
        return containerCapacity;
    }

    public ArrayList<WaterHistoryItem> getWaterHistory() {
        return waterHistory;
    }

    public void setWaterTarget(int waterTarget) {
        this.waterTarget = waterTarget;
    }

    public void setTotalWaterDrank(int totalWaterDrank) {
        this.totalWaterDrank = totalWaterDrank;
    }

    public void setContainerCapacity(int containerCapacity) {
        this.containerCapacity = containerCapacity;
    }

    public void setWaterHistory(ArrayList<WaterHistoryItem> waterHistory) {
        this.waterHistory = waterHistory;
    }

    public void addWaterHistoryItem(WaterHistoryItem waterHistoryItem) {
        waterHistory.add(waterHistoryItem);
    }
}
