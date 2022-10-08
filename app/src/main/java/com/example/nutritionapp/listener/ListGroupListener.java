package com.example.nutritionapp.listener;

import com.example.nutritionapp.entities.FoodEntity;
import com.example.nutritionapp.entities.relations.MealWithFoods;

public interface ListGroupListener {
    void onListGroupClicked(MealWithFoods mealWithFoods);

    void onListChildClicked(MealWithFoods mealWithFoods, FoodEntity foodEntity);
}
