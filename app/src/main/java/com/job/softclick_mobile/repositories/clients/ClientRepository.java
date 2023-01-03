package com.job.softclick_mobile.repositories.clients;

import android.util.Log;

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

public class ClientRepository implements IClientRepository, IBaseRepository<Client, Long> {

    private ClientApi service;
    LiveResponse<List<Client>, Throwable> clientListLiveResponse = new LiveResponse<>();
    LiveResponse<Client, Throwable> clientLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> createLiveResponse = new LiveResponse<>();

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
        service.create(client).enqueue(new Callback<Void>() {
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
    public LiveResponse update(Long aLong, Client client) {
        service.update(aLong, client).enqueue(new Callback<Void>() {
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

    @Override
    public LiveResponse search(Client client) {
        return null;
    }
}
