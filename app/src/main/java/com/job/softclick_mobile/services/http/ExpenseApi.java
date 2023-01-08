package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.Task;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ExpenseApi {
    @GET("expenses")
    Call<List<Expense>> getAll();

    @GET("expenses/{id}")
    Call<Expense> getSingle(@Path("id") Long expenseId);

    @POST("expenses")
    Call<Void> create(@Body Expense expense);

    @PUT("expenses/{id}")
    Call<Void> update(@Path("id") Long expenseId, @Body Expense expense);

    @DELETE("expenses/{id}")
    Call<Void> delete(@Path("id") Long expenseId);

    @GET("expenses/task/{taskId}")
    Call<List<Expense>> getAllByTask(@Path("taskId") Long taskId);

    @POST("expenses/search")
    Call<List<Expense>> search(@Body Expense expense);
}
