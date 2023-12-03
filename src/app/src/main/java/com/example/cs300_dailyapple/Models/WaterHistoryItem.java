package com.example.cs300_dailyapple.Models;

import java.time.LocalDateTime;
import java.util.Date;

public class WaterHistoryItem {
    // Attributes
    private int waterAmount;
    private LocalDateTime time;

    // Methods
    public WaterHistoryItem(int waterAmount, LocalDateTime time) {
        this.waterAmount = waterAmount;
        this.time = time;
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
