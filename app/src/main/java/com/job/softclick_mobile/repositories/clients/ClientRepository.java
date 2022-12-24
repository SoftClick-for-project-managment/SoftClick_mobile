package com.job.softclick_mobile.repositories.clients;

import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.services.http.ClientApi;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClientRepository implements IBaseRepository<Client, Long> {

    private ClientApi service;
    LiveResponse<List<Client>, Throwable> clientListLiveResponse = new LiveResponse<>();
    LiveResponse<Client, Throwable> clientLiveResponse = new LiveResponse<>();

    public ClientRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(ClientApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.code() != 200) {
                    clientListLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Client> cl = response.body();
                    clientListLiveResponse.gettMutableLiveData().setValue(cl);
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                clientListLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return clientListLiveResponse;
    }

    @Override
    public LiveResponse getSingle(Long aLong) {
        service.getSingle(aLong).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.code() != 200) {
                    clientLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    Client c = response.body();
                    clientLiveResponse.gettMutableLiveData().setValue(c);
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                clientListLiveResponse.geteMutableLiveData().setValue(t);
            }

        });

        return clientLiveResponse;
    }

    @Override
    public LiveResponse create(Client client) {
        return null;
    }

    @Override
    public LiveResponse update(Long aLong, Client client) {
        return null;
    }

    @Override
    public LiveResponse delete(Long aLong) {
        return null;
    }
}
