package com.example.cs300_dailyapple.Models;

import java.util.ArrayList;

public class FoodCompound {
    // Attributes
    private ArrayList<Food> foodsList;
    private Nutrition nutrition;
    // Methods
    public FoodCompound() {
        this.foodsList = new ArrayList<Food>();
        this.nutrition = new Nutrition();
    }
    public FoodCompound(ArrayList<Food> foodsList) {
        this.foodsList = foodsList;
        this.nutrition = new Nutrition();
        for (Food food : foodsList) {
            this.nutrition.setKcal(this.nutrition.getKcal() + food.getNutrition().getKcal());
            this.nutrition.setProtein(this.nutrition.getProtein() + food.getNutrition().getProtein());
            this.nutrition.setFiber(this.nutrition.getFiber() + food.getNutrition().getFiber());
            this.nutrition.setFat(this.nutrition.getFat() + food.getNutrition().getFat());
            this.nutrition.setCarbs(this.nutrition.getCarbs() + food.getNutrition().getCarbs());
        }
    }
    public ArrayList<Food> getFoodsList() {
        return foodsList;
    }
    public Nutrition getNutrition() {
        return nutrition;
    }
    public void addFood(Food food) {
        this.foodsList.add(food);
        this.nutrition.setKcal(this.nutrition.getKcal() + food.getNutrition().getKcal());
        this.nutrition.setProtein(this.nutrition.getProtein() + food.getNutrition().getProtein());
        this.nutrition.setFiber(this.nutrition.getFiber() + food.getNutrition().getFiber());
        this.nutrition.setFat(this.nutrition.getFat() + food.getNutrition().getFat());
        this.nutrition.setCarbs(this.nutrition.getCarbs() + food.getNutrition().getCarbs());
    }
    public void removeFood(Food food) {
        this.foodsList.remove(food);
        this.nutrition.setKcal(this.nutrition.getKcal() - food.getNutrition().getKcal());
        this.nutrition.setProtein(this.nutrition.getProtein() - food.getNutrition().getProtein());
        this.nutrition.setFiber(this.nutrition.getFiber() - food.getNutrition().getFiber());
        this.nutrition.setFat(this.nutrition.getFat() - food.getNutrition().getFat());
        this.nutrition.setCarbs(this.nutrition.getCarbs() - food.getNutrition().getCarbs());
    }

    public void editFood(Food food, Food newFood) {
        this.foodsList.remove(food);
        this.nutrition.setKcal(this.nutrition.getKcal() - food.getNutrition().getKcal());
        this.nutrition.setProtein(this.nutrition.getProtein() - food.getNutrition().getProtein());
        this.nutrition.setFiber(this.nutrition.getFiber() - food.getNutrition().getFiber());
        this.nutrition.setFat(this.nutrition.getFat() - food.getNutrition().getFat());
        this.nutrition.setCarbs(this.nutrition.getCarbs() - food.getNutrition().getCarbs());
        this.foodsList.add(newFood);
        this.nutrition.setKcal(this.nutrition.getKcal() + newFood.getNutrition().getKcal());
        this.nutrition.setProtein(this.nutrition.getProtein() + newFood.getNutrition().getProtein());
        this.nutrition.setFiber(this.nutrition.getFiber() + newFood.getNutrition().getFiber());
        this.nutrition.setFat(this.nutrition.getFat() + newFood.getNutrition().getFat());
        this.nutrition.setCarbs(this.nutrition.getCarbs() + newFood.getNutrition().getCarbs());
    }
}
