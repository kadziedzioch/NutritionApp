package com.example.nutritionapp.api;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        final String appKey = "my api key";
        final String appId = "my app id";
        final String fields = "item_name,brand_name,item_id,nf_calories,nf_calories,nf_serving_size_qty,nf_serving_size_unit,nf_serving_weight_grams,metric_qty,metric_uom,nf_total_fat,nf_protein,nf_total_carbohydrate";
        final String results = "0:20";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl httpUrl = original.url();

                HttpUrl newHttpUrl = httpUrl.newBuilder()
                        .addQueryParameter("results",results)
                        .addQueryParameter("fields",fields)
                        .addQueryParameter("appKey",appKey)
                        .addQueryParameter("appId",appId)
                        .build();
                Log.d("TAG", "intercept: "+ newHttpUrl.toString());
                Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).addInterceptor(logging).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nutritionix.com/v1_1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }


}
