package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.TokenPeer;
import com.job.softclick_mobile.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @POST("/login")
    Call<TokenPeer> login(@Body User user);

    @GET("users/{id}")
    Call<User> getSingleById(@Path("id") Long id);

    @PATCH("users/{id}")
    Call<Void> update(@Path("id") Long id, @Body User user);

    @GET("users/details")
    Call<User> getAuthenticated();

    @POST("users")
    Call<Void> create(@Body User user);

    @GET("users/verify")
    Call<Void> verify(@Body String code);
}
