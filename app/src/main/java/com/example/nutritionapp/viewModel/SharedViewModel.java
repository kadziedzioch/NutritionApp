package com.example.nutritionapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nutritionapp.entities.relations.MealWithFoods;
import com.example.nutritionapp.models.Food;

public class SharedViewModel extends ViewModel {

    MutableLiveData<Food> selectedFood = new MutableLiveData<>();
    public void selectFood(Food food){
        selectedFood.setValue(food);
    }
    public LiveData<Food> getFood(){
        return selectedFood;
    }

    MutableLiveData<MealWithFoods> selectedMealWithFoods  = new MutableLiveData<>();
     public void selectMealWithFoods(MealWithFoods mealWithFoods){
         selectedMealWithFoods.setValue(mealWithFoods);
     }
     public LiveData<MealWithFoods> getMealWithFoods(){
         return selectedMealWithFoods;
     }


}
