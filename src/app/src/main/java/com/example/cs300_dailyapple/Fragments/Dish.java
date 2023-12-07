package com.example.cs300_dailyapple.Fragments;

import android.content.Context;

import java.util.LinkedList;

public class Dish {
    private String name;
    private int calo;
    private int gram;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getCalo() {
        return calo;
    }

    public void setCalo(int calo) {
        this.calo = calo;
    }
    public int getGram() {
        return gram;
    }

    public void setGram(int gram) {
        this.gram = gram;
    }
    public static LinkedList<Dish> loadDishList(Context context){
        LinkedList<Dish> dishList = new LinkedList<>();
        return dishList;
    }
    public static void saveDishList(LinkedList<Dish> dishList,Context context){

    }
}
