package com.job.softclick_mobile.repositories.invoices;


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

public class InvoiceRepository implements IBaseRepository<Invoice, Long> {
    private InvoiceApi service;
    LiveResponse<List<Invoice>, Throwable> invoiceListLiveResponse = new LiveResponse<>();
    LiveResponse<Invoice, Throwable> invoiceLiveResponse = new LiveResponse<>();

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
        return null;
    }

    @Override
    public LiveResponse update(Long aLong, Invoice invoice) {
        return null;
    }

    @Override
    public LiveResponse delete(Long aLong) {
        return null;
    }
}
