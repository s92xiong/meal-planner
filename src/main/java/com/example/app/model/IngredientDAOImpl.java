package com.example.app.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAOImpl implements IngredientDAO {
    private final Connection connection;

    public IngredientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Ingredient> read() {
        List<Ingredient> list = new ArrayList<>();
        try {
            // Create a statement
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM ingredients";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                var name = rs.getString("ingredient");
                var id = rs.getInt("ingredient_id");
                var mealId = rs.getInt("meal_id");
                Ingredient ingredient = new Ingredient(name, id, mealId);
                list.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
