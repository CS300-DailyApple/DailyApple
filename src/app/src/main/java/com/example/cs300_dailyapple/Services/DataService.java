package com.example.cs300_dailyapple.Services;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DataService {
    private final String TAG = "DataService";
    private static final String USERS_COLLECTION = "users";
    private static final String SHARED_FOODS_COLLECTION = "shared_foods";
    private static final String CUSTOM_FOODS_COLLECTION = "custom_foods";
    private static final String USER_CUSTOM_FOODS_COLLECTION = "user_custom_foods";

    // Singleton
    private static DataService instance = null;
    private final FirebaseFirestore db;
    private DataService() {
        db = FirebaseFirestore.getInstance();
    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    public void addUser(String uid, String email, String username, PersonalInformation PI) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("username", username);
        user.put("role", "user");
        user.put("credit_points", 100);
        user.put("is_banned", false);
        user.put("personal_information", PI);
        db.collection(USERS_COLLECTION).document(uid).set(user);
    }
    public String getUserRole(String uid) {
        Task<DocumentSnapshot> query = db.collection(USERS_COLLECTION).document(uid).get();
        while (!query.isComplete()) {}
        return query.getResult().getString("role");
    }
    public boolean isFoodExist(String foodName) {
        // Find food in shared foods
        Task<QuerySnapshot> query = db.collection(SHARED_FOODS_COLLECTION).whereEqualTo("name", foodName).get();
        if (query.getResult().size() > 0) {
            return true;
        }
        // Find food in custom foods
        Task<QuerySnapshot> query2 = db.collection(CUSTOM_FOODS_COLLECTION).whereEqualTo("name", foodName).get();
        if (query2.getResult().size() > 0) {
            return true;
        }
        return false;
    }
    public void addSharedFood(Food food) {
        Map<String, Object> food_to_add = new HashMap<>();
        food_to_add.put("name", food.getName());
        food_to_add.put("unit", '1' + food.getUnit());
        food_to_add.put("energy", food.getNutritionPerUnit().getCalories());
        food_to_add.put("protein", food.getNutritionPerUnit().getProtein());
        food_to_add.put("fiber", food.getNutritionPerUnit().getFibre());
        food_to_add.put("fat", food.getNutritionPerUnit().getFat());
        food_to_add.put("carb", food.getNutritionPerUnit().getCarbs());
        db.collection(SHARED_FOODS_COLLECTION).add(food_to_add);
    }

    public ArrayList<Food> getSharedFoods() {
        ArrayList<Food> foods = new ArrayList<>();
        Task<QuerySnapshot> query = db.collection(SHARED_FOODS_COLLECTION).get();
        while (!query.isComplete()) {}
        for (DocumentSnapshot document : query.getResult()) {
            Food food = new Food();
            food.setName(document.getString("name"));
            food.setUnit(document.getString("unit").substring(1));
            food.getNutritionPerUnit().setCalories(document.getDouble("energy"));
            food.getNutritionPerUnit().setProtein(document.getDouble("protein"));
            food.getNutritionPerUnit().setFibre(document.getDouble("fiber"));
            food.getNutritionPerUnit().setFat(document.getDouble("fat"));
            food.getNutritionPerUnit().setCarbs(document.getDouble("carb"));
            foods.add(food);
        }
        return foods;
    }
}
