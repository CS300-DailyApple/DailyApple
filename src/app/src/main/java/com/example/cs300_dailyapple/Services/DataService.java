package com.example.cs300_dailyapple.Services;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.Nutrition;
import com.example.cs300_dailyapple.Models.NutritionOverall;
import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.Models.WaterHistoryItem;
import com.example.cs300_dailyapple.Models.WaterInformation;
import com.example.cs300_dailyapple.Models.WaterOverall;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.rpc.context.AttributeContext;

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
        user.put("creditPoints", 100);
        user.put("isBanned", false);
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

        // add waterInformation
        Map<String, Object> waterInformation = new HashMap<>();
        waterInformation.put("waterTarget", PI.calculateWater());
        waterInformation.put("totalWaterDrank", 0.0);
        waterInformation.put("containerCapacity", 0.0);
        waterInformation.put("waterHistory", new ArrayList<WaterHistoryItem>());
        user.put("waterInformation", waterInformation);

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
        food_to_add.put("unit", food.getUnit());
        food_to_add.put("numberOfUnits", food.getNumberOfUnits());
        Map<String, Object> nutritionPerUnit = new HashMap<>();
        nutritionPerUnit.put("kcal", food.getNutritionPerUnit().getKcal());
        nutritionPerUnit.put("protein", food.getNutritionPerUnit().getProtein());
        nutritionPerUnit.put("fiber", food.getNutritionPerUnit().getFiber());
        nutritionPerUnit.put("fat", food.getNutritionPerUnit().getFat());
        nutritionPerUnit.put("carbs", food.getNutritionPerUnit().getCarbs());
        food_to_add.put("nutritionPerUnit", nutritionPerUnit);
        db.collection(SHARED_FOODS_COLLECTION).add(food_to_add).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Add food successfully");
            } else {
                Log.d(TAG, "Add food failed");
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
                    food.setUnit(document.getString("unit"));
                    food.setNumberOfUnits(document.getLong("numberOfUnits").intValue());
                    Nutrition nutritionPerUnit = new Nutrition();
                    nutritionPerUnit.setKcal(document.getDouble("nutritionPerUnit.kcal"));
                    nutritionPerUnit.setProtein(document.getDouble("nutritionPerUnit.protein"));
                    nutritionPerUnit.setFiber(document.getDouble("nutritionPerUnit.fiber"));
                    nutritionPerUnit.setFat(document.getDouble("nutritionPerUnit.fat"));
                    nutritionPerUnit.setCarbs(document.getDouble("nutritionPerUnit.carbs"));
                    food.setNutritionPerUnit(nutritionPerUnit);
                    foods.add(food);
                }
            }
        });
        return foods;
    }
    public LinkedList<Food> searchSharedFoods(String query) {
        query = query.toLowerCase();
        LinkedList<Food> foods = new LinkedList<>();
        Task<QuerySnapshot> querySnapshotTask = db.collection(SHARED_FOODS_COLLECTION)
                .get();
        while (!querySnapshotTask.isComplete()) {}

        for (DocumentSnapshot document : querySnapshotTask.getResult()) {
            String foodName = document.getString("name");
            if (foodName != null && foodName.toLowerCase().contains(query)) {
                Food food = new Food();
                food.setName(foodName);
                food.setUnit(document.getString("unit"));
                food.setNumberOfUnits(document.getLong("numberOfUnits").intValue());
                Nutrition nutritionPerUnit = new Nutrition();
                nutritionPerUnit.setKcal(document.getDouble("nutritionPerUnit.kcal"));
                nutritionPerUnit.setProtein(document.getDouble("nutritionPerUnit.protein"));
                nutritionPerUnit.setFiber(document.getDouble("nutritionPerUnit.fiber"));
                nutritionPerUnit.setFat(document.getDouble("nutritionPerUnit.fat"));
                nutritionPerUnit.setCarbs(document.getDouble("nutritionPerUnit.carbs"));
                food.setNutritionPerUnit(nutritionPerUnit);
                foods.add(food);
            }
        }
        return foods;
    }
    public String getFoodId(String name) {
        Task<QuerySnapshot> query = db.collection(SHARED_FOODS_COLLECTION).whereEqualTo("name", name).get();
        while (!query.isComplete()) {}
        return query.getResult().getDocuments().get(0).getId();
    }
    public Food getFoodById(String foodId) {
        Task<DocumentSnapshot> query = db.collection(SHARED_FOODS_COLLECTION).document(foodId).get();
        while (!query.isComplete()) {}
        DocumentSnapshot document = query.getResult();
        Food food = new Food();
        food.setName(document.getString("name"));
        food.setUnit(document.getString("unit"));
        food.setNumberOfUnits(document.getLong("numberOfUnits").intValue());
        Nutrition nutritionPerUnit = new Nutrition();
        nutritionPerUnit.setKcal(document.getDouble("nutritionPerUnit.kcal"));
        nutritionPerUnit.setProtein(document.getDouble("nutritionPerUnit.protein"));
        nutritionPerUnit.setFiber(document.getDouble("nutritionPerUnit.fiber"));
        nutritionPerUnit.setFat(document.getDouble("nutritionPerUnit.fat"));
        nutritionPerUnit.setCarbs(document.getDouble("nutritionPerUnit.carbs"));
        food.setNutritionPerUnit(nutritionPerUnit);
        return food;
    }
    public void deleteSharedFoodById(String foodId) {
        db.collection(SHARED_FOODS_COLLECTION).document(foodId).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Delete food successfully");
            } else {
                Log.d(TAG, "Delete food failed");
            }
        });
    }
    public User getUser(String uid) {
        Task<DocumentSnapshot> query = db.collection(USERS_COLLECTION).document(uid).get();
        while (!query.isComplete()) {}
        DocumentSnapshot document = query.getResult();
        User user = new User();
        if (!document.exists()){
            Log.d(TAG, "get-user query failed!");
            return user;
        }
        user.setId(uid);
        user.setUsername(document.getString("username"));
        user.setEmail(document.getString("email"));
        user.setCreditPoints(document.getLong("creditPoints").intValue());
        user.setIsBanned(document.getBoolean("isBanned"));
        user.setPersonalInformation(document.get("PI", PersonalInformation.class));
        // get nutritionOverall
        NutritionOverall nutritionOverall = new NutritionOverall();
        nutritionOverall.setNutritionTarget(document.get("nutritionOverall.nutritionTarget", Nutrition.class));
        nutritionOverall.setNutritionAbsorbed(document.get("nutritionOverall.nutritionAbsorbed", Nutrition.class));
        user.setNutritionOverall(nutritionOverall);
        // get waterInformation
        WaterInformation waterInformation = new WaterInformation();
        waterInformation.setWaterTarget(document.getDouble("waterInformation.waterTarget").intValue());
        waterInformation.setTotalWaterDrank(document.getDouble("waterInformation.totalWaterDrank").intValue());
        waterInformation.setContainerCapacity(document.getDouble("waterInformation.containerCapacity").intValue());
        Gson gson = new Gson();
        String waterHistoryJson = gson.toJson(document.get("waterInformation.waterHistory"));
        ArrayList<WaterHistoryItem> waterHistory = gson.fromJson(waterHistoryJson, new TypeToken<ArrayList<WaterHistoryItem>>() {}.getType());
        waterInformation.setWaterHistory(waterHistory);
        user.setWaterInformation(waterInformation);
        return user;
    }

    public void updateMeal(String uid) {

    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        // Get users with role "user"
        Task<QuerySnapshot> query = db.collection(USERS_COLLECTION).whereEqualTo("role", "user").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    User user = new User();
                    user.setId(document.getId());
                    user.setUsername(document.getString("username"));
                    user.setEmail(document.getString("email"));
                    user.setCreditPoints(document.getLong("creditPoints").intValue());
                    user.setIsBanned(document.getBoolean("isBanned"));
                    user.setPersonalInformation(document.get("PI", PersonalInformation.class));
                    // get nutritionOverall
                    NutritionOverall nutritionOverall = new NutritionOverall();
                    nutritionOverall.setNutritionTarget(document.get("nutritionOverall.nutritionTarget", Nutrition.class));
                    nutritionOverall.setNutritionAbsorbed(document.get("nutritionOverall.nutritionAbsorbed", Nutrition.class));
                    user.setNutritionOverall(nutritionOverall);
                    // get waterInformation
                    WaterInformation waterInformation = new WaterInformation();
                    waterInformation.setWaterTarget(document.getDouble("waterInformation.waterTarget").intValue());
                    waterInformation.setTotalWaterDrank(document.getDouble("waterInformation.totalWaterDrank").intValue());
                    waterInformation.setContainerCapacity(document.getDouble("waterInformation.containerCapacity").intValue());
                    Gson gson = new Gson();
                    String waterHistoryJson = gson.toJson(document.get("waterInformation.waterHistory"));
                    ArrayList<WaterHistoryItem> waterHistory = gson.fromJson(waterHistoryJson, new TypeToken<ArrayList<WaterHistoryItem>>() {}.getType());
                    waterInformation.setWaterHistory(waterHistory);
                    user.setWaterInformation(waterInformation);
                    users.add(user);
                }
            }
        });
        return users;
    }
    public ArrayList<User> searchUsers(String query) {
        query = query.toLowerCase();
        ArrayList<User> users = new ArrayList<>();
        Task<QuerySnapshot> querySnapshotTask = db.collection(USERS_COLLECTION)
                .whereEqualTo("role", "user")
                .get();
        while (!querySnapshotTask.isComplete()) {}

        for (DocumentSnapshot document : querySnapshotTask.getResult()) {
            String username = document.getString("username");
            if (username != null && username.toLowerCase().contains(query)) {
                User user = new User();
                user.setId(document.getId());
                user.setUsername(document.getString("username"));
                user.setEmail(document.getString("email"));
                user.setCreditPoints(document.getLong("creditPoints").intValue());
                user.setIsBanned(document.getBoolean("isBanned"));
                user.setPersonalInformation(document.get("PI", PersonalInformation.class));
                // get nutritionOverall
                NutritionOverall nutritionOverall = new NutritionOverall();
                nutritionOverall.setNutritionTarget(document.get("nutritionOverall.nutritionTarget", Nutrition.class));
                nutritionOverall.setNutritionAbsorbed(document.get("nutritionOverall.nutritionAbsorbed", Nutrition.class));
                user.setNutritionOverall(nutritionOverall);
                // get waterInformation
                WaterInformation waterInformation = new WaterInformation();
                waterInformation.setWaterTarget(document.getDouble("waterInformation.waterTarget").intValue());
                waterInformation.setTotalWaterDrank(document.getDouble("waterInformation.totalWaterDrank").intValue());
                waterInformation.setContainerCapacity(document.getDouble("waterInformation.containerCapacity").intValue());
                Gson gson = new Gson();
                String waterHistoryJson = gson.toJson(document.get("waterInformation.waterHistory"));
                ArrayList<WaterHistoryItem> waterHistory = gson.fromJson(waterHistoryJson, new TypeToken<ArrayList<WaterHistoryItem>>() {}.getType());
                waterInformation.setWaterHistory(waterHistory);
                user.setWaterInformation(waterInformation);
                users.add(user);
            }
        }
        return users;
    }
    public void banUser(String username) {
        // Get user with username
        Task<QuerySnapshot> query = db.collection(USERS_COLLECTION).whereEqualTo("username", username).get();
        while (!query.isComplete()) {}
        DocumentSnapshot document = query.getResult().getDocuments().get(0);
        // Update isBanned field
        db.collection(USERS_COLLECTION).document(document.getId()).update("isBanned", true).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Ban user successfully");
            } else {
                Log.d(TAG, "Ban user failed");
            }
        });
    }
}
