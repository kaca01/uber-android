package com.example.ubermobileapp.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static OkHttpClient createClient(){
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + AuthService.getToken())
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
    }

    public static Retrofit getClient(String url){
        return new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(createClient())
                    .build();
    }
}
