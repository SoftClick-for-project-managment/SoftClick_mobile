package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Skill;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SkillApi {
    @GET("skills")
    Call<List<Skill>> getAll();

    @GET("skills/{id}")
    Call<Skill> getSingle(@Path("id") Long skillId);

    @POST("skills")
    Call<Void> create(@Body Skill skill);

    @PUT("skills/{id}")
    Call<Void> update(@Path("id") Long skillId, @Body Skill skill);

    @DELETE("skills/{id}")
    Call<Void> delete(@Path("id") Long skillId);
}
