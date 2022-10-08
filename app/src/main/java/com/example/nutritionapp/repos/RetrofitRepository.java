package com.example.nutritionapp.repos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nutritionapp.api.ApiClient;
import com.example.nutritionapp.api.ApiInterface;
import com.example.nutritionapp.models.Food;
import com.example.nutritionapp.models.Hit;
import com.example.nutritionapp.models.NutritionResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitRepository {

    private final MutableLiveData<ArrayList<Food>> data = new MutableLiveData<>();
    private static RetrofitRepository instance;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public static RetrofitRepository getInstance(){
        if(instance==null){
            instance = new RetrofitRepository();
        }
        return instance;
    }

    public LiveData<ArrayList<Food>> getData(){
        return data;
    }

    public void searchFood(String foodName){
        apiInterface.getData(foodName).enqueue(new Callback<NutritionResponse>() {
            @Override
            public void onResponse(Call<NutritionResponse> call, Response<NutritionResponse> response) {
                if(response.body() !=null && response.isSuccessful()){
                    NutritionResponse nutritionResponse = response.body();
                    ArrayList<Food> foodArrayList = new ArrayList<>();
                    for(Hit hit : nutritionResponse.getHits()){
                        foodArrayList.add(hit.getFields());
                    }
                    data.postValue(foodArrayList);
                }
            }
            @Override
            public void onFailure(Call<NutritionResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
