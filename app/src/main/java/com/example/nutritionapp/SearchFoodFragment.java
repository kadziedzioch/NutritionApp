package com.example.nutritionapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nutritionapp.adapter.RecyclerViewAdapter;
import com.example.nutritionapp.entities.relations.MealWithFoods;
import com.example.nutritionapp.listener.RecyclerItemListener;
import com.example.nutritionapp.models.Food;
import com.example.nutritionapp.utils.Util;
import com.example.nutritionapp.viewModel.MainActivityViewModel;
import com.example.nutritionapp.viewModel.SharedViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SearchFoodFragment extends Fragment implements RecyclerItemListener {

    ProgressBar progressBar;
    EditText foodNameEditText;
    ImageButton searchFoodButton;
    RecyclerView recyclerView;
    TextView noDataInfo, nameOfMeal, dateOfMeal;
    MainActivityViewModel mainActivityViewModel;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Food> foodArrayList;
    private SharedViewModel sharedViewModel;

    public SearchFoodFragment() {

    }

    public static SearchFoodFragment newInstance() {
        SearchFoodFragment fragment = new SearchFoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodArrayList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_food, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        foodNameEditText = view.findViewById(R.id.foodNameEditText);
        nameOfMeal = view.findViewById(R.id.nameOfMealTextView);
        dateOfMeal = view.findViewById(R.id.dateOfMealTextView);
        searchFoodButton = view.findViewById(R.id.searchFoodButton);
        noDataInfo = view.findViewById(R.id.noDataTextView);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivityViewModel = new ViewModelProvider(requireActivity())
                .get(MainActivityViewModel.class);
        mainActivityViewModel.init();
        sharedViewModel = new ViewModelProvider(requireActivity())
                .get(SharedViewModel.class);
        noDataInfo.setText(R.string.noDataInfo);
        sharedViewModel.getMealWithFoods().observe(requireActivity(), new Observer<MealWithFoods>() {
            @Override
            public void onChanged(MealWithFoods mealWithFoods) {
                nameOfMeal.setText(mealWithFoods.meal.mealName.toString());
                Date date = mealWithFoods.meal.dateOfDay;
                DateFormat dateFormat = DateFormat.getDateInstance();
                dateOfMeal.setText(dateFormat.format(date));

            }
        });
        mainActivityViewModel.getFood().observe(requireActivity(), new Observer<ArrayList<Food>>() {
            @Override
            public void onChanged(ArrayList<Food> foods) {
                if(foods !=null){
                    foodArrayList = foods;
                    recyclerViewAdapter = new RecyclerViewAdapter(foodArrayList, SearchFoodFragment.this);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    progressBar.setVisibility(View.GONE);
                    noDataInfo.setText("");
                }
                else{
                    noDataInfo.setText(R.string.noDataInfo);
                }
            }
        });

        searchFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(foodNameEditText.getText().toString())){
                    progressBar.setVisibility(View.VISIBLE);
                    noDataInfo.setText("");
                    mainActivityViewModel.searchFood(foodNameEditText.getText().toString());
                    Util.hideSoftKeyboard(getActivity().getCurrentFocus());
                }
            }
        });

    }

    @Override
    public void onRecyclerItemClicked(Food food) {
        sharedViewModel.selectFood(food);
        NavHostFragment.findNavController(SearchFoodFragment.this)
                .navigate(R.id.action_searchFoodFragment2_to_addFoodFragment2);

    }
}