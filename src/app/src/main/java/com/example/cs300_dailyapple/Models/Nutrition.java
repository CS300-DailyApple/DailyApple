package com.example.cs300_dailyapple.Models;

public class Nutrition {
    // Attributes
    private int calories;
    private float protein;
    private float fibre;
    private float fat;
    private float carbs;
    // Methods
    public Nutrition(int calories, float protein, float fibre, float fat, float carbs) {
        this.calories = calories;
        this.protein = protein;
        this.fibre = fibre;
        this.fat = fat;
        this.carbs = carbs;
    }
    public int getCalories() {
        return calories;
    }
    public float getProtein() {
        return protein;
    }
    public float getFibre() {
        return fibre;
    }
    public float getFat() {
        return fat;
    }
    public float getCarbs() {
        return carbs;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    public void setProtein(float protein) {
        this.protein = protein;
    }
    public void setFibre(float fibre) {
        this.fibre = fibre;
    }
    public void setFat(float fat) {
        this.fat = fat;
    }
    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }
    public void updateNutrition(Nutrition nutrition) {
        this.calories = nutrition.getCalories();
        this.protein = nutrition.getProtein();
        this.fibre = nutrition.getFibre();
        this.fat = nutrition.getFat();
        this.carbs = nutrition.getCarbs();
    }
}
