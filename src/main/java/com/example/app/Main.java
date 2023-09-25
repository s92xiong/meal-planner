package com.example.app;

import com.example.app.model.Meal;
import com.example.app.model.MealDAOImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Main {
    private final ArrayList<String> mealCategories;
    private final Connection connection;
    private final List<Meal> meals;
    public static void main(String[] args) throws SQLException {
        // Read the application.properties file
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("application.properties")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // DB Setup
        String DB_URL = properties.getProperty("DB_URL");
        String USER = properties.getProperty("DB_USER");
        String PASS = properties.getProperty("DB_PASS");

        // Establish connection to the database URL
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        // Create meals table
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS meals (" +
                "category varchar(1024) NOT NULL," +
                "meal varchar(1024) NOT NULL," +
                "meal_id integer" +
                ")");

        // Create ingredients table
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients (" +
                "ingredient varchar(1024) NOT NULL," +
                "ingredient_id integer," +
                "meal_id integer" +
                ")");

        // Create ingredients table
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS plan (" +
                "day_of_week varchar(1024) NOT NULL," +
                "breakfast_id integer," +
                "lunch_id integer," +
                "dinner_id integer," +
                "breakfast_name varchar(1024) NOT NULL," +
                "lunch_name varchar(1024) NOT NULL," +
                "dinner_name varchar(1024) NOT NULL" +
                ")");

        // Query for all Meals
        MealDAOImpl mealDAO = new MealDAOImpl(connection);
        List<Meal> meals = mealDAO.getAll();

        // Start application
        Main app = new Main(connection, meals);

        int result = 0;
        while (result == 0) {
            result = app.run();
        }
        System.out.println("Bye!");

        statement.close();
        connection.close();
    }

    private Main(Connection connection, List<Meal> meals) {
        ArrayList<String> list = new ArrayList<>();
        list.add("breakfast");
        list.add("lunch");
        list.add("dinner");
        this.mealCategories = list;
        this.connection = connection;
        this.meals = meals;
    }

    public int run() throws SQLException {
        String operation = getInput("What would you like to do (add, show, plan, save, exit)?");
        switch (operation) {
            case "add" -> add();
//            case "show" -> show();
//            case "plan" -> plan();
//            case "save" -> save();
            case "exit" -> {
                return -1;
            }
        }
        return 0;
    }

    private int add() throws SQLException {
        // Get category
        String category = getCategory("meal", "add");

        // Get meal name
        String name = "";
        boolean invalidName = false;
        while (true) {
            String message = invalidName ? "Wrong format. Use letters only!" : "Input the meal's name:";
            name = getMealNameInput(message);
            if (!isValidFormat(name)) {
                invalidName = true;
            } else {
                break;
            }
        }

        // Get ingredients (comma separated values)
        String[] ingredients;
        boolean invalidIngredients = false;
        while (true) {
            // Assume that all ingredients are valid, if we find an ingredient that is invalid, set as false
            String message = invalidIngredients ? "Wrong format. Use letters only!" : "Input the ingredients:";

            // Check for double commas
            String ingredientsString = getIngredientsInput(message);
            if (isValidFormat(ingredientsString)) {
                ingredients = ingredientsString.split(",");
                for (int i = 0; i < ingredients.length; i++) {
                    ingredients[i] = ingredients[i].trim();
                }
                break;
            } else {
                invalidIngredients = true;
            }
        }

        // Save meal to database
        int mealId = generateRandomInt();
        MealDAOImpl mealDAO = new MealDAOImpl(connection);
        mealDAO.save(category, name, mealId, ingredients);

        // Update the meals list to reflect immediate change
        Meal meal = new Meal(category, name, mealId);
        for (String ingredient : ingredients) {
            meal.addIngredient(ingredient);
        }
        meals.add(meal);

        System.out.println("The meal has been added!");
        return 0;
    }

    private static String getCategoryInput(String s) {
        return getInput(s);
    }

    private static String getMealNameInput(String s) {
        return getInput(s);
    }

    private static String getIngredientsInput(String s) {
        return getInput(s);
    }

    private static String getInput(String message) {
        System.out.println(message);
        return new Scanner(System.in).nextLine();
    }

    private String getCategory(String s1, String s2) {
        String category;
        boolean invalidCategory = false;
        while (true) {
            String message = invalidCategory ? "Wrong meal category! Choose from: breakfast, lunch, dinner." :
                    "Which " + s1 + " do you want to " + s2 + " (breakfast, lunch, dinner)?";
            category = getCategoryInput(message);
            if (!isValidCategory(category)) {
                invalidCategory = true;
            } else {
                break;
            }
        }

        return category;
    }

    private boolean isValidCategory(String category) {
        return !category.isEmpty() && mealCategories.contains(category);
    }

    public static boolean isValidFormat(String input) {
        // Check for double commas
        if (input.contains(",,") || input.endsWith(",")) {
            return false;
        }
        String[] items = input.split(",");
        for (String item : items) {
            item = item.trim();
            // Check for trailing commas without a following word
            if (item.isEmpty()) {
                return false;
            }
            // Check for non-letter characters (including spaces)
            if (!item.matches("[a-zA-Z ]+")) {
                return false;
            }
        }
        return true;
    }

    public static int generateRandomInt() {
        Random random = new Random();
        return random.nextInt(2147483647) + 1;
    }
}
