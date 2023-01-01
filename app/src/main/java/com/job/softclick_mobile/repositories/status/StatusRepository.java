package com.job.softclick_mobile.repositories.status;

import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.StatusApi;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StatusRepository implements IStatusRepository {
    private StatusApi service;
    private LiveResponse<List<Status>, Throwable> liveResponse = new LiveResponse<>();

    public StatusRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(StatusApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                if (response.code() != 200) {
                    liveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Status> sl = response.body();
                    liveResponse.gettMutableLiveData().setValue(sl);
                }
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
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
    public LiveResponse create(Status status) {
        return new LiveResponse();
    }

    @Override
    public LiveResponse update(Long aLong, Status status) {
        return new LiveResponse();
    }

    @Override
    public LiveResponse delete(Long aLong) {
        return new LiveResponse();
    }
}
