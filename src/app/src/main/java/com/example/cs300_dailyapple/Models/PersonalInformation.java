package com.example.cs300_dailyapple.Models;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class PersonalInformation {
    // Attributes
    private boolean gender;
    private int age;
    private int height;
    private int weight;
    private String date;

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
    public String getDate() { return this.date; }
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
    public void setDate(){
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'tháng' MM, yyyy");
        String formattedDate = currentDate.format(formatter);

        this.date = formattedDate;
    }

}
