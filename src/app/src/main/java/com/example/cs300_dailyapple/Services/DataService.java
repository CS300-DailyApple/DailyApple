package com.example.cs300_dailyapple.Services;

import android.util.Log;
import android.widget.Toast;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.Nutrition;
import com.example.cs300_dailyapple.Models.NutritionOverall;
import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.example.cs300_dailyapple.Models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
        user.put("PI", PI);
        // add target and absorbed into nutritionOverall
        Map<String, Object> nutritionOverall = new HashMap<>();
        Map<String, Object> nutritionTarget = new HashMap<>();
        nutritionTarget.put("kcal", PI.calculateTDEE());
        nutritionTarget.put("protein", PI.calculateProtein());
        nutritionTarget.put("carbs", PI.calculateCarbs());
        nutritionTarget.put("fat", PI.calculateFat());

        Map<String, Object> nutritionAbsorbed = new HashMap<>();
        nutritionAbsorbed.put("kcal", 0.0);
        nutritionAbsorbed.put("protein", 0.0);
        nutritionAbsorbed.put("carbs", 0.0);
        nutritionAbsorbed.put("fat", 0.0);

        nutritionOverall.put("nutritionTarget", nutritionTarget);
        nutritionOverall.put("nutritionAbsorbed", nutritionAbsorbed);

        user.put("nutritionOverall", nutritionOverall);

        // add waterOverall
        Map<String, Object> waterOverall = new HashMap<>();
        waterOverall.put("waterTarget", PI.calculateWater());
        waterOverall.put("waterAbsorbed", 0);
        user.put("waterOverall", waterOverall);

        db.collection(USERS_COLLECTION).document(uid).set(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Add user successfully");
            } else {
                Log.d(TAG, "Add user failed");
            }
        });
    }
    public String getUserRole(String uid) {
        Task<DocumentSnapshot> query = db.collection(USERS_COLLECTION).document(uid).get();
        while (!query.isComplete()) {}
        return query.getResult().getString("role");
    }
    public boolean isFoodExist(String foodName) {
        // Find food in shared foods
        Task<QuerySnapshot> query = db.collection(SHARED_FOODS_COLLECTION).whereEqualTo("name", foodName).get();
        while (!query.isComplete()) {}
        if (query.getResult().size() > 0) {
            return true;
        }
        // Find food in custom foods
        Task<QuerySnapshot> query2 = db.collection(CUSTOM_FOODS_COLLECTION).whereEqualTo("name", foodName).get();
        return query2.getResult().size() > 0;
    }
    public void addSharedFood(Food food) {
        Map<String, Object> food_to_add = new HashMap<>();
        food_to_add.put("name", food.getName());
        food_to_add.put("unit", '1' + food.getUnit());
        food_to_add.put("energy", food.getNutritionPerUnit().getCalories());
        food_to_add.put("protein", food.getNutritionPerUnit().getProtein());
        food_to_add.put("fiber", food.getNutritionPerUnit().getFiber());
        food_to_add.put("fat", food.getNutritionPerUnit().getFat());
        food_to_add.put("carb", food.getNutritionPerUnit().getCarbs());
        db.collection(SHARED_FOODS_COLLECTION).add(food_to_add).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(null, "Add food successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(null, "Add food failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LinkedList<Food> getSharedFoods() {
        LinkedList<Food> foods = new LinkedList<>();
        Task<QuerySnapshot> query = db.collection(SHARED_FOODS_COLLECTION).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Food food = new Food();
                    food.setName(document.getString("name"));
                    food.setUnit(document.getString("unit").substring(1));
                    food.getNutritionPerUnit().setCalories(document.getDouble("energy"));
                    food.getNutritionPerUnit().setProtein(document.getDouble("protein"));
                    food.getNutritionPerUnit().setFiber(document.getDouble("fiber"));
                    food.getNutritionPerUnit().setFat(document.getDouble("fat"));
                    food.getNutritionPerUnit().setCarbs(document.getDouble("carb"));
                    foods.add(food);
                }
            }
        });
        return foods;
    }
    public User getUser(String uid) {
        Task<DocumentSnapshot> query = db.collection(USERS_COLLECTION).document(uid).get();
        while (!query.isComplete()) {}
        DocumentSnapshot document = query.getResult();
        User user = new User();
        user.setId(uid);
        user.setUsername(document.getString("username"));
        user.setEmail(document.getString("email"));
        user.setCreditPoints(document.getLong("credit_points").intValue());
        user.setIsBanned(document.getBoolean("is_banned"));
        user.setPersonalInformation(document.get("PI", PersonalInformation.class));
        // get nutritionOverall
        NutritionOverall nutritionOverall = new NutritionOverall();
        nutritionOverall.setNutritionTarget(document.get("nutritionOverall.nutritionTarget", Nutrition.class));
        nutritionOverall.setNutritionAbsorbed(document.get("nutritionOverall.nutritionAbsorbed", Nutrition.class));
        user.setNutritionOverall(nutritionOverall);
        // get waterOverall
        Map<String, Object> waterOverall = document.get("waterOverall", Map.class);
        // get waterTarget
        Double waterTarget = (Double) waterOverall.get("waterTarget");
        // get waterAbsorbed
        Double waterAbsorbed = (Double) waterOverall.get("waterAbsorbed");
        user.setWaterTarget(waterTarget);
        user.setWaterAbsorbed(waterAbsorbed);
        return user;
    }
}
