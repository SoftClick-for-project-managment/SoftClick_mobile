package com.job.softclick_mobile.repositories.project;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.ProjectApi;
import com.job.softclick_mobile.utils.LiveResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;
import java.util.Map;

public class ProjectRepository implements  IProjectRepository , IBaseRepository<Project,Long> {

    private ProjectApi service;
    private  LiveResponse<List<Project>, Throwable>  tMutableLiveDataList = new LiveResponse<>();
    private  LiveResponse<List<Project>, Throwable>  searchLiveDataList = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> createLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> deleteLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> updateLiveResponse = new LiveResponse<>();

    public ProjectRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(ProjectApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

                if (response.code() != 200) {
                    tMutableLiveDataList.geteMutableLiveData().setValue(new HttpException(response));
                    Log.d("CONSOLE LOG", "status code is " + response.code());
                } else {
                    List<Project> tl = response.body();
                    tMutableLiveDataList.gettMutableLiveData().setValue(tl);
                    Log.d("CONSOLE LOG", "response code is : "+response.code()+tl.toString());

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
    public LiveResponse getSingle(Long aLong) {
        return null;
    }

    @Override
    public LiveResponse create(Project project) {
        service.create(project).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() != 201) {
                    createLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                    Log.d("CONSOLE LOG", "created : status code is " + response.code());

                } else {
                    createLiveResponse.gettMutableLiveData().setValue(true);
                    Log.d("CONSOLE LOG", "response code is : "+response.code());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                createLiveResponse.geteMutableLiveData().setValue(t);
                Log.d("DEBUG", t.getMessage());
                Log.d("CONSOLE LOG", "Check your internet connection");
            }
        });
        return createLiveResponse;

    }



    @Override
    public LiveResponse update(Long aLong, Project project) {

        service.update(aLong,project ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("DEBUG", response.code() + "");
                if (response.code() != 200) {
                    updateLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    updateLiveResponse.gettMutableLiveData().setValue(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                updateLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return updateLiveResponse;
    }

    @Override
    public LiveResponse delete(Long aLong) {
        service.delete(aLong).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() != 200) {
                    deleteLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                    Log.d("CONSOLE LOG", "status code is " + response.code());
                } else {
                    ResponseBody tl = response.body();
                    deleteLiveResponse.gettMutableLiveData().setValue( true);
                    Log.d("CONSOLE LOG", "response code is : "+response.code()+tl.toString());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                deleteLiveResponse.geteMutableLiveData().setValue(t);
                Log.d("DEBUG", t.getMessage());
                Log.d("CONSOLE LOG", "Check your internet connection");
            }



        });
        return deleteLiveResponse;
    }

    public void patch(Long id,Map<Object,Object> fields){
        service.patch(id,fields).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() != 200) {
                    Log.d("CONSOLE LOG", "status code is " + response.code());
                } else {
                    ResponseBody tl = response.body();
                    Log.d("CONSOLE LOG", "response code is : "+response.code()+fields.toString());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("DEBUG", t.getMessage());
                Log.d("CONSOLE LOG", "Check your internet connection");
            }


        });
    }

    @Override
    public LiveResponse search(Project project) {
        service.search(project).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                Log.d("DEBUG", response.body().toString());
                if (response.code() != 200) {
                    Log.d("DEBUG", response.body().toString());
                    searchLiveDataList.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Project> t = response.body();
                    searchLiveDataList.gettMutableLiveData().setValue(t);
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                Log.d("DEBUG", t.getMessage());
                Log.d("CONSOLE LOG", "Check your internet connection");
                searchLiveDataList.geteMutableLiveData().setValue(t);
            }
        });

        return searchLiveDataList;
    }


}
