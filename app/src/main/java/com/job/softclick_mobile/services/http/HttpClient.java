package com.job.softclick_mobile.services.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    public static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            // Define the interceptor, add authentication headers
            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain
                            .request()
                            .newBuilder()
                            .addHeader("User-Agent", "SoftClick-Android-App")
                            .build();
                    return chain.proceed(newRequest);
                }
            };

            // build OkHttpClient instance
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES)
                    .build();

            // Create Converter
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();

            instance = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.109:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return instance;
    }
}