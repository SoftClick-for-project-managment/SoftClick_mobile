package com.job.softclick_mobile.repositories.employees;

import android.util.Log;

import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.services.http.EmployeeApi;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployeeRepository implements IEmployeeRepository, IBaseRepository<Employee, Long> {

    private EmployeeApi service;
    LiveResponse<List<Employee>, Throwable> employeeListLiveResponse = new LiveResponse<>();
    LiveResponse<Employee, Throwable> employeeLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> createLiveResponse = new LiveResponse<>();

    public EmployeeRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(EmployeeApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.code() != 200) {
                    employeeListLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Employee> el = response.body();
                    employeeListLiveResponse.gettMutableLiveData().setValue(el);
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                employeeListLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return employeeListLiveResponse;
    }

    @Override
    public LiveResponse getSingle(Long aLong) {
        service.getSingle(aLong).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.code() != 200) {
                    employeeLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    Employee e = response.body();
                    employeeLiveResponse.gettMutableLiveData().setValue(e);
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                employeeListLiveResponse.geteMutableLiveData().setValue(t);
            }

        });

        return employeeLiveResponse;
    }

    @Override
    public LiveResponse create(Employee employee) {
        service.create(employee).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("DEBUG", response.code() + "");
                if (response.code() != 201) {
                    createLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    createLiveResponse.gettMutableLiveData().setValue(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                createLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return createLiveResponse;
    }

    @Override
    public LiveResponse update(Long aLong, Employee employee) {
        service.update(aLong, employee).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("DEBUG", response.code() + "");
                if (response.code() != 200) {
                    createLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    createLiveResponse.gettMutableLiveData().setValue(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                createLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return createLiveResponse;
    }

    @Override
    public LiveResponse delete(Long aLong) {
        service.delete(aLong).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("DEBUG", response.code() + "");
                if (response.code() != 200) {
                    createLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    createLiveResponse.gettMutableLiveData().setValue(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                createLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return createLiveResponse;
    }
}
