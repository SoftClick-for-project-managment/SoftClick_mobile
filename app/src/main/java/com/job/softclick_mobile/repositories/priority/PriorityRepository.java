package com.job.softclick_mobile.repositories.priority;

import com.job.softclick_mobile.models.Priority;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.repositories.status.IStatusRepository;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.PriorityApi;
import com.job.softclick_mobile.services.http.StatusApi;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PriorityRepository implements IPriorityRepository {

    private PriorityApi service;
    private LiveResponse<List<Priority>, Throwable> liveResponse = new LiveResponse<>();

    public PriorityRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(PriorityApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Priority>>() {
            @Override
            public void onResponse(Call<List<Priority>> call, Response<List<Priority>> response) {
                if (response.code() != 200) {
                    liveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Priority> sl = response.body();
                    liveResponse.gettMutableLiveData().setValue(sl);
                }
            }

            @Override
            public void onFailure(Call<List<Priority>> call, Throwable t) {
                liveResponse.geteMutableLiveData().setValue(t);
            }
        });
        return liveResponse;
    }

    @Override
    public LiveResponse getSingle(Long aLong) {
        return null;
    }

    @Override
    public LiveResponse create(Priority priority) {
        return null;
    }

    @Override
    public LiveResponse update(Long aLong, Priority priority) {
        return null;
    }

    @Override
    public LiveResponse delete(Long aLong) {
        return null;
    }

    @Override
    public LiveResponse search(Priority priority) {
        return null;
    }
}
