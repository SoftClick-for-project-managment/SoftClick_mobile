package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Domain;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DomainApi {
    @GET("domains")
    Call<List<Domain>> getAll();

    @GET("domains/{id}")
    Call<Domain> getSingle(@Path("id") Long domainId);

    @POST("domains")
    Call<Void> create(@Body Domain domain);

    @PUT("domains/{id}")
    Call<Void> update(@Path("id") Long domainId, @Body Domain domain);

    @DELETE("domains/{id}")
    Call<Void> delete(@Path("id") Long domainId);
}
