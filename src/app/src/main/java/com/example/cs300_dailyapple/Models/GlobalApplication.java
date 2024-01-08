package com.example.cs300_dailyapple.Models;

import android.app.Application;

public class GlobalApplication extends Application {
    private User user;

    String currentMealChoosing = "";


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
