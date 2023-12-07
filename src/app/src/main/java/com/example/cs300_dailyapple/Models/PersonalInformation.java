package com.example.cs300_dailyapple.Models;

public class PersonalInformation {
    // Attributes
    private boolean gender;
    private int age;
    private int height;
    private int weight;

    // Methods
    public boolean getGender() {
        return this.gender;
    }

    public int getAge() {
        return this.age;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
