package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeApi {

    @GET("employees")
    Call<List<Employee>> getAll();

    @GET("employees/{id}")
    Call<Employee> getSingle(@Path("id") Long employeeId);

    @POST("employees")
    Call create(@Body Employee employee);

    @PUT("employees/{id}")
    Call update(@Path("id") Long employeeId, @Body Employee employee);

    @DELETE("employees/{id}")
    Call delete(@Path("id") Long employeeId);

}
