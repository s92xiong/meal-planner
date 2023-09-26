package com.example.app.model;

import java.util.List;

public interface DailyMealPlanDAO {
    void add(DailyMealPlan dailyMealPlan);
    void delete();
    List<Integer> read();
}