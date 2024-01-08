package com.example.cs300_dailyapple.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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
        return this.waterAmount;
    }

    public String get_hm() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(df);
    }

    public LocalDateTime getTime() {
        return this.time;
    }
}
