package com.job.softclick_mobile.services.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    public static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
//            // Define the interceptor, add authentication headers
//            Interceptor interceptor = new Interceptor() {
//                @Override
//                public okhttp3.Response intercept(Chain chain) throws IOException {
//                    Request newRequest = chain.request().newBuilder().addHeader("User-Agent", "Retrofit-Sample-App").build();
//                    return chain.proceed(newRequest);
//                }
//            };
//
//            // Add the interceptor to OkHttpClient
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.interceptors().add(interceptor);
//            OkHttpClient client = builder.build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("dd/MM/yyyy HH:mm")
                    .create();

            instance = new Retrofit.Builder()
                    .baseUrl("http://192.168.43.176:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .client(client)
                    .build();
        }
        return instance;
    }
}