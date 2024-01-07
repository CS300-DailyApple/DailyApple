package com.example.cs300_dailyapple.Models;

import java.util.ArrayList;

public class NutritionOverall {
    // Attributes
    private Nutrition nutritionTarget;
    private Nutrition nutritionAbsorbed;
    private ArrayList<DailyMeal> mealHistory;

    // Methods
    public NutritionOverall() {
        this.nutritionTarget = new Nutrition();
        this.nutritionAbsorbed = new Nutrition();
        this.mealHistory = new ArrayList<DailyMeal>();
    }
    public NutritionOverall(Nutrition nutritionTarget, Nutrition nutritionAbsorbed, ArrayList<DailyMeal> mealHistory) {
        this.nutritionTarget = nutritionTarget;
        this.nutritionAbsorbed = nutritionAbsorbed;
        this.mealHistory = mealHistory;
    }

    public Nutrition getNutritionTarget() {
        return nutritionTarget;
    }

    public Nutrition getNutritionAbsorbed() {
        return nutritionAbsorbed;
    }

    public ArrayList<DailyMeal> getMealHistory() {
        return mealHistory;
    }

    public void setNutritionTarget(Nutrition nutritionTarget) {
        this.nutritionTarget = nutritionTarget;
    }

    public void setNutritionAbsorbed(Nutrition nutritionAbsorbed) {
        this.nutritionAbsorbed = nutritionAbsorbed;
    }

    public void setMealHistory(ArrayList<DailyMeal> mealHistory) {
        this.mealHistory = mealHistory;
    }
}
