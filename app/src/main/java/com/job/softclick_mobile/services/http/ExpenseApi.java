package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Expense;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ExpenseApi {
    @GET("expense")
    Call<List<Expense>> getAll();

    @GET("expenses/{id}")
    Call<Expense> getSingle(@Path("id") Long expenseId);

    @POST("expenses")
    Call create(@Body Expense expense);

    @PUT("expenses/{id}")
    Call update(@Path("id") Long expenseId, @Body Expense expense);

    @DELETE("expenses/{id}")
    Call delete(@Path("id") Long expenseId);
}
