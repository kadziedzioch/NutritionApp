package com.example.nutritionapp.utils;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.nutritionapp.models.MealName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {
    @TypeConverter
    public static Date fromTimestamp(String value){

        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        try {
            return value == null ? null: dateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static String dateToTimestamp(Date date){

        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        return date == null ? null: dateFormat.format(date);
        //return date == null ? null: date.getTime();
    }

    @TypeConverter
    public static String fromMealName(MealName mealName){
        return mealName==null?null:mealName.name();
    }
    @TypeConverter
    public static MealName toMealName(String mealName){
        return mealName==null?null: MealName.valueOf(mealName);
    }
}
