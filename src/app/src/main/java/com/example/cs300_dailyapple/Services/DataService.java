package com.example.cs300_dailyapple.Services;

import android.system.SystemCleaner;
import android.util.Log;
import android.widget.Toast;

import com.example.cs300_dailyapple.Models.BodyInformation;
import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.Nutrition;
import com.example.cs300_dailyapple.Models.NutritionOverall;
import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.example.cs300_dailyapple.Models.SuggestedFood;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.Models.WaterHistoryItem;
import com.example.cs300_dailyapple.Models.WaterInformation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DataService {
    private final String TAG = "DataService";
    private static final String USERS_COLLECTION = "users";
    private static final String SHARED_FOODS_COLLECTION = "shared_foods";
    private static final String CUSTOM_FOODS_COLLECTION = "custom_foods";
    private static final String SUGGESTED_FOODS_COLLECTION = "suggested_foods";

    // Singleton
    private static DataService instance = null;

    private boolean called = false;

    public boolean isCalled() {
        return called;
    }

    public void setCalled(boolean called) {
        this.called = called;
    }

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

    public void addUser(String uid, String email, String username, PersonalInformation PI, WaterInformation waterInformation) {
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
        user.put("waterInformation", waterInformation);

        Map<String, Boolean> favorite = new HashMap<>();
        user.put("favorite", favorite);

        db.collection(USERS_COLLECTION).document(uid).set(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Add user successfully");
            } else {
                Log.d(TAG, "Add user failed");
            }
        });
    }

    public LinkedList<Food> getUserFoodList(Map<String, Boolean> favorite){
        LinkedList<Food> foods = new LinkedList<>();
        foods.addAll(getSharedFoods());
        foods.addAll(getUserCustomFood());
        for (Food foodElement: foods){
            if (favorite.containsKey(foodElement.getName())) {
                foodElement.setFavorite(true);
            }
        }
        Comparator<Food> foodComparator = Comparator.comparing(food -> food.isFavorite()? 0 : 1);
        foods.sort(foodComparator);
        Log.d(TAG, "Get user food list successfully");
        return foods;
    }

    public LinkedList<Food> getUserCustomFood(){
        LinkedList<Food> foods = new LinkedList<>();
        String uid = AuthService.getInstance().getCurrentUser().getUid();
        Task<DocumentSnapshot> query = db.collection(CUSTOM_FOODS_COLLECTION).document(uid).get();
        while (!query.isComplete()) {}
        DocumentSnapshot document = query.getResult();
        if (!document.exists()){
            Log.d(TAG, "get-user-custom-food query failed!");
            return foods;
        }
        Gson gson = new Gson();
        String foodsJson = gson.toJson(document.get("foods"));
        foods = gson.fromJson(foodsJson, new TypeToken<LinkedList<Food>>() {}.getType());
        Log.d(TAG, "Get user custom food successfully");
        return foods;
    }

    public LinkedList<Food> getSuggestedFood(){
        LinkedList<Food> foods = new LinkedList<>();
//        Task<QuerySnapshot> query = db.collection(SUGGESTED_FOODS_COLLECTION).document()get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (DocumentSnapshot document : task.getResult()) {
//                    Food food = new Food();
//                    food.setName(document.getString("name"));
//                    food.setUnit(document.getString("unit"));
//                    food.setNumberOfUnits(document.getLong("numberOfUnits").intValue());
//                    Nutrition nutritionPerUnit = new Nutrition();
//                    nutritionPerUnit.setKcal(document.getDouble("nutritionPerUnit.kcal"));
//                    nutritionPerUnit.setProtein(document.getDouble("nutritionPerUnit.protein"));
//                    nutritionPerUnit.setFiber(document.getDouble("nutritionPerUnit.fiber"));
//                    nutritionPerUnit.setFat(document.getDouble("nutritionPerUnit.fat"));
//                    nutritionPerUnit.setCarbs(document.getDouble("nutritionPerUnit.carbs"));
//                    food.setNutritionPerUnit(nutritionPerUnit);
//                    foods.add(food);
//                }
//            }
//        });
        return foods;
    }
    public String getUserRole(String uid) {
        Task<DocumentSnapshot> query = db.collection(USERS_COLLECTION).document(uid).get();
        while (!query.isComplete()) {}
        return query.getResult().getString("role");
    }
    public String getUsernameById(String uid) {
        Task<DocumentSnapshot> query = db.collection(USERS_COLLECTION).document(uid).get();
        while (!query.isComplete()) {}
        return query.getResult().getString("username");
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
        Task<QuerySnapshot> query = db.collection(SHARED_FOODS_COLLECTION).get();
        while (!query.isComplete()) {}
        if (!query.isSuccessful()){
            Log.d(TAG, "Add shared food failed");
            return foods;
        }
        for (DocumentSnapshot document : query.getResult()) {
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
        Log.d(TAG, "Get add shared foods successfully");
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
        Gson gson = new Gson();
        user.setId(uid);
        user.setUsername(document.getString("username"));
        user.setEmail(document.getString("email"));
        user.setCreditPoints(document.getLong("creditPoints").intValue());
        user.setIsBanned(document.getBoolean("isBanned"));

        // get personalInformation
        PersonalInformation PI = new PersonalInformation();
        PI.setAge(document.getLong("PI.age").intValue());
        PI.setGender(document.getString("PI.gender"));
        String historyPIJson = gson.toJson(document.get("PI.historyPI"));
        ArrayList<BodyInformation> historyPI = gson.fromJson(historyPIJson, new TypeToken<ArrayList<BodyInformation>>() {}.getType());
        PI.setHistoryPI(historyPI);
        user.setPersonalInformation(PI);

        String favoriteJson = gson.toJson(document.get("favorite"));
        Map<String, Boolean> favorite = gson.fromJson(favoriteJson, new TypeToken<Map<String, Boolean>>() {}.getType());
        user.setFavorite(favorite);
        // get nutritionOverall
        NutritionOverall nutritionOverall = new NutritionOverall();
        nutritionOverall.setNutritionTarget(document.get("nutritionOverall.nutritionTarget", Nutrition.class));
        nutritionOverall.setNutritionAbsorbed(document.get("nutritionOverall.nutritionAbsorbed", Nutrition.class));
        user.setNutritionOverall(nutritionOverall);
        // get waterInformation
        WaterInformation waterInformation = new WaterInformation();
        waterInformation.setWaterTarget(document.getDouble("waterInformation.waterTarget").intValue());
        waterInformation.setContainerCapacity(document.getDouble("waterInformation.containerCapacity").intValue());

        String waterHistoryJson = gson.toJson(document.get("waterInformation.waterHistory"));
        ArrayList<WaterHistoryItem> waterHistory = gson.fromJson(waterHistoryJson, new TypeToken<ArrayList<WaterHistoryItem>>() {}.getType());
        waterInformation.setWaterHistory(waterHistory);
        user.setWaterInformation(waterInformation);
        return user;
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
                PersonalInformation PI = new PersonalInformation();
                PI.setAge(document.getLong("PI.age").intValue());
                PI.setGender(document.getString("PI.gender"));
                String historyPIJson = new Gson().toJson(document.get("PI.historyPI"));
                ArrayList<BodyInformation> historyPI = new Gson().fromJson(historyPIJson, new TypeToken<ArrayList<BodyInformation>>() {}.getType());
                PI.setHistoryPI(historyPI);
                user.setPersonalInformation(PI);
                // get nutritionOverall
                NutritionOverall nutritionOverall = new NutritionOverall();
                nutritionOverall.setNutritionTarget(document.get("nutritionOverall.nutritionTarget", Nutrition.class));
                nutritionOverall.setNutritionAbsorbed(document.get("nutritionOverall.nutritionAbsorbed", Nutrition.class));
                user.setNutritionOverall(nutritionOverall);
                // get waterInformation
                WaterInformation waterInformation = new WaterInformation();
                waterInformation.setWaterTarget(document.getDouble("waterInformation.waterTarget").intValue());
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

    public void saveUser(User user) {
        Map<String, Object> tmp = new HashMap<>();
        tmp.put("username", user.getUsername());
        tmp.put("email", user.getEmail());
        tmp.put("role", user.getRole());
        tmp.put("creditPoints", user.getCreditPoints());
        tmp.put("isBanned", user.getIsBanned());
        tmp.put("PI", user.getPersonalInformation());
        tmp.put("waterInformation", user.getWaterInformation());
        tmp.put("nutritionOverall", user.getNutritionOverall());
        tmp.put("favorite", user.getFavorite());
        db.collection(USERS_COLLECTION).document(AuthService.getInstance().getCurrentUser().getUid()).set(tmp).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Save user successfully");
            } else {
                Log.d(TAG, "Save user failed");
            }
        });
    }
    public LinkedList<SuggestedFood> getSuggestedFoodList() {
        LinkedList<SuggestedFood> suggestedFoods = new LinkedList<>();
        Task<QuerySnapshot> query = db.collection(SUGGESTED_FOODS_COLLECTION).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    SuggestedFood suggestedFood = new SuggestedFood();
                    suggestedFood.setName(document.getString("name"));
                    suggestedFood.setUnit(document.getString("unit"));
                    suggestedFood.setNumberOfUnits(document.getLong("numberOfUnits").intValue());
                    Nutrition nutritionPerUnit = new Nutrition();
                    nutritionPerUnit.setKcal(document.getDouble("nutritionPerUnit.kcal"));
                    nutritionPerUnit.setProtein(document.getDouble("nutritionPerUnit.protein"));
                    nutritionPerUnit.setFiber(document.getDouble("nutritionPerUnit.fiber"));
                    nutritionPerUnit.setFat(document.getDouble("nutritionPerUnit.fat"));
                    nutritionPerUnit.setCarbs(document.getDouble("nutritionPerUnit.carbs"));
                    suggestedFood.setNutritionPerUnit(nutritionPerUnit);
                    suggestedFood.setContributorId(document.getString("contributorId"));
                    suggestedFoods.add(suggestedFood);
                }
            }
        });
        return suggestedFoods;
    }



    public void addSuggestedFood(LinkedList<Food> userSuggestedFoodList) {
        CollectionReference colRef = db.collection(SUGGESTED_FOODS_COLLECTION);
        if (userSuggestedFoodList == null) return;
        for (Food food: userSuggestedFoodList){
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
            food_to_add.put("ContributorId", AuthService.getInstance().getCurrentUser().getUid());
            db.collection(SHARED_FOODS_COLLECTION).add(food_to_add).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Add suggested food successfully");
                } else {
                    Log.d(TAG, "Add suggested food failed");
                }
            });
        }

    }

    public void setCustomFood(LinkedList<Food> foods) {
        db.collection(CUSTOM_FOODS_COLLECTION).document(AuthService.getInstance().getCurrentUser().getUid()).set(foods).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Log.d(TAG, "save custom foods successfully!");
            }
            else{
                Log.d(TAG, "save custom foods failed!");
            }
        });
    }
}
