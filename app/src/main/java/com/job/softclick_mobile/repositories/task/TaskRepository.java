package com.job.softclick_mobile.repositories.task;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.TaskApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskRepository implements ITaskRepository, com.job.softclick_mobile.repositories.IBaseRepository<Task, Long> {

    private TaskApi service;
    private MutableLiveData<List<Task>> tMutableLiveDataList = new MutableLiveData<>();

    public TaskRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(TaskApi.class);
    }

    @Override
    public LiveData<List<Task>> getAll() {
        service.getAll().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.code() != 200) {
                    Log.d("CONSOLE LOG", "status code is " + response.code());
                } else {
                    Log.d("CONSOLE LOG", response.body().toString());
                    List<Task> tl = response.body();
                    Log.d("CONSOLE LOG", tl.toString());
                    tMutableLiveDataList.setValue(tl);
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.d("DEBUG", t.getMessage());
                Log.d("CONSOLE LOG", "Check your internet connection");
            }
        });
        return tMutableLiveDataList;
    }

    @Override
    public LiveData<Task> getSingle(Long key) {
        return null;
    }

    @Override
    public void create(Task t) {

    }

    @Override
    public void update(Long key, Task t) {

    }

    @Override
    public void delete(Long key) {

    }
}
