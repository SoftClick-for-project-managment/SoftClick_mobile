package com.job.softclick_mobile.services.http;

import com.job.softclick_mobile.models.ExpenseCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ExpenseCategoryApi {
    @GET("expenseCategories")
    Call<List<ExpenseCategory>> getAll();

    @GET("expenseCategories/{id}")
    Call<ExpenseCategory> getSingle(@Path("id") Long expenseCategoriesId);

    @POST("expenseCategories")
    Call create(@Body ExpenseCategory expenseCategories);

    @PUT("expenseCategories")
    Call update(@Body ExpenseCategory expenseCategories);

    @PATCH("expenseCategories/{id}")
    Call update(@Path("id") Long expenseCategoriesId, @Body ExpenseCategory expenseCategories);

    @DELETE("expenseCategories/{id}")
    Call delete(@Path("id") Long expenseCategoriesId);
}
