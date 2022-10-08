package com.example.nutritionapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.example.nutritionapp.entities.FoodEntity;
import com.example.nutritionapp.entities.MealEntity;
import com.example.nutritionapp.entities.relations.MealWithFoods;
import com.example.nutritionapp.models.MealName;
import com.example.nutritionapp.viewModel.MainActivityViewModel;
import com.example.nutritionapp.viewModel.SharedViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    MainActivityViewModel mainActivityViewModel;
    SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mainActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication()).create(MainActivityViewModel.class);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        //checking whether the user saw the intro activity
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime",true);
        if(isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstTime",false);
            editor.apply();
            Intent intent = new Intent(getApplicationContext(),IntroActivity.class);
            startActivity(intent);
        }

    }



}