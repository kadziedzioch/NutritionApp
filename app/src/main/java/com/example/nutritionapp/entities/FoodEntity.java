package com.example.nutritionapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Food")
public class FoodEntity {

    @PrimaryKey(autoGenerate = true)
    public long foodId;
    public long mealId;
    public String foodName;
    public String brandName;
    public double calories;
    public double totalFat;
    public double totalCarbohydrate;
    public double totalProtein;
    public double quantity;
    public String unit;
    public double grams;

    public FoodEntity(String foodName, String brandName, double calories, double totalFat,
                      double totalCarbohydrate, double totalProtein, double quantity, String unit,
                      double grams) {
        this.foodName = foodName;
        this.brandName = brandName;
        this.calories = calories;
        this.totalFat = totalFat;
        this.totalCarbohydrate = totalCarbohydrate;
        this.totalProtein = totalProtein;
        this.quantity = quantity;
        this.unit = unit;
        this.grams = grams;
    }

    public FoodEntity() {
    }

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public double getTotalCarbohydrate() {
        return totalCarbohydrate;
    }

    public void setTotalCarbohydrate(double totalCarbohydrate) {
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public double getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(double totalProtein) {
        this.totalProtein = totalProtein;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getGrams() {
        return grams;
    }

    public void setGrams(double grams) {
        this.grams = grams;
    }
}
