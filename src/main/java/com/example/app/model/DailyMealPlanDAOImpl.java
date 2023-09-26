package com.example.app.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DailyMealPlanDAOImpl implements DailyMealPlanDAO {
    private final Connection connection;

    public DailyMealPlanDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void add(DailyMealPlan dailyMealPlan) {
        try {
            String sql = "INSERT INTO plan " +
                    "(day_of_week, breakfast_id, " +
                    "lunch_id, dinner_id, breakfast_name, lunch_name, dinner_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, dailyMealPlan.getDayOfWeek());
            statement.setInt(2, dailyMealPlan.getBreakfast().getMeal_id());
            statement.setInt(3, dailyMealPlan.getLunch().getMeal_id());
            statement.setInt(4, dailyMealPlan.getDinner().getMeal_id());
            statement.setString(5, dailyMealPlan.getBreakfast().getMeal());
            statement.setString(6, dailyMealPlan.getLunch().getMeal());
            statement.setString(7, dailyMealPlan.getDinner().getMeal());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try {
            // Create a statement
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM plan";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> read() {
        List<Integer> list = new ArrayList<>();
        try {
            // Create a statement
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM plan";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                var breakfastId = rs.getInt("breakfast_id");
                var lunchId = rs.getInt("lunch_id");
                var dinnerId = rs.getInt("dinner_id");
                list.add(breakfastId);
                list.add(lunchId);
                list.add(dinnerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
