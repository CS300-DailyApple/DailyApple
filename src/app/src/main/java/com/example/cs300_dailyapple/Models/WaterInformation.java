package com.example.cs300_dailyapple.Models;

import android.content.Context;

import com.example.cs300_dailyapple.Services.NotificationScheduler;
import com.google.firebase.Timestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class WaterInformation {
    // Attributes
    private int waterTarget;
    private int containerCapacity;
    private ArrayList<WaterHistoryItem> saveTotalDrinkingPerDay;
    private ArrayList<WaterHistoryItem> waterHistory;
    LocalDateTime dl;
    Context context = null;
    private int cooldown = 60; //in minutes

    public WaterInformation() {
        this.waterTarget = 1000;
        this.containerCapacity = 100;
        this.dl = LocalDateTime.now();
    }
    // Methods
    public WaterInformation(int waterTarget, int containerCapacity, ArrayList<WaterHistoryItem> waterHistory) {
        this.waterTarget = waterTarget;
        this.containerCapacity = containerCapacity;
        this.waterHistory = waterHistory;
        this.dl = LocalDateTime.now();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getWaterTarget() {
        return this.waterTarget;
    }

    public int getTotalWaterDrank() {
        int totalWaterDrank = 0;
        for (WaterHistoryItem v: waterHistory) {
            totalWaterDrank += v.getWaterAmount();
        }

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

    public void setContainerCapacity(int containerCapacity) {
        this.containerCapacity = containerCapacity;
    }

    public void setWaterHistory(ArrayList<WaterHistoryItem> waterHistory) {
        this.waterHistory = waterHistory;
    }

    public void addWaterHistoryItem(WaterHistoryItem waterHistoryItem) {
        waterHistory.add(0, waterHistoryItem);
        resetDeadline();
    }

    private void resetDeadline() {
        this.dl = LocalDateTime.now();
        this.dl.plusMinutes(this.cooldown);
    }

    public String getCooldownString() {
        LocalDateTime current = LocalDateTime.now();
        if (this.dl.isBefore(current))
            return "00:00";
        return ChronoUnit.HOURS.between(current, this.dl) + ":" + ChronoUnit.MINUTES.between(current, this.dl);
    }

    public void addWaterHistoryItem() {
        //default
        waterHistory.add(0, new WaterHistoryItem(containerCapacity, Timestamp.now()));
        resetDeadline();
        setNotification();
    }

    private void setNotification() {
        //set new notf
        NotificationScheduler.scheduleNotification(this.context, "Uống nước", "Đã đến giờ uống nước!", this.cooldown);
    }
}
