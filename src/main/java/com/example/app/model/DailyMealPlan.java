package com.example.app.model;

import com.example.app.Main;

public class DailyMealPlan {
    private String dayOfWeek;
    private Meal breakfast;
    private Meal lunch;
    private Meal dinner;
    private int id;

    public DailyMealPlan(String dayOfWeek, Meal breakfast, Meal lunch, Meal dinner) {
        this.dayOfWeek = dayOfWeek;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.id = Main.generateRandomInt();
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public Meal getBreakfast() {
        return breakfast;
    }

    public Meal getLunch() {
        return lunch;
    }

    public Meal getDinner() {
        return dinner;
    }
}
