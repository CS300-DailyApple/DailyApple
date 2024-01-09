package com.example.cs300_dailyapple.Models;
import android.content.Context;

import java.util.ArrayList;

public class PersonalInformation {
    // Attributes
    private String gender;
    private int age;
    private ArrayList<BodyInformation> historyPI;
    // Methods
    public String getGender() {
        return this.gender;
    }

    public int getAge() {
        return this.age;
    }

    public Double getHeight() {
        BodyInformation BI = this.historyPI.get(0);
        return BI.getHeight();
    }
    public Double getWeight() {
        BodyInformation BI = this.historyPI.get(0);
        return BI.getWeight();
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(Double height) {
        BodyInformation BI = this.historyPI.get(0);
        BI.setHeight(height);
    }

    public void setWeight(Double weight) {
        BodyInformation BI = this.historyPI.get(0);
        BI.setWeight(weight);
    }
    public ArrayList<BodyInformation> getHistoryPI() {
        return this.historyPI;
    }
    public Double calculateBMI(){
        return getWeight()/((getHeight()/100)*(getHeight()/100));
    }
    public Double calculateTDEE() {
        if (this.gender == "male") {
            return (88.362 + 13.397 * getWeight() + 4.799 * getHeight() - 5.677 * this.age) * 1.375;
        } else {
            return (447.593 + 9.247 * getWeight() + 3.098 * getHeight() - 4.330 * this.age) * 1.375;
        }
    }
    public Double calculateProtein() {
        return getWeight() * 2.2;
    }
    public Double calculateFibre() {
        return getWeight() * 14;
    }
    public Double calculateFat() {
        return this.calculateTDEE() * 0.25 / 9;
    }
    public Double calculateCarbs() {
        return this.calculateTDEE() * 0.5 / 4;
    }
    // Return the amount of water in ml
    public Double calculateWater() {
        return getWeight() * 0.033 * 1000;
    }

    public void addNewBodyInformation(Double weight, Double height) {
        historyPI.add(0, new BodyInformation(height, weight));
    }
}
