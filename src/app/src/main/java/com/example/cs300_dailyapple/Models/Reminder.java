package com.example.cs300_dailyapple.Models;

import android.util.Pair;

import java.time.LocalTime;
import java.util.ArrayList;

public class Reminder {
    // Attributes
    private ArrayList<Pair<String, LocalTime>> listOfNotices;
    // Methods
    public Reminder() {
        this.listOfNotices = new ArrayList<Pair<String, LocalTime>>();
    }
    public Reminder(ArrayList<Pair<String, LocalTime>> listOfNotices) {
        this.listOfNotices = listOfNotices;
    }
    public ArrayList<Pair<String, LocalTime>> getListOfNotices() {
        return listOfNotices;
    }
    public void addNotice(String notice, LocalTime time) {
        this.listOfNotices.add(new Pair<String, LocalTime>(notice, time));
    }
    public void removeNotice(String notice, LocalTime time) {
        this.listOfNotices.remove(new Pair<String, LocalTime>(notice, time));
    }
    public void editNotice(String notice, LocalTime time, String newNotice, LocalTime newTime) {
        this.listOfNotices.remove(new Pair<String, LocalTime>(notice, time));
        this.listOfNotices.add(new Pair<String, LocalTime>(newNotice, newTime));
    }
    public void notifying() {
        // TODO: 03-Dec-23
    }
    public void update() {
        // TODO: 03-Dec-23
    }
}
