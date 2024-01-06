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
        Double breakfastCalories = breakfast == null ? 0.0 : breakfast.getNutrition().getCalories();
        Double lunchCalories = lunch == null ? 0.0 : lunch.getNutrition().getCalories();
        Double dinnerCalories = dinner == null ? 0.0 : dinner.getNutrition().getCalories();
        Double snackCalories = snack == null ? 0.0 : snack.getNutrition().getCalories();
        totalCalories = breakfastCalories + lunchCalories + dinnerCalories + snackCalories;
        return totalCalories;
    }
}
