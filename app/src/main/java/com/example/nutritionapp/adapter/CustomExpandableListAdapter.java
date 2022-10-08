package com.example.nutritionapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutritionapp.R;
import com.example.nutritionapp.entities.FoodEntity;
import com.example.nutritionapp.entities.MealEntity;
import com.example.nutritionapp.entities.relations.MealWithFoods;
import com.example.nutritionapp.listener.ListGroupListener;
import com.example.nutritionapp.models.Food;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<MealEntity> expandableListTitle;
    private LinkedHashMap<MealEntity, List<FoodEntity>> expandableListDetail;
    private ListGroupListener listGroupListener;


    public CustomExpandableListAdapter(Context context, List<MealEntity> expandableListTitle,
                                       LinkedHashMap<MealEntity, List<FoodEntity>> expandableListDetail,
                                       ListGroupListener listGroupListener) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.listGroupListener = listGroupListener;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        MealEntity meal = (MealEntity) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setAllCaps(true);
        listTitleTextView.setText(meal.mealName.toString());

        List<FoodEntity> foodList = this.expandableListDetail.get(this.expandableListTitle.get(listPosition));
        double totalKcal = 0;
        if(foodList !=null){
            for(FoodEntity food : foodList){
                totalKcal+=food.getCalories();
            }
        }
        TextView totalKcalTextView = (TextView) convertView
                .findViewById(R.id.totalKcalTextView);
        String kcalText = String.format(Locale.getDefault(),"%.0f kcal",totalKcal);
        totalKcalTextView.setText(kcalText);

        ImageView addFoodButton = convertView.findViewById(R.id.addFoodButton);
        addFoodButton.setOnClickListener(view -> {
            MealWithFoods mealWithFoods = new MealWithFoods();
            mealWithFoods.meal = meal;
            mealWithFoods.foods = this.expandableListDetail.get(this.expandableListTitle.get(listPosition));
            listGroupListener.onListGroupClicked(mealWithFoods);
        });
        return convertView;

    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final FoodEntity expandedListFood = (FoodEntity) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        TextView portionInfoTextView = (TextView) convertView
                .findViewById(R.id.portionInfoTextView);
        TextView kcalInfoTextView = (TextView) convertView
                .findViewById(R.id.kcalOfPortionTextView);
        expandedListTextView.setText(expandedListFood.foodName);

        String kcalInfo = String.format(Locale.getDefault(),"%.0f kcal",expandedListFood.getCalories());
        kcalInfoTextView.setText(kcalInfo);
        String portionInfo = "";
        if(expandedListFood.getGrams()==0){
            portionInfo =  String.format(Locale.getDefault(),"%.0f",expandedListFood.getQuantity()) +" x "+expandedListFood.getUnit();
        }else{
            portionInfo =  String.format(Locale.getDefault(),"%.0f g",expandedListFood.getGrams());
        }
        portionInfoTextView.setText(portionInfo);

        ImageButton deleteFoodButton = convertView.findViewById(R.id.deleteFoodButton);

        deleteFoodButton.setOnClickListener(view -> {
            MealWithFoods mealWithFoods = new MealWithFoods();
            mealWithFoods.meal = (MealEntity) getGroup(expandedListPosition);
            mealWithFoods.foods = this.expandableListDetail.get(this.expandableListTitle.get(expandedListPosition));
            listGroupListener.onListChildClicked(mealWithFoods,expandedListFood);
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


}
