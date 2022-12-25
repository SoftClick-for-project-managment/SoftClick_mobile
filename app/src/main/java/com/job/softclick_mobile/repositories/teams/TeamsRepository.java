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
    private LiveResponse<List<Team>, Throwable> TeamListliveResponse = new LiveResponse<>();
    LiveResponse<Team, Throwable> TeamLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> createLiveResponse = new LiveResponse<>();

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
                    TeamListliveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Team> sl = response.body();
                    TeamListliveResponse.gettMutableLiveData().setValue(sl);
                }
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                TeamListliveResponse.geteMutableLiveData().setValue(t);
            }
        });
        return TeamListliveResponse;
    }

    @Override
    public LiveResponse getSingle(Long aLong) {
        service.getSingle(aLong).enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                if (response.code() != 200) {
                    TeamLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    Team team = response.body();
                    TeamLiveResponse.gettMutableLiveData().setValue(team);
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                TeamLiveResponse.geteMutableLiveData().setValue(t);
            }

        });

        return TeamLiveResponse;
    }

    @Override
    public LiveResponse create(Team team) {
        service.create(team).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() != 200 || response.code() != 201) {
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
    public LiveResponse update(Long aLong, Team team) {
        return new LiveResponse();
    }



    @Override
    public LiveResponse delete(Long aLong) {
        return new LiveResponse();
    }
}
