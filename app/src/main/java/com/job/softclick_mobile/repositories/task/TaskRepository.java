package com.job.softclick_mobile.repositories.task;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.TaskApi;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskRepository implements ITaskRepository, com.job.softclick_mobile.repositories.IBaseRepository<Task, Long> {

    private TaskApi service;
    LiveResponse<List<Task>, Throwable> liveResponse = new LiveResponse<>();

    public TaskRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(TaskApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.code() != 200) {
                    liveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Task> tl = response.body();
                    liveResponse.gettMutableLiveData().setValue(tl);
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
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
    public LiveResponse create(Task task) {
        return new LiveResponse();
    }

    @Override
    public LiveResponse update(Long aLong, Task task) {
        return new LiveResponse();
    }

    @Override
    public LiveResponse delete(Long aLong) {
        return new LiveResponse();
    }
}
