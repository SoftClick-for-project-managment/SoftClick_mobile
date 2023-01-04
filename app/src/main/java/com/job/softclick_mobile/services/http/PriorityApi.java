package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Priority;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PriorityApi {
    @GET("priorities")
    Call<List<Priority>> getAll();

    @GET("priorities/{id}")
    Call<Priority> getSingle(@Path("id") Long priorityId);

    @POST("priorities")
    Call create(@Body Priority priority);

    @PUT("priorities")
    Call update(@Body Priority priority);

    @PATCH("priorities/{id}")
    Call update(@Path("id") Long priorityId, @Body Priority priority);

    @DELETE("priorities/{id}")
    Call delete(@Path("id") Long priorityId);
}
