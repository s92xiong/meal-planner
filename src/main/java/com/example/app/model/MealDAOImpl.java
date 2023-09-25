package com.example.app.model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MealDAOImpl implements MealDAO {
    private final Connection connection;

    public MealDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(String category, String meal, int id, String[] ingredients) throws SQLException {

        // Create a PreparedStatement to safely execute the SQL query
        String sql = "INSERT INTO meals (category, meal, meal_id) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        // Set the parameter values for the PreparedStatement
        statement.setString(1, category);
        statement.setString(2, meal);
        statement.setInt(3, id);

        // Execute the PreparedStatement to insert the meal
        statement.executeUpdate();

        // Add ingredients into Postgres DB
        String ingredientSql = "INSERT INTO ingredients (ingredient, ingredient_id, meal_id) VALUES (?, ?, ?)";
        PreparedStatement ingredientStatement = connection.prepareStatement(ingredientSql);

        for (String ingredient : ingredients) {
            int ingredientId = generateRandomInt();

            // Set the parameter values for the ingredient PreparedStatement
            ingredientStatement.setString(1, ingredient);
            ingredientStatement.setInt(2, ingredientId);
            ingredientStatement.setInt(3, mealId);

            // Execute the ingredient PreparedStatement to insert the ingredient
            ingredientStatement.executeUpdate();
        }
    }

    @Override
    public List<Meal> getAll() throws SQLException {
        Statement statement = connection.createStatement();
        List<Meal> meals = new LinkedList<>();
        ResultSet rsMeals = statement.executeQuery("SELECT * FROM \"meals\";");
        while (rsMeals.next()) {
            var category = rsMeals.getString("category");
            var mealName = rsMeals.getString("meal");
            var mealId = rsMeals.getInt("meal_id");
            Meal meal = new Meal(category, mealName, mealId);
            meals.add(meal);
        }

        // For each meal, query for and set its ingredients
        for (Meal meal : meals) {
            var sql = String.format("SELECT * FROM ingredients WHERE meal_id = %d", meal.getMeal_id());
            ResultSet rsIngredients = statement.executeQuery(sql);

            while (rsIngredients.next()) {
                var ingredient = rsIngredients.getString("ingredient");
                meal.addIngredient(ingredient);
            }
        }

        return meals;
    }

    public static int generateRandomInt() {
        Random random = new Random();
        return random.nextInt(2147483647) + 1;
    }
}
