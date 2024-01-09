package com.example.cs300_dailyapple.Models;

import android.content.Context;

import com.example.cs300_dailyapple.Services.DataService;

import java.util.LinkedList;

public class SuggestedFood {
    // Attributes
    private String name;
    private String unit;
    private int numberOfUnits;
    private Nutrition nutritionPerUnit;
    String contributorId;
    // Methods
    public SuggestedFood() {
        this.name = "";
        this.unit = "";
        this.numberOfUnits = 0;
        this.nutritionPerUnit = new Nutrition();
        this.contributorId = "";
    }
    public SuggestedFood(String name, String unit, int numberOfUnits, Nutrition nutritionPerUnit, String contributorId) {
        this.name = name;
        this.unit = unit;
        this.numberOfUnits = numberOfUnits;
        this.nutritionPerUnit = nutritionPerUnit;
        this.contributorId = contributorId;
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
    //    public boolean getFavorite() { return favorite; }
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
    public String getContributorId(){
        return this.contributorId;
    }
    public void setContributorId(String uid){
        this.contributorId=uid;
    }
    //    public void updateFavorite(boolean favorite){
//        this.favorite=favorite;
//    }
    public Nutrition getNutrition() {
        return new Nutrition(
                this.nutritionPerUnit.getKcal() * this.numberOfUnits,
                this.nutritionPerUnit.getProtein() * this.numberOfUnits,
                this.nutritionPerUnit.getFiber() * this.numberOfUnits,
                this.nutritionPerUnit.getFat() * this.numberOfUnits,
                this.nutritionPerUnit.getCarbs() * this.numberOfUnits
        );
    }
    public static LinkedList<Food> loadFoodList(Context context){
        LinkedList<Food> foodList = new LinkedList<>();
        foodList = DataService.getInstance().getSharedFoods();
        return foodList;
    }
    public static void saveFoodList(LinkedList<Food> foodList,Context context){

    }
    public String getAmount(){
        return String.valueOf(this.numberOfUnits)+" "+this.unit;
    }
    public long getKcal(){
        return Math.round(this.nutritionPerUnit.getKcal()*this.numberOfUnits);
    }
    public long getProtein(){
        return Math.round(this.nutritionPerUnit.getProtein()*this.numberOfUnits);
    }
    public long getFat(){
        return Math.round(this.nutritionPerUnit.getFat()*this.numberOfUnits);
    }
    public long getCarbs(){
        return Math.round(this.nutritionPerUnit.getCarbs()*this.numberOfUnits);
    }
    public String getContributorName() {
        return DataService.getInstance().getUsernameById(this.contributorId);
    }
}
