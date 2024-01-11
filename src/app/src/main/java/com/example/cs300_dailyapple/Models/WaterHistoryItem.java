package com.example.cs300_dailyapple.Models;

import com.google.firebase.Timestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

public class WaterHistoryItem {
    // Attributes
    private int waterAmount;
    private Timestamp time;

    // Methods
    public WaterHistoryItem(int waterAmount, Timestamp time) {
        this.waterAmount = waterAmount;
        this.time = time;
    }

    public int getWaterAmount() {
        return this.waterAmount;
    }

    public String get_hm() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime localDateTime = time.toDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(df);
    }

    public Timestamp getTime() {
        return this.time;
    }
}
