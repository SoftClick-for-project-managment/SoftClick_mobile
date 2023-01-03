package com.job.softclick_mobile.repositories.task;

import android.util.Log;

import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.TaskApi;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskRepository implements ITaskRepository, IBaseRepository<Task, Long> {

    private TaskApi service;
    LiveResponse <List<Task>,Throwable> taskListByProjectLiveResponse = new LiveResponse<>();
    LiveResponse<List<Task>, Throwable> taskListLiveResponse = new LiveResponse<>();
    LiveResponse<Task, Throwable> taskLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> createLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> updateLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> deleteLiveResponse = new LiveResponse<>();

    public TaskRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(TaskApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    taskListLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Task> tl = response.body();
                    taskListLiveResponse.gettMutableLiveData().setValue(tl);
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                taskListLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return taskListLiveResponse;
    }

    @Override
    public LiveResponse getSingle(Long key) {
        service.getSingle(key).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    taskLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    Task t = response.body();
                    taskLiveResponse.gettMutableLiveData().setValue(t);
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                taskLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return taskLiveResponse;
    }

    @Override
    public LiveResponse create(Task task) {
        service.create(task).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    createLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    createLiveResponse.gettMutableLiveData().setValue(true);
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
    public LiveResponse update(Long key, Task task) {

        service.update(key, task).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("DEBUG", response.code() + "");
                if (response.isSuccessful()) {
                    updateLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    updateLiveResponse.gettMutableLiveData().setValue(true);
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
                    deleteLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    deleteLiveResponse.gettMutableLiveData().setValue(true);
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
    public LiveResponse search(Task task) {
        return null;
    }

    @Override
    public LiveResponse<List<Task>,Throwable> getAllByProject(Long id) {
        service.getAllByProject(id).enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    taskListByProjectLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Task> tl = response.body();
                    taskListByProjectLiveResponse.gettMutableLiveData().setValue(tl);
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                taskListByProjectLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return taskListByProjectLiveResponse;
    }
}
