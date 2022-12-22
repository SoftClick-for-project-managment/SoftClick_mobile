package com.job.softclick_mobile.repositories.expense;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.services.http.ExpenseApi;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.ui.employees.utils.LiveResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExpenseRepository implements IExpenseRepository, IBaseRepository<Expense, Long> {
    private ExpenseApi service;
    LiveResponse<List<Expense>, Throwable> expenseListLiveResponse = new LiveResponse<>();
    LiveResponse<Expense, Throwable> expenseLiveResponse = new LiveResponse<>();

    public ExpenseRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(ExpenseApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if (response.code() != 200) {
                    expenseListLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Expense> tl = response.body();
                    expenseListLiveResponse.gettMutableLiveData().setValue(tl);
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
                if (response.code() != 200) {
                    expenseLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    Expense e = response.body();
                    expenseLiveResponse.gettMutableLiveData().setValue(e);
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
        return new LiveResponse();
    }

    @Override
    public LiveResponse update(Long aLong, Expense expense) {
        return new LiveResponse();
    }

    @Override
    public LiveResponse delete(Long aLong) {
        return new LiveResponse();
    }
}
