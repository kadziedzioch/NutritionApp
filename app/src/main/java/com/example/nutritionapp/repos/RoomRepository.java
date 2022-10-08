package com.example.nutritionapp.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.nutritionapp.dao.DayDao;
import com.example.nutritionapp.database.NutritionRoomDatabase;
import com.example.nutritionapp.entities.FoodEntity;
import com.example.nutritionapp.entities.MealEntity;
import com.example.nutritionapp.entities.relations.MealWithFoods;

import java.util.Date;
import java.util.List;

public class RoomRepository {

    private final DayDao dayDao;

    public RoomRepository(Application application) {
        NutritionRoomDatabase database = NutritionRoomDatabase.getDatabase(application);
        this.dayDao = database.dayDao();
    }


    public LiveData<List<MealWithFoods>> getMealWithFoodsList(Date date){
        return dayDao.getMealWithFoodsList(date);
    }

    public void insertMealWithFood(FoodEntity food, MealEntity meal){
        NutritionRoomDatabase.databaseWriterExecutor.execute(()->{
            dayDao.addMealWithFood(meal,food);
        });
    }

    public void insertFood(FoodEntity food){
        NutritionRoomDatabase.databaseWriterExecutor.execute(()->{
            dayDao.insertFood(food);
        });
    }

    public void deleteFood(FoodEntity food){
        NutritionRoomDatabase.databaseWriterExecutor.execute(()->{
            dayDao.deleteFood(food);
        });
    }

    public void deleteMeal(MealEntity meal){
        NutritionRoomDatabase.databaseWriterExecutor.execute(()->{
            dayDao.deleteMeal(meal);
        });
    }





}
