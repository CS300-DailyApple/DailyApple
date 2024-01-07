package com.example.cs300_dailyapple.Models;
import android.content.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class PersonalInformation {
    // Attributes
    private String gender;
    private int age;
    private Double height;
    private Double weight;
    // Methods
    public String getGender() {
        return this.gender;
    }

    public int getAge() {
        return this.age;
    }

    public Double getHeight() {
        return this.height;
    }

    public Double getWeight() {
        return this.weight;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public LinkedList<PersonalInformation> loadPersonalInformationList(Context context){
        LinkedList<PersonalInformation> listInf= new LinkedList<>();
        //Load here
        return listInf;
    }
    public static void savePersonalInformationList(LinkedList<PersonalInformation> personalInformationList,Context context){

    }
    public Double calculateBMI(){
        return this.weight/((this.height/100)*(this.height/100));
    }
    public Double calculateTDEE() {
        if (this.gender == "male") {
            return (88.362 + 13.397 * this.weight + 4.799 * this.height - 5.677 * this.age) * 1.375;
        } else {
            return (447.593 + 9.247 * this.weight + 3.098 * this.height - 4.330 * this.age) * 1.375;
        }
    }
    public Double calculateProtein() {
        return this.weight * 2.2;
    }
    public Double calculateFibre() {
        return this.weight * 14;
    }
    public Double calculateFat() {
        return this.calculateTDEE() * 0.25 / 9;
    }
    public Double calculateCarbs() {
        return this.calculateTDEE() * 0.5 / 4;
    }
    // Return the amount of water in ml
    public Double calculateWater() {
        return this.weight * 0.033 * 1000;
    }
}
