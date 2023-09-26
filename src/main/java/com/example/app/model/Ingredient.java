package com.example.app.model;

public class Ingredient {
    private String name;
    private int id;
    private int mealId;

    public Ingredient(String name, int id, int mealId) {
        this.name = name;
        this.id = id;
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getMealId() {
        return mealId;
    }
}
