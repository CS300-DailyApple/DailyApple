package com.example.cs300_dailyapple.Models;

import java.util.ArrayList;

public class NutritionOverall {
    // Attributes
    private Nutrition nutritionTarget;
    private Nutrition nutritionAbsorbed;
    private DailyMeal mealHistory;

    // Methods
    public NutritionOverall() {
        this.nutritionTarget = new Nutrition();
        this.nutritionAbsorbed = new Nutrition();
        this.mealHistory = new DailyMeal();
    }
    public NutritionOverall(Nutrition nutritionTarget, Nutrition nutritionAbsorbed, DailyMeal mealHistory) {
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

    public DailyMeal getMealHistory() {
        return mealHistory;
    }

    public void setNutritionTarget(Nutrition nutritionTarget) {
        this.nutritionTarget = nutritionTarget;
    }

    public void setNutritionAbsorbed(Nutrition nutritionAbsorbed) {
        this.nutritionAbsorbed = nutritionAbsorbed;
    }

    public void setMealHistory(DailyMeal mealHistory) {
        this.mealHistory = mealHistory;
    }

    public void updateNutritionAbsorbed() {
        nutritionAbsorbed.setKcal(mealHistory.getTotalCalories());
        nutritionAbsorbed.setFat(mealHistory.getTotalFat());
        nutritionAbsorbed.setCarbs(mealHistory.getTotalCarbs());
        nutritionAbsorbed.setProtein(mealHistory.getTotalProtein());
        nutritionAbsorbed.setFiber(mealHistory.getTotalFiber());
    }

    public void addFood(String currentMealChoosing, Food food) {
        mealHistory.addFood(currentMealChoosing, food);
    }

    public void setMeal(String currentMealChoosing, FoodCompound meal) {
        if (currentMealChoosing.equals("breakfast")){
            mealHistory.addBreakfast(meal);
        }
        else if (currentMealChoosing.equals("lunch")){
            mealHistory.addLunch(meal);
        }
        else if (currentMealChoosing.equals("dinner")){
            mealHistory.addDinner(meal);
        }
        else{
            mealHistory.addSnack(meal);
        }
    }
}
