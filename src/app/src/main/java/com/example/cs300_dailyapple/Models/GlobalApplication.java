package com.example.cs300_dailyapple.Models;

import android.app.Application;
import android.provider.ContactsContract;

import com.example.cs300_dailyapple.Services.DataService;

import java.util.LinkedList;
import java.util.Map;

public class GlobalApplication extends Application {
    private User user;

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

    @Override
    public void onTerminate() {
        super.onTerminate();
        DataService dataService = DataService.getInstance();
        dataService.saveUser(user);
        dataService.saveUserCustomList(userCustomList);
        dataService.saveUserSuggestedFoodList(userSuggestedFoodList);
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

    public void setUserSuggestedFoodList() {
        this.userSuggestedFoodList = DataService.getInstance().getSuggestedFood();
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
}
