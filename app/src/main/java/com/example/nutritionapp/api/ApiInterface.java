package com.example.nutritionapp.api;

import com.example.nutritionapp.models.NutritionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("search/{foodName}")
    Call<NutritionResponse> getData(@Path("foodName") String foodName);

}
