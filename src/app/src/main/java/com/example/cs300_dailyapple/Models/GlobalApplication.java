package com.example.cs300_dailyapple.Models;

import android.app.Application;
import android.provider.ContactsContract;

import com.example.cs300_dailyapple.Services.DataService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class GlobalApplication extends Application {
    private User user;
    private Food currentFoodChoosing;

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Application getInstance(){
        return instance;
    }
    String currentMealChoosing = "";
    LinkedList<Food> foodList, userCustomList, userSuggestedFoodList;
    ArrayList<User> forAdminUserList;
    LinkedList<Food> forAdminFoodList;
    LinkedList<SuggestedFood> forAdminSuggestedFoodList;

    private Food customDish;

    private Food contributeDish;
    public Food getCustomDish() {
        return customDish;
    }

    public void setCustomDish(Food customDish) {
        this.customDish = customDish;
    }

    public Food getContributeDish() {
        return contributeDish;
    }

    public void setContributeDish(Food contributeDish) {
        this.contributeDish = contributeDish;
    }

    public void checkFavorite(String foodName, Boolean b){
        Map<String, Boolean> favorite = user.getFavorite();
        if (b){
            if (!favorite.containsKey(foodName)){
                favorite.put(foodName, true);
            }
        }
        else favorite.remove(foodName);
        user.setFavorite(favorite);
    }
    public LinkedList<Food> getUserSuggestedFoodList() {
        return userSuggestedFoodList;
    }
    public void queryForAdminLists() {
        this.forAdminUserList = DataService.getInstance().searchUsers("");
        this.forAdminFoodList = DataService.getInstance().searchSharedFoods("");
        this.forAdminSuggestedFoodList = DataService.getInstance().getSuggestedFoodList();
    }
    public ArrayList<User> getForAdminUserList() {
        return this.forAdminUserList;
    }
    public LinkedList<Food> getForAdminFoodList() {
        return this.forAdminFoodList;
    }
    public void setForAdminUserList(ArrayList<User> forAdminUserList) {
        this.forAdminUserList = forAdminUserList;
    }
    public void setForAdminFoodList(LinkedList<Food> forAdminFoodList) {
        this.forAdminFoodList = forAdminFoodList;
    }
    public LinkedList<SuggestedFood> getForAdminSuggestedFoodList() {
        return forAdminSuggestedFoodList;
    }
    public void setForAdminSuggestedFoodList(LinkedList<SuggestedFood> forAdminSuggestedFoodList) {
        this.forAdminSuggestedFoodList = forAdminSuggestedFoodList;
    }

    public Food getCurrentFoodChoosing(){
        return currentFoodChoosing;
    }
    public void setCurrentFoodChoosing(Food currentFoodChoosing){
        this.currentFoodChoosing=currentFoodChoosing;
    }
    public LinkedList<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(LinkedList<Food> foodList){
        this.foodList = foodList;
    }

    public void setFoodList() {
        this.foodList = DataService.getInstance().getUserFoodList(user.getFavorite());
    }

    public LinkedList<Food> getUserCustomList() {
        return userCustomList;
    }

    public void setUserCustomList() {
        this.userCustomList = DataService.getInstance().getUserCustomFood();
    }

    public String getCurrentMealChoosing() {
        return currentMealChoosing;
    }

    public void setCurrentMealChoosing(String currentMealChoosing) {
        this.currentMealChoosing = currentMealChoosing;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void resetFoodList() {
        Map <String, Boolean> favorite = user.getFavorite();
        for (Food food: userCustomList){
            favorite.remove(food.getName());
        }
        userCustomList.clear();
        foodList.clear();
        foodList = DataService.getInstance().getSharedFoods();
        for (Food food: foodList){
            if (favorite.containsKey(food.getName())){
                food.setFavorite(true);
            }
        }
        user.setFavorite(favorite);
    }
    public void setMeal(String currentMealChoosing, FoodCompound meal) {
        user.setMeal(currentMealChoosing, meal);
    }

    public void addCustomDish() {
        userCustomList.add(customDish);
        foodList.add(customDish);
    }

    public void addSuggestedDish(){
        userSuggestedFoodList.add(contributeDish);
    }
}
