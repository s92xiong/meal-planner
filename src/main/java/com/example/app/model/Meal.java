package com.example.app.model;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String category;
    private String meal;
    private int meal_id;
    private List<String> ingredients;

    public Meal(String category, String meal, int meal_id) {
        this.category = category;
        this.meal = meal;
        this.meal_id = meal_id;
        this.ingredients = new ArrayList<>();
    }

    public String getCategory() {
        return category;
    }

    public String getMeal() {
        return meal;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
