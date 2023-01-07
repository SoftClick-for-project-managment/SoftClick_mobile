package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientApi {
    @GET("clients")
    Call<List<Client>> getAll();

    @GET("clients/{id}")
    Call<Client> getSingle(@Path("id") Long clientId);

    @POST("clients")
    Call<Void> create(@Body Client client);

    @PUT("clients/{id}")
    Call<Void> update(@Path("id") Long clientId, @Body Client client);

    @DELETE("clients/{id}")
    Call<Void> delete(@Path("id") Long clientId);

    @POST("clients/search")
    Call<List<Client>> search(@Body Client client);
}
