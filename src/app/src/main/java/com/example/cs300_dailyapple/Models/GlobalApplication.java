package com.example.cs300_dailyapple.Models;

import android.app.Application;
import android.provider.ContactsContract;

import com.example.cs300_dailyapple.Services.DataService;

import java.util.ArrayList;
import java.util.LinkedList;

public class GlobalApplication extends Application {
    private User user;
    String currentMealChoosing = "";
    LinkedList<Food> foodList, userCustomList, userSuggestedFoodList;
    ArrayList<User> forAdminUserList;
    LinkedList<Food> forAdminFoodList;
    LinkedList<SuggestedFood> forAdminSuggestedFoodList;

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
    public LinkedList<Food> getFoodList() {
        return foodList;
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
