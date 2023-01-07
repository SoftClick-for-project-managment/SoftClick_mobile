package com.job.softclick_mobile.repositories.expense;
import android.util.Log;

import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.services.http.ExpenseApi;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.ExpenseApi;
import com.job.softclick_mobile.services.http.ExpenseApi;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ExpenseRepository implements IExpenseRepository, IBaseRepository<Expense, Long> {


    private ExpenseApi service;
    LiveResponse <List<Expense>,Throwable> expenseListByTaskLiveResponse = new LiveResponse<>();
    LiveResponse<List<Expense>, Throwable> expenseListLiveResponse = new LiveResponse<>();
    LiveResponse<Expense, Throwable> expenseLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> createLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> updateLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> deleteLiveResponse = new LiveResponse<>();

    public ExpenseRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(ExpenseApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if ( response.isSuccessful() ) {
                    List<Expense> tl = response.body();
                    expenseListLiveResponse.gettMutableLiveData().setValue(tl);
                } else {
                    expenseListLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                expenseListLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return expenseListLiveResponse;
    }

    @Override
    public LiveResponse getSingle(Long key) {
        service.getSingle(key).enqueue(new Callback<Expense>() {
            @Override
            public void onResponse(Call<Expense> call, Response<Expense> response) {
                if (response.isSuccessful()) {
                    Expense t = response.body();
                    expenseLiveResponse.gettMutableLiveData().setValue(t);
                } else {
                    expenseLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call<Expense> call, Throwable t) {
                expenseLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return expenseLiveResponse;
    }

    @Override
    public LiveResponse create(Expense expense) {
        System.out.print("expense:"+expense.toString());
        service.create(expense).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    createLiveResponse.gettMutableLiveData().setValue(true);
                } else {
                    createLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                createLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return createLiveResponse;
    }

    @Override
    public LiveResponse update(Long key, Expense expense) {

        service.update(key, expense).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("DEBUG", response.code() + "");
                if (response.isSuccessful()) {
                    updateLiveResponse.gettMutableLiveData().setValue(true);
                } else {
                    updateLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                updateLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return updateLiveResponse;
    }

    @Override
    public LiveResponse delete(Long aLong) {
        service.delete(aLong).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    deleteLiveResponse.gettMutableLiveData().setValue(true);
                } else {
                    deleteLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                deleteLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return deleteLiveResponse;
    }

    @Override
    public LiveResponse<List<Expense>,Throwable> getAllByTask(Long id) {
        service.getAllByTask(id).enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if (response.isSuccessful()) {
                    List<Expense> tl = response.body();
                    expenseListByTaskLiveResponse.gettMutableLiveData().setValue(tl);
                } else {
                    expenseListByTaskLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                expenseListByTaskLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return expenseListByTaskLiveResponse;
    }

    @Override
    public LiveResponse search(Expense expense) {
        return null;
    }
}
