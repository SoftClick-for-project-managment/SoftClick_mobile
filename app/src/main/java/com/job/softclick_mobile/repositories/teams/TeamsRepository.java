package com.job.softclick_mobile.repositories.teams;


import com.job.softclick_mobile.models.Team;

import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.TeamsApi;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TeamsRepository implements ITeamsRepository {
    private TeamsApi service;
    private LiveResponse<List<Team>, Throwable> liveResponse = new LiveResponse<>();

    public TeamsRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(TeamsApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if (response.code() != 200) {
                    liveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Team> sl = response.body();
                    liveResponse.gettMutableLiveData().setValue(sl);
                }
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
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
    public LiveResponse create(Team team) {
        return new LiveResponse();
    }

    @Override
    public LiveResponse update(Long aLong, Team team) {
        return new LiveResponse();
    }



    @Override
    public LiveResponse delete(Long aLong) {
        return new LiveResponse();
    }
}
