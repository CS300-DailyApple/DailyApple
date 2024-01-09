package com.example.cs300_dailyapple.Models;

import java.time.LocalDateTime;

public class DailyMeal {
    // Attributes
    private FoodCompound breakfast;
    private FoodCompound lunch;
    private FoodCompound dinner;
    private FoodCompound snack;
    private LocalDateTime date;

    // Methods
    public DailyMeal() {
        breakfast = new FoodCompound();
        lunch = new FoodCompound();
        dinner = new FoodCompound();
        snack = new FoodCompound();
        date = LocalDateTime.now();
    }
    public DailyMeal(FoodCompound breakfast, FoodCompound lunch, FoodCompound dinner, FoodCompound snack, LocalDateTime date) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.snack = snack;
        this.date = date;
    }

    public void addBreakfast(FoodCompound breakfast) {
        this.breakfast = breakfast;
    }

    public void addLunch(FoodCompound lunch) {
        this.lunch = lunch;
    }

    public void addDinner(FoodCompound dinner) {
        this.dinner = dinner;
    }

    public void addSnack(FoodCompound snack) {
        this.snack = snack;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public FoodCompound getBreakfast() {
        return breakfast;
    }

    public FoodCompound getLunch() {
        return lunch;
    }

    public FoodCompound getDinner() {
        return dinner;
    }

    public FoodCompound getSnack() {
        return snack;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Double getTotalCalories() {
        Double totalCalories = 0.0;
        Double breakfastCalories = breakfast == null ? 0.0 : breakfast.getNutrition().getKcal();
        Double lunchCalories = lunch == null ? 0.0 : lunch.getNutrition().getKcal();
        Double dinnerCalories = dinner == null ? 0.0 : dinner.getNutrition().getKcal();
        Double snackCalories = snack == null ? 0.0 : snack.getNutrition().getKcal();
        totalCalories = breakfastCalories + lunchCalories + dinnerCalories + snackCalories;
        return totalCalories;
    }

    public Double getTotalProtein() {
        Double totalProtein = 0.0;
        Double breakfastProtein = breakfast == null ? 0.0 : breakfast.getNutrition().getProtein();
        Double lunchProtein = lunch == null ? 0.0 : lunch.getNutrition().getProtein();
        Double dinnerProtein = dinner == null ? 0.0 : dinner.getNutrition().getProtein();
        Double snackProtein = snack == null ? 0.0 : snack.getNutrition().getProtein();
        totalProtein = breakfastProtein + lunchProtein + dinnerProtein + snackProtein;
        return totalProtein;
    }

    public Double getTotalCarbs() {
        Double totalCarbs = 0.0;
        Double breakfastCarbs = breakfast == null ? 0.0 : breakfast.getNutrition().getCarbs();
        Double lunchCarbs = lunch == null ? 0.0 : lunch.getNutrition().getCarbs();
        Double dinnerCarbs = dinner == null ? 0.0 : dinner.getNutrition().getCarbs();
        Double snackCarbs = snack == null ? 0.0 : snack.getNutrition().getCarbs();
        totalCarbs = breakfastCarbs + lunchCarbs + dinnerCarbs + snackCarbs;
        return totalCarbs;
    }

    public Double getTotalFat() {
        Double totalFat = 0.0;
        Double breakfastFat = breakfast == null ? 0.0 : breakfast.getNutrition().getFat();
        Double lunchFat = lunch == null ? 0.0 : lunch.getNutrition().getFat();
        Double dinnerFat = dinner == null ? 0.0 : dinner.getNutrition().getFat();
        Double snackFat = snack == null ? 0.0 : snack.getNutrition().getFat();
        totalFat = breakfastFat + lunchFat + dinnerFat + snackFat;
        return totalFat;
    }

    public Double getTotalFiber() {
        Double totalFiber = 0.0;
        Double breakfastFiber = breakfast == null ? 0.0 : breakfast.getNutrition().getFiber();
        Double lunchFiber = lunch == null ? 0.0 : lunch.getNutrition().getFiber();
        Double dinnerFiber = dinner == null ? 0.0 : dinner.getNutrition().getFiber();
        Double snackFiber = snack == null ? 0.0 : snack.getNutrition().getFiber();
        totalFiber = breakfastFiber + lunchFiber + dinnerFiber + snackFiber;
        return totalFiber;
    }

    public void addFood(String currentMealChoosing, Food food) {
        if (currentMealChoosing.equals("breakfast")){
            breakfast.addFood(food);
        }
        else if (currentMealChoosing.equals("lunch")){
            lunch.addFood(food);
        }
        else if (currentMealChoosing.equals("dinner")){
            dinner.addFood(food);
        }
        else{
            snack.addFood(food);
        }
    }
}
