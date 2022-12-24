package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TeamsApi {
    @GET("teams")
    Call<List<Team>> getAll();

    @GET("teams/{id}")
    Call<Team> getSingle(@Path("id") Long teamId);

    @POST("teams")
    Call create(@Body Team teams);

    @PUT("teams")
    Call update(@Body Team teams);

    @PATCH("teams/{id}")
    Call update(@Path("id") Long teamId, @Body Team team);

    @DELETE("teams/{id}")
    Call delete(@Path("id") Long teamId);
}
