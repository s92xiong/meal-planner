# Meal Planner Java Application

## Overview

The Meal Planner Java Application is a command-line tool that allows users to plan meals for the week, store meal information in a database, generate a weekly meal plan, and create a shopping list based on the planned meals.

## Features

1. **Add Meals**: You can add meals to the application, including specifying their category, name, and necessary ingredients.

2. **Show Meals**: View a list of all saved meals and filter them by category.

3. **Store in Database**: Meals are stored in a database, allowing you to access them even after closing and reopening the application.

4. **Plan Meals**: Plan meals for the week using the "plan" command, creating a weekly meal schedule.

5. **Generate Shopping List**: The application can generate a shopping list containing all the required ingredients for the planned meals and save it to a file.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- PostgreSQL database (configured in `application.properties`)
- Gradle (for building the project)

### Installation

1. Clone this repository:

   ```
   git clone https://github.com/yourusername/meal-planner.git
   ```

2. Configure your PostgreSQL database connection in `application.properties`.

3. Build the project:

   ```
   cd meal-planner
   ./gradlew build
   ```

4. Run the application:

   ```
   ./gradlew bootRun
   ```

## Usage

The application provides a menu-driven interface. Use the following commands:

- `add`: Add a new meal.
- `show`: View saved meals.
- `plan`: Plan meals for the week.
- `save`: Generate a shopping list based on the weekly plan.
- `exit`: Exit the application.


