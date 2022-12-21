package com.job.softclick_mobile.repositories.status;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.StatusApi;
import com.job.softclick_mobile.services.http.TaskApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StatusRepository implements IStatusRepository {
    private StatusApi service;
    private MutableLiveData<List<Status>> sMutableLiveDataList = new MutableLiveData<>();

    public StatusRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(StatusApi.class);
    }

    @Override
    public LiveData<List<Status>> getAll() {
        service.getAll().enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                if (response.code() != 200) {
                    Log.d("CONSOLE LOG", "status code is " + response.code());
                } else {
                    List<Status> sl = response.body();
                    Log.d("CONSOLE LOG", sl.toString());
                    sMutableLiveDataList.setValue(sl);
                }
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                Log.d("DEBUG", t.getMessage());
                Log.d("CONSOLE LOG", "Check your internet connection");
            }
        });
        return sMutableLiveDataList;
    }

    @Override
    public LiveData<Status> getSingle(Long aLong) {
        return null;
    }

    @Override
    public void create(Status status) {

    }

    @Override
    public void update(Long aLong, Status status) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
