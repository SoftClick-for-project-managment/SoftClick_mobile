package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.TokenPeer;
import com.job.softclick_mobile.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @POST("/login")
    Call<TokenPeer> login(@Body String username, @Body String password);

    @GET("/users/{id}")
    Call<User> getSingleById(@Path("id") Long id);

    @GET("/users/{username}")
    Call<User> getSingleByUsername(@Path("username") String username);

    @POST("/users/create")
    Call create(@Body User user);

    @GET("/users/verify")
    Call verify(@Body String code);
}
