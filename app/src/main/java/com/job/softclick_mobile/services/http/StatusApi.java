package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StatusApi {
    @GET("status")
    Call<List<Status>> getAll();

    @GET("status/{id}")
    Call<Status> getSingle(@Path("id") Long statusId);

    @POST("status")
    Call create(@Body Status status);

    @PUT("status")
    Call update(@Body Status status);

    @PATCH("status/{id}")
    Call update(@Path("id") Long statusId, @Body Status status);

    @DELETE("status/{id}")
    Call delete(@Path("id") Long statusId);
}
