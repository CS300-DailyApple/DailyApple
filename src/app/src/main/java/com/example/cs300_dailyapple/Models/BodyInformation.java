package com.example.cs300_dailyapple.Models;

import com.google.firebase.Timestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BodyInformation {
    private Timestamp time;
    private Double height;
    private Double weight;

    public BodyInformation(Double height, Double weight) {
        this.height = height;
        this.weight = weight;
        this.time = Timestamp.now();
    }

    public Double getHeight() {
        return this.height;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getTime() {
        String res;
        LocalDateTime localDateTime = this.time.toDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        res = localDateTime.getDayOfMonth() + " tháng " + localDateTime.getMonth() + ", " + localDateTime.getYear();
        return res;
    }
}
