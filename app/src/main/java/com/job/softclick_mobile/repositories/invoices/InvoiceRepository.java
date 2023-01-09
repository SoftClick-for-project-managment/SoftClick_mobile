package com.job.softclick_mobile.repositories.invoices;


import android.util.Log;

import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.InvoiceApi;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InvoiceRepository implements IInvoiceRepository, IBaseRepository<Invoice, Long> {
    private InvoiceApi service;
    LiveResponse<List<Invoice>, Throwable> invoiceListLiveResponse = new LiveResponse<>();
    LiveResponse<Invoice, Throwable> invoiceLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> createLiveResponse = new LiveResponse<>();

    public InvoiceRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        service = httpClient.create(InvoiceApi.class);
    }

    @Override
    public LiveResponse getAll() {
        service.getAll().enqueue(new Callback<List<Invoice>>() {

            @Override
            public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
                if (response.code() != 200) {
                    invoiceListLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    List<Invoice> tl = response.body();
                    invoiceListLiveResponse.gettMutableLiveData().setValue(tl);
                }
            }

            @Override
            public void onFailure(Call<List<Invoice>> call, Throwable t) {
                invoiceListLiveResponse.geteMutableLiveData().setValue(t);
            }
        });
        return invoiceListLiveResponse;
    }

    @Override
    public LiveResponse getSingle(Long aLong) {
        service.getSingle(aLong).enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if (response.code() != 200) {
                    invoiceLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                } else {
                    Invoice e = response.body();
                    invoiceLiveResponse.gettMutableLiveData().setValue(e);
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                invoiceListLiveResponse.geteMutableLiveData().setValue(t);
            }

        });

        return invoiceLiveResponse;
    }

    @Override
    public LiveResponse create(Invoice invoice) {
        service.create(invoice).enqueue(new Callback<Void>() {
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
    public LiveResponse update(Long aLong, Invoice invoice) {
        service.update(aLong, invoice).enqueue(new Callback<Void>() {
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
    public LiveResponse search(Invoice invoice) {
        return null;
    }
}
