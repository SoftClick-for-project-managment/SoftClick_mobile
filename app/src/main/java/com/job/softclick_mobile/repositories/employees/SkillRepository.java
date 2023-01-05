package com.job.softclick_mobile.repositories.employees;

import android.util.Log;

import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Skill;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.services.http.EmployeeApi;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.SkillApi;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SkillRepository implements ISkillRepository, IBaseRepository<Skill, Long> {

    private SkillApi service;
    LiveResponse<List<Skill>, Throwable> skillListLiveResponse = new LiveResponse<>();
    LiveResponse<Skill, Throwable> skillLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> createLiveResponse = new LiveResponse<>();

    public SkillRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(SkillApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                if (response.code() != 200) {
                    skillListLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Skill> el = response.body();
                    skillListLiveResponse.gettMutableLiveData().setValue(el);
                }
            }

            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {
                skillListLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return skillListLiveResponse;
    }

    @Override
    public LiveResponse getSingle(Long aLong) {
        service.getSingle(aLong).enqueue(new Callback<Skill>() {
            @Override
            public void onResponse(Call<Skill> call, Response<Skill> response) {
                if (response.code() != 200) {
                    skillLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    Skill e = response.body();
                    skillLiveResponse.gettMutableLiveData().setValue(e);
                }
            }

            @Override
            public void onFailure(Call<Skill> call, Throwable t) {
                skillListLiveResponse.geteMutableLiveData().setValue(t);
            }

        });

        return skillLiveResponse;
    }

    @Override
    public LiveResponse create(Skill skill) {
        service.create(skill).enqueue(new Callback<Void>() {
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
    public LiveResponse update(Long aLong, Skill skill) {
        service.update(aLong, skill).enqueue(new Callback<Void>() {
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
