package com.example.app.model;

import java.sql.SQLException;
import java.util.List;

public interface IngredientDAO {
    List<Ingredient> read() throws SQLException;
}
