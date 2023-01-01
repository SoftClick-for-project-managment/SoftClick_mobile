package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaskApi {
    @GET("tasks")
    Call<List<Task>> getAll();

    @GET("tasks/{id}")
    Call<Task> getSingle(@Path("id") Long taskId);

    @POST("tasks")
    Call<Void> create(@Body Task task);

    @GET("tasks/project/{projectId}")
    Call<List<Task>> getAllByProject(@Path("projectId") Long projectId);

    @PUT("tasks/{id}")
    Call<Void> update(@Path("id") Long taskId, @Body Task task);

    @DELETE("tasks/{id}")
    Call<Void> delete(@Path("id") Long taskId);


}
