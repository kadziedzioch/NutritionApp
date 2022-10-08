package com.example.nutritionapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.nutritionapp.dao.DayDao;
import com.example.nutritionapp.entities.FoodEntity;
import com.example.nutritionapp.entities.MealEntity;
import com.example.nutritionapp.utils.Converter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {FoodEntity.class, MealEntity.class},
version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class NutritionRoomDatabase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS =4;
    public static volatile NutritionRoomDatabase INSTANCE;
    public static final String DATABASE_NAME ="nutrition_database";
    public static final ExecutorService databaseWriterExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public abstract DayDao dayDao();

    public static NutritionRoomDatabase getDatabase(final Context context){
        if(INSTANCE ==null){
            synchronized (NutritionRoomDatabase.class){
                if(INSTANCE ==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NutritionRoomDatabase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }
        INSTANCE.getOpenHelper().getWritableDatabase();
        return INSTANCE;
    }


}
