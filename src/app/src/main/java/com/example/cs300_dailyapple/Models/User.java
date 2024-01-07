package com.example.cs300_dailyapple.Models;

public class User {
    // Attributes
    private String id;
    private String email;
    private String role = "user";
    private String username;
    private int creditPoints;
    private boolean isBanned;
    private PersonalInformation personalInformation;
    private NutritionOverall nutritionOverall;
    private WaterOverall waterOverall;

    // Methods
    public User() {
        this.id = "";
        this.username = "";
        this.email = "";
        this.creditPoints = 0;
        this.isBanned = false;
        this.personalInformation = new PersonalInformation();
        this.nutritionOverall = new NutritionOverall();
        this.waterOverall = new WaterOverall();
    }
    public User(String username) {
        this.id = "";
        this.username = username;
        this.email = "";
        this.creditPoints = 0;
        this.isBanned = false;
        this.personalInformation = new PersonalInformation();
        this.nutritionOverall = new NutritionOverall();
    }

    public User(String id, String username, int creditPoints, boolean isBanned, PersonalInformation personalInformation, NutritionOverall nutritionOverall) {
        this.id = id;
        this.username = username;
        this.creditPoints = creditPoints;
        this.isBanned = isBanned;
        this.personalInformation = personalInformation;
        this.nutritionOverall = nutritionOverall;
    }

    public String getRole() {
        return role;
    }

    public void setAdmin() {
        this.role = "admin";
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public NutritionOverall getNutritionOverall() {
        return nutritionOverall;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public void setNutritionOverall(NutritionOverall nutritionOverall) {
        this.nutritionOverall = nutritionOverall;
    }
    public WaterOverall getWaterOverall() {
        return waterOverall;
    }
    public void setWaterOverall(WaterOverall waterOverall) {
        this.waterOverall = waterOverall;
    }
}
