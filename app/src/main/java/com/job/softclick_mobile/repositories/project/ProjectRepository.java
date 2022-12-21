package com.job.softclick_mobile.repositories.project;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.ProjectApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;

public class ProjectRepository implements  IProjectRepository , IBaseRepository<Project,Long> {

    private ProjectApi service;
    private MutableLiveData<List<Project>> tMutableLiveDataList = new MutableLiveData<>();

    public ProjectRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(ProjectApi.class);
    }

    @Override
    public LiveData<List<Project>> getAll() {
        service.getAll().enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.code() != 200) {
                    Log.d("CONSOLE LOG", "status code is " + response.code());
                } else {
                    List<Project> tl = response.body();
                    Log.d("CONSOLE LOG", tl.toString());
                    tMutableLiveDataList.setValue(tl);
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                Log.d("DEBUG", t.getMessage());
                Log.d("CONSOLE LOG", "Check your internet connection");
            }
        });
        return tMutableLiveDataList;
    }

    @Override
    public LiveData<Project> getSingle(Long aLong) {
        return null;
    }

    @Override
    public void create(Project project) {

    }

    @Override
    public void update(Long aLong, Project project) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
