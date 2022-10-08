package com.example.nutritionapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nutritionapp.databinding.FragmentAddFoodBinding;
import com.example.nutritionapp.entities.FoodEntity;
import com.example.nutritionapp.entities.MealEntity;
import com.example.nutritionapp.entities.relations.MealWithFoods;
import com.example.nutritionapp.models.Food;
import com.example.nutritionapp.viewModel.MainActivityViewModel;
import com.example.nutritionapp.viewModel.SharedViewModel;

import java.util.Locale;

public class AddFoodFragment extends Fragment implements View.OnClickListener {

private SharedViewModel sharedViewModel;
private MainActivityViewModel mainActivityViewModel;
private FragmentAddFoodBinding fragmentAddFoodBinding;
private MealWithFoods mealWithFood;
private Food foodFromDb;

    public AddFoodFragment() {
    }


    public static AddFoodFragment newInstance() {
        AddFoodFragment fragment = new AddFoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getFood().observe(requireActivity(), new Observer<Food>() {
            @Override
            public void onChanged(Food food) {
                foodFromDb = food;
                fragmentAddFoodBinding.setFood(food);
                String foodName = food.getItem_name()+", "+food.getBrand_name();
                fragmentAddFoodBinding.foodNameTextView.setText(foodName);
                String onePortionQty =  String.format(Locale.getDefault(),"%.1f", food.getNf_serving_size_qty()) +" x " + food.getNf_serving_size_unit();
                fragmentAddFoodBinding.onePortionInfoTextView.setText(onePortionQty);
                String onePortionKcal = String.format(Locale.getDefault(),"%.0f", food.getNf_calories())+"kcal";
                fragmentAddFoodBinding.onePortionKcalTextView.setText(onePortionKcal);
                String onePortionGram = String.format(Locale.getDefault(),"%.1f", food.getNf_serving_weight_grams())+"g";
                fragmentAddFoodBinding.onePortionGramTextView.setText(onePortionGram);

                String twoPortionQty = String.format(Locale.getDefault(),"%.1f", food.getNf_serving_size_qty()*2) +" x " + food.getNf_serving_size_unit();
                fragmentAddFoodBinding.twoPortionsInfoTextView.setText(twoPortionQty);
                String twoPortionKcal = String.format(Locale.getDefault(),"%.0f", food.getNf_calories()*2) +"kcal";
                fragmentAddFoodBinding.twoPortionsKcalTextView.setText(twoPortionKcal);
                String twoPortionGram = String.format(Locale.getDefault(),"%.1f", food.getNf_serving_weight_grams()*2)+"g";
                fragmentAddFoodBinding.twoPortionsGramTextView.setText(twoPortionGram);

                if(food.getNf_serving_weight_grams()==0){
                    fragmentAddFoodBinding.onePortionGramTextView.setVisibility(View.INVISIBLE);
                    fragmentAddFoodBinding.twoPortionsGramTextView.setVisibility(View.INVISIBLE);
                    fragmentAddFoodBinding.unitTextView.setText(food.getNf_serving_size_unit());
                }
                else{
                    fragmentAddFoodBinding.unitTextView.setText("g");
                }
                String nutritionalFactsText = "Nutritional facts per "+ String.format(Locale.getDefault(),"%.0f",food.getNf_serving_size_qty())+" "+ food.getNf_serving_size_unit();
                fragmentAddFoodBinding.nutritionalFactsTextView.setText(nutritionalFactsText);
                fragmentAddFoodBinding.energeticValueTextView.setText(String.format(Locale.getDefault(),"%.0f",food.getNf_calories()));
                fragmentAddFoodBinding.proteinValueTextView.setText(String.format(Locale.getDefault(),"%.1f",food.getNf_protein()));
                fragmentAddFoodBinding.carbohydratesValueTextView.setText(String.format(Locale.getDefault(),"%.1f",food.getNf_total_carbohydrate()));
                fragmentAddFoodBinding.fatsValueTextView.setText(String.format(Locale.getDefault(),"%.1f",food.getNf_total_fat()));



                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(!TextUtils.isEmpty(editable.toString())){
                            double portion = Double.parseDouble(editable.toString());
                            double kcal;
                            if(food.getNf_serving_weight_grams()==0){
                                kcal = portion*food.getNf_calories()/food.getNf_serving_size_qty();
                            }
                            else{
                                kcal = portion*food.getNf_calories()/food.getNf_serving_weight_grams();
                            }
                            fragmentAddFoodBinding.customPortionKcalTextView.setText(String.format(Locale.getDefault(),"%.0f kcal",kcal));
                        }
                        else{
                            fragmentAddFoodBinding.customPortionKcalTextView.setText(R.string.noKcalInfo);
                        }
                    }
                };
                fragmentAddFoodBinding.customPortionEditText.addTextChangedListener(textWatcher);
            }
        });

        sharedViewModel.getMealWithFoods().observe(requireActivity(), new Observer<MealWithFoods>() {
            @Override
            public void onChanged(MealWithFoods mealWithFoods) {
                mealWithFood = mealWithFoods;
            }
        });

        fragmentAddFoodBinding.onePortionButton.setOnClickListener(this);
        fragmentAddFoodBinding.twoPortionTextButton.setOnClickListener(this);
        fragmentAddFoodBinding.customPortionButton.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentAddFoodBinding = FragmentAddFoodBinding.inflate(inflater,container,false);
        return fragmentAddFoodBinding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.onePortionButton:
                saveOnePortionOfFood();
                break;
            case R.id.twoPortionTextButton:
                saveTwoPortionsOfFood();
                break;
            case R.id.customPortionButton:
                saveCustomPortionOdFood();
                break;
        }
        NavHostFragment.findNavController(AddFoodFragment.this)
                .navigate(R.id.action_addFoodFragment2_to_mainFragment);

    }

    public void saveOnePortionOfFood(){
        if(mealWithFood!=null && foodFromDb !=null){
            MealEntity meal = mealWithFood.meal;
            FoodEntity food = new FoodEntity();
            food.setFoodName(foodFromDb.getItem_name());
            food.setBrandName(foodFromDb.getBrand_name());
            food.setCalories(foodFromDb.getNf_calories());
            food.setGrams(foodFromDb.getNf_serving_weight_grams());
            food.setQuantity(foodFromDb.getNf_serving_size_qty());
            food.setTotalCarbohydrate(foodFromDb.getNf_total_carbohydrate());
            food.setTotalFat(foodFromDb.getNf_total_fat());
            food.setTotalProtein(foodFromDb.getNf_protein());
            food.setUnit(foodFromDb.getNf_serving_size_unit());

            if(mealWithFood.foods.size()==0){
                MainActivityViewModel.insertMealWithFood(food,meal);
            }
            else{
                food.setMealId(mealWithFood.meal.getMealId());
                MainActivityViewModel.insertMeal(food);
            }
        }
    }

    public void saveTwoPortionsOfFood(){
        if(mealWithFood!=null && foodFromDb !=null){
            MealEntity meal = mealWithFood.meal;
            FoodEntity food = new FoodEntity();
            food.setFoodName(foodFromDb.getItem_name());
            food.setBrandName(foodFromDb.getBrand_name());
            food.setCalories(foodFromDb.getNf_calories()*2);
            food.setGrams(foodFromDb.getNf_serving_weight_grams()*2);
            food.setQuantity(foodFromDb.getNf_serving_size_qty()*2);
            food.setTotalCarbohydrate(foodFromDb.getNf_total_carbohydrate()*2);
            food.setTotalFat(foodFromDb.getNf_total_fat()*2);
            food.setTotalProtein(foodFromDb.getNf_protein()*2);
            food.setUnit(foodFromDb.getNf_serving_size_unit());

            if(mealWithFood.foods.size()==0){
                MainActivityViewModel.insertMealWithFood(food,meal);
            }
            else{
                food.setMealId(mealWithFood.meal.getMealId());
                MainActivityViewModel.insertMeal(food);
            }
        }
    }

    public void saveCustomPortionOdFood(){

        if(mealWithFood!=null && foodFromDb !=null){
            MealEntity meal = mealWithFood.meal;
            FoodEntity food = new FoodEntity();
            food.setFoodName(foodFromDb.getItem_name());
            food.setBrandName(foodFromDb.getBrand_name());
            food.setUnit(foodFromDb.getNf_serving_size_unit());

            if(!TextUtils.isEmpty(fragmentAddFoodBinding.customPortionEditText.getText().toString())){

                double portion = Double.parseDouble(fragmentAddFoodBinding.customPortionEditText.getText().toString());
                double kcal,carbs,fats,protein;
                if(foodFromDb.getNf_serving_weight_grams()==0){
                    kcal = portion*foodFromDb.getNf_calories()/foodFromDb.getNf_serving_size_qty();
                    carbs = portion*foodFromDb.getNf_total_carbohydrate()/foodFromDb.getNf_serving_size_qty();
                    fats =  portion*foodFromDb.getNf_total_fat()/foodFromDb.getNf_serving_size_qty();
                    protein = portion*foodFromDb.getNf_protein()/foodFromDb.getNf_serving_size_qty();
                    food.setGrams(0);
                    food.setQuantity(portion);
                }
                else{
                    kcal = portion*foodFromDb.getNf_calories()/foodFromDb.getNf_serving_weight_grams();
                    food.setGrams(portion);
                    carbs = portion*foodFromDb.getNf_total_carbohydrate()/foodFromDb.getNf_serving_weight_grams();
                    fats =  portion*foodFromDb.getNf_total_fat()/foodFromDb.getNf_serving_weight_grams();
                    protein = portion*foodFromDb.getNf_protein()/foodFromDb.getNf_serving_weight_grams();
                    food.setQuantity(0);
                }
                food.setCalories(kcal);
                food.setTotalCarbohydrate(carbs);
                food.setTotalFat(fats);
                food.setTotalProtein(protein);

                if(mealWithFood.foods.size()==0){
                    MainActivityViewModel.insertMealWithFood(food,meal);
                }
                else{
                    food.setMealId(mealWithFood.meal.getMealId());
                    MainActivityViewModel.insertMeal(food);
                }
            }
            else{
                Toast.makeText(requireActivity(),"Fill in the measure!", Toast.LENGTH_SHORT).show();
            }

        }

    }
}