package com.example.nutritionapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.nutritionapp.entities.FoodEntity;
import com.example.nutritionapp.entities.MealEntity;
import com.example.nutritionapp.entities.relations.MealWithFoods;

import java.util.Date;
import java.util.List;

@Dao
public abstract class DayDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertMeal(MealEntity meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertFood(FoodEntity food);

    @Transaction
    public void addMealWithFood(MealEntity meal, FoodEntity food){
        food.mealId = insertMeal(meal);
        insertFood(food);
    }

    @Transaction
    @Query("SELECT * FROM Meal WHERE dateOfDay==:date")
    public abstract LiveData<List<MealWithFoods>> getMealWithFoodsList(Date date);

    @Delete
    public abstract void deleteFood(FoodEntity foodEntity);

    @Delete
    public abstract void deleteMeal(MealEntity mealEntity);


}
