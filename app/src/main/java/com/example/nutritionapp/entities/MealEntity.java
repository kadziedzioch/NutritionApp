package com.example.nutritionapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.nutritionapp.models.MealName;

import java.util.Date;

@Entity(tableName = "Meal")
public class MealEntity {

    @PrimaryKey(autoGenerate = true)
    public long mealId;
    public MealName mealName;
    public Date dateOfDay;


    public MealEntity(MealName mealName, Date dateOfDay) {

        this.mealName = mealName;
        this.dateOfDay = dateOfDay;
    }

    public MealEntity() {
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public MealName getMealName() {
        return mealName;
    }

    public void setMealName(MealName mealName) {
        this.mealName = mealName;
    }

    public Date getDateOfDay() {
        return dateOfDay;
    }

    public void setDateOfDay(Date dateOfDay) {
        this.dateOfDay = dateOfDay;
    }

}
