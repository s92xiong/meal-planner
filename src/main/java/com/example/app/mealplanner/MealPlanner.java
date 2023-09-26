package com.example.app.mealplanner;

import com.example.app.model.DailyMealPlan;
import com.example.app.model.DailyMealPlanDAOImpl;
import com.example.app.model.Meal;
import util.DayOfWeek;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

public class MealPlanner {
    public static void planMeal(List<Meal> meals, Connection connection) {
        // Create a DAO object for the dailMealPlan to save and delete data
        DailyMealPlanDAOImpl dailyMealPlanDao = new DailyMealPlanDAOImpl(connection);

        // Clear 'plan' table
        dailyMealPlanDao.delete();

        // Get filtered meals from the meals list
        var breakfastMeals = filterMealsByCategory(meals, "Breakfast");
        var lunchMeals = filterMealsByCategory(meals, "Lunch");
        var dinnerMeals = filterMealsByCategory(meals, "Dinner");

        List<DailyMealPlan> dailyMealPlanList = new ArrayList<>();

        // Loop through the days of the week and prompt user for meal input, saving each DailyMealPlan as a record
        for (DayOfWeek enumDay : DayOfWeek.values()) {
            Scanner scanner = new Scanner(System.in);
            var day = enumDay.getDay();
            System.out.println(day);

            // Get breakfast, lunch, and dinner
            Meal breakfastMeal = getMealInput("breakfast", scanner, breakfastMeals, day);
            Meal lunchMeal = getMealInput("lunch", scanner, lunchMeals, day);
            Meal dinnerMeal = getMealInput("dinner", scanner, dinnerMeals, day);

            // Create dailyMealPlan object -> { Monday, 58120, 93213, 12313 }
            DailyMealPlan dailyMealPlan = new DailyMealPlan(day, breakfastMeal, lunchMeal, dinnerMeal);

            // Print confirmation that meals are saved for this day
            System.out.println("Yeah! We planned the meals for " + day + ".\n");

            // Save the daily meal plan to the database
            dailyMealPlanDao.add(dailyMealPlan);

            dailyMealPlanList.add(dailyMealPlan);
        }

        printMealPlan(dailyMealPlanList);
    }

    private static List<Meal> filterMealsByCategory(List<Meal> meals, String category) {
        return meals.stream()
                .filter(meal -> meal.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    private static void printMeals(List<Meal> filteredMeals) {
        // Sort mealsList alphabetically based on the 'meal' field
        Collections.sort(filteredMeals, Comparator.comparing(Meal::getMeal));
        for (Meal meal : filteredMeals) {
            System.out.println(meal.getMeal());
        }
    }

    private static Meal getMealInput(String mealCategory, Scanner scanner, List<Meal> filteredMeals, String day) {
        Meal returnMeal = null;

        printMeals(filteredMeals);
        System.out.println("Choose the " + mealCategory + " for " + day + " from the list above:");
        String mealInput;
        while (true) {
            // Prompt user for either breakfast/lunch/dinner
            mealInput = scanner.nextLine();
            for (Meal meal : filteredMeals) {
                if (meal.getMeal().equals(mealInput)) {
                    returnMeal = meal;
                    break;
                }
            }
            if (returnMeal != null) break;
            System.out.println("This meal doesn’t exist. Choose a meal from the list above.");

        }
        return returnMeal;
    }

    public static void printMealPlan(List<DailyMealPlan> mealPlanList) {
        for (DailyMealPlan mealPlan : mealPlanList) {
            System.out.println(String.format("""
                %s
                Breakfast: %s
                Lunch: %s
                Dinner: %s
                """, mealPlan.getDayOfWeek(),
                    mealPlan.getBreakfast().getMeal(),
                    mealPlan.getLunch().getMeal(),
                    mealPlan.getDinner().getMeal()));
        }
    }
}
