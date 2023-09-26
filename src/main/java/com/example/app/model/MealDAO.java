package com.example.app.model;

import java.sql.SQLException;
import java.util.List;

public interface MealDAO {
    List<Meal> getAll() throws SQLException;
    List<Meal> getMealsByCategory(String category) throws SQLException;
    // category, meal, meal_id
    void save(String category, String meal, int id, String[] ingredients) throws SQLException;
}
