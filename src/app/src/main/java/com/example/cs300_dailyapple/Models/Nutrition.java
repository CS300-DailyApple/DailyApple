package com.example.cs300_dailyapple.Models;

public class Nutrition {
    // Attributes
    private Double kcal;
    private Double protein;
    private Double fiber;
    private Double fat;
    private Double carbs;
    // Methods
    public Nutrition() {
        this.kcal = 0.0;
        this.protein = 0.0;
        this.fiber = 0.0;
        this.fat = 0.0;
        this.carbs = 0.0;
    }
    public Nutrition(Double kcal, Double protein, Double fibre, Double fat, Double carbs) {
        this.kcal = kcal;
        this.protein = protein;
        this.fiber = fibre;
        this.fat = fat;
        this.carbs = carbs;
    }
    public Double getKcal() {
        return kcal;
    }
    public Double getProtein() {
        return protein;
    }
    public Double getFiber() {
        return fiber;
    }
    public Double getFat() {
        return fat;
    }
    public Double getCarbs() {
        return carbs;
    }
    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }
    public void setProtein(Double protein) {
        this.protein = protein;
    }
    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }
    public void setFat(Double fat) {
        this.fat = fat;
    }
    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }
    public void updateNutrition(Nutrition nutrition) {
        this.kcal = nutrition.getKcal();
        this.protein = nutrition.getProtein();
        this.fiber = nutrition.getFiber();
        this.fat = nutrition.getFat();
        this.carbs = nutrition.getCarbs();
    }
}
