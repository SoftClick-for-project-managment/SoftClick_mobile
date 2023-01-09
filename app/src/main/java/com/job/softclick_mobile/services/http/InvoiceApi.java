package com.job.softclick_mobile.services.http;


import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.Invoice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InvoiceApi {
    @GET("invoices")
    Call<List<Invoice>> getAll();

    @GET("invoices/{id}")
    Call<Invoice> getSingle(@Path("id") Long invoiceId);

    @POST("invoices")
    Call<Void> create(@Body Invoice invoice);

    @PUT("invoices/{id}")
    Call<Void> update(@Path("id") Long invoiceId, @Body Invoice invoice);

    @DELETE("invoices/{id}")
    Call<Void> delete(@Path("id") Long invoiceId);

    @POST("invoices/search")
    Call<List<Invoice>> search(@Body Invoice invoice);
}
