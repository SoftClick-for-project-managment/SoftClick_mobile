package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Project;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface ProjectApi {
    @GET("projects")
    Call<List<Project>> getAll();

    @GET("projects/{id}")
    Call<Project> getSingle(@Path("id") Long projectId);

    @POST("projects")
    Call<ResponseBody> create(@Body Project project);

    @PUT("projects/{id}")
    Call<ResponseBody> update(@Path("id") Long projectId, @Body Project project);

    @PATCH("projects/{id}")
    Call<ResponseBody> patch(@Path("id") Long projectId, @Body Project project);

    @DELETE("projects/{id}")
    Call<ResponseBody>  delete(@Path("id") Long projectId);
}
