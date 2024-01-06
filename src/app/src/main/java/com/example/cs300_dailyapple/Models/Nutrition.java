package com.example.cs300_dailyapple.Models;

public class Nutrition {
    // Attributes
    private Double calories;
    private Double protein;
    private Double fibre;
    private Double fat;
    private Double carbs;
    // Methods
    public Nutrition() {
        this.calories = 0.0;
        this.protein = 0.0;
        this.fibre = 0.0;
        this.fat = 0.0;
        this.carbs = 0.0;
    }
    public Nutrition(Double calories, Double protein,Double fibre, Double fat, Double carbs) {
        this.calories = calories;
        this.protein = protein;
        this.fibre = fibre;
        this.fat = fat;
        this.carbs = carbs;
    }
    public Double getCalories() {
        return calories;
    }
    public Double getProtein() {
        return protein;
    }
    public Double getFibre() {
        return fibre;
    }
    public Double getFat() {
        return fat;
    }
    public Double getCarbs() {
        return carbs;
    }
    public void setCalories(Double calories) {
        this.calories = calories;
    }
    public void setProtein(Double protein) {
        this.protein = protein;
    }
    public void setFibre(Double fibre) {
        this.fibre = fibre;
    }
    public void setFat(Double fat) {
        this.fat = fat;
    }
    public void setCarbs(Double carbs) {
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
