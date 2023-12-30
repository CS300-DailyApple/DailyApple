package com.example.cs300_dailyapple.Models;
import android.content.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class PersonalInformation {
    // Attributes
    private boolean gender; // true is male, false is female
    private int age;
    private int height;
    private int weight;
    private String date;
    private int calo;
    private int water;

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
    public int getCalo() {
        return this.calo;
    }
    public int getWater() {
        return this.water;
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
    public void setCalo(int calo) {
        this.calo = calo;
    }
    public void setWater(int water) {
        this.water = water;
    }
    public void setDate(){
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'tháng' MM, yyyy");
        String formattedDate = currentDate.format(formatter);

        this.date = formattedDate;
    }
    public LinkedList<PersonalInformation> loadPersonalInformationList(Context context){
        LinkedList<PersonalInformation> listInf= new LinkedList<>();
        //Load here
        return listInf;
    }
    public static void savePersonalInformationList(LinkedList<PersonalInformation> personalInformationList,Context context){

    }
}
