package com.example.nutritionapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.nutritionapp.entities.FoodEntity;
import com.example.nutritionapp.entities.MealEntity;
import com.example.nutritionapp.entities.relations.MealWithFoods;
import com.example.nutritionapp.models.Food;
import com.example.nutritionapp.models.MealName;
import com.example.nutritionapp.repos.RetrofitRepository;
import com.example.nutritionapp.repos.RoomRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private RetrofitRepository retrofitRepository;
    private LiveData<ArrayList<Food>> foodList;

    MutableLiveData<Date> dateOfMeals = new MutableLiveData<>();

    private LiveData<List<MealWithFoods>> mealWithFoodsList;

    private static RoomRepository roomRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        roomRepository = new RoomRepository(application);
        dateOfMeals.setValue(Calendar.getInstance().getTime());
        mealWithFoodsList = Transformations.switchMap(dateOfMeals, date ->
            roomRepository.getMealWithFoodsList(date)
        );
    }

    public void init(){
        retrofitRepository = RetrofitRepository.getInstance();
        foodList = retrofitRepository.getData();

    }

    public void setDateOfMeals(Date date){
        dateOfMeals.setValue(date);
    }


    public LiveData<ArrayList<Food>> getFood(){
        return foodList;
    }

    public void searchFood(String foodName){
        retrofitRepository.searchFood(foodName);
    }

    public LiveData<List<MealWithFoods>> getMealWithFoodsList(){
        return mealWithFoodsList;
    }


    public static void insertMealWithFood(FoodEntity food, MealEntity meal){
        roomRepository.insertMealWithFood(food,meal);
    }

    public static void insertMeal(FoodEntity food){
        roomRepository.insertFood(food);
    }

    public static void deleteFood(FoodEntity food){
        roomRepository.deleteFood(food);
    }
    public static void deleteMeal(MealEntity meal){
        roomRepository.deleteMeal(meal);
    }

    public LinkedHashMap<MealEntity, List<FoodEntity>> getExpandibleListData(List<MealWithFoods> mealWithFoodsList, Date date){
        MealEntity breakfastMeal = new MealEntity();
        breakfastMeal.setMealName(MealName.Breakfast);
        breakfastMeal.setDateOfDay(date);
        MealEntity brunchMeal = new MealEntity();
        brunchMeal.setMealName(MealName.Brunch);
        brunchMeal.setDateOfDay(date);
        MealEntity lunchMeal = new MealEntity();
        lunchMeal.setMealName(MealName.Lunch);
        lunchMeal.setDateOfDay(date);
        MealEntity dinerMeal = new MealEntity();
        dinerMeal.setMealName(MealName.Dinner);
        dinerMeal.setDateOfDay(date);
        MealEntity snackMeal = new MealEntity();
        snackMeal.setMealName(MealName.Snack);
        snackMeal.setDateOfDay(date);
        MealEntity supperMeal = new MealEntity();
        supperMeal.setDateOfDay(date);
        supperMeal.setMealName(MealName.Supper);

        List<FoodEntity> breakfastList = new ArrayList<>();
        List<FoodEntity> brunchList = new ArrayList<>();
        List<FoodEntity> lunchList = new ArrayList<>();
        List<FoodEntity> dinerList = new ArrayList<>();
        List<FoodEntity> snackList = new ArrayList<>();
        List<FoodEntity> supperList = new ArrayList<>();

        for(MealWithFoods mealWithFoods: mealWithFoodsList){

            if(mealWithFoods.meal.mealName == breakfastMeal.mealName){
                breakfastMeal.setMealId(mealWithFoods.meal.getMealId());
                breakfastList.addAll(mealWithFoods.foods);
            }
            else if(mealWithFoods.meal.mealName == brunchMeal.mealName){
                brunchMeal.setMealId(mealWithFoods.meal.getMealId());
                brunchList.addAll(mealWithFoods.foods);
            }
            else if(mealWithFoods.meal.mealName == lunchMeal.mealName){
                lunchMeal.setMealId(mealWithFoods.meal.getMealId());
                lunchList.addAll(mealWithFoods.foods);
            }
            else if(mealWithFoods.meal.mealName == dinerMeal.mealName){
                dinerMeal.setMealId(mealWithFoods.meal.getMealId());
                dinerList.addAll(mealWithFoods.foods);
            }
            else if(mealWithFoods.meal.mealName == snackMeal.mealName){
                snackMeal.setMealId(mealWithFoods.meal.getMealId());
                snackList.addAll(mealWithFoods.foods);
            }
            else if(mealWithFoods.meal.mealName == supperMeal.mealName){
                supperMeal.setMealId(mealWithFoods.meal.getMealId());
                supperList.addAll(mealWithFoods.foods);
            }
        }
        LinkedHashMap<MealEntity, List<FoodEntity>> expandableListData = new LinkedHashMap<MealEntity, List<FoodEntity>>();
        expandableListData.put(breakfastMeal,breakfastList);
        expandableListData.put(brunchMeal,brunchList);
        expandableListData.put(lunchMeal,lunchList);
        expandableListData.put(dinerMeal,dinerList);
        expandableListData.put(snackMeal,snackList);
        expandableListData.put(supperMeal,supperList);

        return expandableListData;

    }






}
