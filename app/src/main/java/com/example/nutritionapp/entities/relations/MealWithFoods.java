package com.example.nutritionapp.entities.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.nutritionapp.entities.FoodEntity;
import com.example.nutritionapp.entities.MealEntity;

import java.util.List;

public class MealWithFoods {
    @Embedded
    public MealEntity meal;
    @Relation(
            parentColumn = "mealId",
            entityColumn = "mealId"
    )
    public List<FoodEntity> foods;
}
