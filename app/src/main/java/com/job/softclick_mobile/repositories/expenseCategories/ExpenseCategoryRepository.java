package com.job.softclick_mobile.repositories.expenseCategories;

import com.job.softclick_mobile.models.ExpenseCategory;
import com.job.softclick_mobile.services.http.ExpenseCategoryApi;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExpenseCategoryRepository implements  IExpenseCategoryRepository {
    private ExpenseCategoryApi service;
    private LiveResponse<List<ExpenseCategory>, Throwable> liveResponse = new LiveResponse<>();

    public ExpenseCategoryRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(ExpenseCategoryApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<ExpenseCategory>>() {
            @Override
            public void onResponse(Call<List<ExpenseCategory>> call, Response<List<ExpenseCategory>> response) {
                if (response.code() != 200) {
                    liveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<ExpenseCategory> sl = response.body();
                    liveResponse.gettMutableLiveData().setValue(sl);
                }
            }

            @Override
            public void onFailure(Call<List<ExpenseCategory>> call, Throwable t) {
                liveResponse.geteMutableLiveData().setValue(t);
            }
        });
        return liveResponse;
    }

    @Override
    public LiveResponse getSingle(Long aLong) {
        return new LiveResponse();
    }

    @Override
    public LiveResponse create(ExpenseCategory expenseCategory) {
        return new LiveResponse();
    }

    @Override
    public LiveResponse update(Long aLong, ExpenseCategory expenseCategory) {
        return new LiveResponse();
    }

    @Override
    public LiveResponse delete(Long aLong) {
        return new LiveResponse();
    }

    @Override
    public LiveResponse search(ExpenseCategory expenseCategory) {
        return null;
    }
}
