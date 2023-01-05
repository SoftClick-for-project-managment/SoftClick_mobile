package com.job.softclick_mobile.services.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.job.softclick_mobile.App;
import com.job.softclick_mobile.services.http.interceptors.AuthInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    public static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            Interceptor authInterceptor = new AuthInterceptor(App.getInstance().getApplicationContext());

            // build OkHttpClient instance
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES)
                    .build();

            // Create Converter
            Gson gson = new GsonBuilder()
                    .setDateFormat("dd/MM/yyyy HH:mm")
                    .create();

            instance = new Retrofit.Builder()
//                    .baseUrl("http://192.168.43.91:8080/api/v1/")
//                    .baseUrl("http://192.168.1.104:8080/api/v1/")
//                    .baseUrl("http://192.168.1.80:8080/api/v1/")
                    .baseUrl("https://softclick-qa-api-app.azurewebsites.net/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return instance;
    }
}