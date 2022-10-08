package com.example.nutritionapp;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nutritionapp.adapter.CalendarAdapter;
import com.example.nutritionapp.adapter.CustomExpandableListAdapter;
import com.example.nutritionapp.entities.FoodEntity;
import com.example.nutritionapp.entities.MealEntity;
import com.example.nutritionapp.entities.relations.MealWithFoods;
import com.example.nutritionapp.listener.CalendarItemListener;
import com.example.nutritionapp.listener.ListGroupListener;
import com.example.nutritionapp.models.Food;
import com.example.nutritionapp.models.MealName;
import com.example.nutritionapp.utils.CalendarUtils;
import com.example.nutritionapp.viewModel.MainActivityViewModel;
import com.example.nutritionapp.viewModel.SharedViewModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment implements ListGroupListener, CalendarItemListener, View.OnClickListener {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<MealEntity> expandableListTitle;
    private LinkedHashMap<MealEntity, List<FoodEntity>> expandableListDetail;
    private MainActivityViewModel mainActivityViewModel;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private TextView kcalInfo,proteinInfo,fatsInfo,carbsInfo;
    private SharedViewModel sharedViewModel;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        expandableListView = view.findViewById(R.id.expandableListView);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        previousButton = view.findViewById(R.id.previousWeekButton);
        nextButton = view.findViewById(R.id.nextWeekButton);
        kcalInfo = view.findViewById(R.id.kcalBottomSheetTextView);
        proteinInfo = view.findViewById(R.id.protenBottomSheetTextView);
        fatsInfo = view.findViewById(R.id.fatsBottomSheetTextView);
        carbsInfo = view.findViewById(R.id.carbsBottomSheetTextView);
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


        setWeekView();

        mainActivityViewModel.getMealWithFoodsList().observe(requireActivity(), new Observer<List<MealWithFoods>>() {
            @Override
            public void onChanged(List<MealWithFoods> mealWithFoods) {
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Date selectedDate = Date.from(CalendarUtils.selectedDate.atStartOfDay(defaultZoneId).toInstant());
                expandableListDetail = mainActivityViewModel.getExpandibleListData(mealWithFoods,selectedDate);
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail, MainFragment.this);
                expandableListView.setAdapter(expandableListAdapter);
                setBottomSheet(mealWithFoods);
            }
        });

        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

    }

    public void setWeekView(){

        monthYearText.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = CalendarUtils.daysInWeekArray(CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, MainFragment.this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireActivity(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }
    @Override
    public void onListGroupClicked(MealWithFoods mealWithFoods) {
        sharedViewModel.selectMealWithFoods(mealWithFoods);
        NavHostFragment.findNavController(MainFragment.this)
                .navigate(R.id.action_mainFragment_to_searchFoodFragment22);

    }
    @Override
    public void onListChildClicked(MealWithFoods mealWithFoods, FoodEntity foodEntity) {
        MainActivityViewModel.deleteFood(foodEntity);
        if(mealWithFoods.foods.size() ==1){
            MainActivityViewModel.deleteMeal(mealWithFoods.meal);
        }
    }

    @Override
    public void onCalendarCellClicked(int position, LocalDate localDate) {
        CalendarUtils.selectedDate = localDate;
        setWeekView();
        changeDate(localDate);
    }

    public void changeDate(LocalDate localDate){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        mainActivityViewModel.setDateOfMeals(date);
    }

    public void setBottomSheet(List<MealWithFoods> mealWithFoods){
        double kcal=0,fats=0,carbs=0,protein = 0;
        for(MealWithFoods meal: mealWithFoods){
            for(FoodEntity food: meal.foods){
                kcal+=food.calories;
                fats+=food.totalFat;
                carbs+=food.totalCarbohydrate;
                protein+=food.totalProtein;
            }
        }
        kcalInfo.setText(String.format(Locale.getDefault(),"%.0f", kcal));
        fatsInfo.setText(String.format(Locale.getDefault(),"%.0f g", fats));
        carbsInfo.setText(String.format(Locale.getDefault(),"%.0f g", carbs));
        proteinInfo.setText(String.format(Locale.getDefault(),"%.0f g", protein));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.previousWeekButton:
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
                setWeekView();
                changeDate(CalendarUtils.selectedDate);
                break;
            case R.id.nextWeekButton:
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
                setWeekView();
                changeDate(CalendarUtils.selectedDate);
                break;
        }
    }
}