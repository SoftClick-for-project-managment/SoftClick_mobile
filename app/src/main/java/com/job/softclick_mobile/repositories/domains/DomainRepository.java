package com.job.softclick_mobile.repositories.domains;

import com.job.softclick_mobile.models.Domain;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.services.http.DomainApi;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.TaskApi;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DomainRepository implements IDomainRepository, IBaseRepository<Domain, Long> {

    private DomainApi service;
    LiveResponse<List<Domain>, Throwable> domainListLiveResponse = new LiveResponse<>();
    LiveResponse<Domain, Throwable> domainLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> createLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> updateLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> deleteLiveResponse = new LiveResponse<>();

    public DomainRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(DomainApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Domain>>() {
            @Override
            public void onResponse(Call<List<Domain>> call, Response<List<Domain>> response) {
                if (response.code() != 200) {
                    domainListLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Domain> tl = response.body();
                    domainListLiveResponse.gettMutableLiveData().setValue(tl);
                }
            }

            @Override
            public void onFailure(Call<List<Domain>> call, Throwable t) {
                domainListLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return domainListLiveResponse;
    }

    @Override
    public LiveResponse getSingle(Long key) {
        service.getSingle(key).enqueue(new Callback<Domain>() {
            @Override
            public void onResponse(Call<Domain> call, Response<Domain> response) {
                if (response.code() != 200) {
                    domainLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    Domain t = response.body();
                    domainLiveResponse.gettMutableLiveData().setValue(t);
                }
            }

            @Override
            public void onFailure(Call<Domain> call, Throwable t) {
                domainLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return domainLiveResponse;
    }

    @Override
    public LiveResponse create(Domain domain) {
        service.create(domain).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() != 201) {
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
    public LiveResponse update(Long aLong, Domain domain) {
        return null;
    }

    @Override
    public LiveResponse delete(Long aLong) {
        return null;
    }

    @Override
    public LiveResponse search(Domain domain) {
        return null;
    }
}
