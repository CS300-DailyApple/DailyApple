package com.example.cs300_dailyapple.Models;

import android.content.Context;

import java.util.LinkedList;

public class Food {
    // Attributes
    private String name;
    private String unit;
    private int numberOfUnits;
    private Nutrition nutritionPerUnit;
    // Methods
    public Food(String name, String unit, int numberOfUnits, Nutrition nutritionPerUnit) {
        this.name = name;
        this.unit = unit;
        this.numberOfUnits = numberOfUnits;
        this.nutritionPerUnit = nutritionPerUnit;
    }
    public String getName() {
        return name;
    }
    public String getUnit() {
        return unit;
    }
    public int getNumberOfUnits() {
        return numberOfUnits;
    }
    public Nutrition getNutritionPerUnit() {
        return nutritionPerUnit;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }
    public void setNutritionPerUnit(Nutrition nutritionPerUnit) {
        this.nutritionPerUnit = nutritionPerUnit;
    }
    public void updateFood(Food food) {
        this.name = food.getName();
        this.unit = food.getUnit();
        this.numberOfUnits = food.getNumberOfUnits();
        this.nutritionPerUnit = food.getNutritionPerUnit();
    }
    public Nutrition getNutrition() {
        return new Nutrition(
                this.nutritionPerUnit.getCalories() * this.numberOfUnits,
                this.nutritionPerUnit.getProtein() * this.numberOfUnits,
                this.nutritionPerUnit.getFibre() * this.numberOfUnits,
                this.nutritionPerUnit.getFat() * this.numberOfUnits,
                this.nutritionPerUnit.getCarbs() * this.numberOfUnits
        );
    }
    public static LinkedList<Food> loadFoodList(Context context){
        LinkedList<Food> foodList = new LinkedList<>();
        // load Foodlist here
        return foodList;
    }
    public static void saveFoodList(LinkedList<Food> foodList,Context context){

    }
}
