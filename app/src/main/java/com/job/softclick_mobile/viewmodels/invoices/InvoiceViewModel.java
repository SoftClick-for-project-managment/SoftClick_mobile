package com.job.softclick_mobile.viewmodels.invoices;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.repositories.invoices.InvoiceRepository;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

public class InvoiceViewModel extends BaseViewModel<Invoice,Long> implements IInvoiceViewModel{

    public InvoiceViewModel(@NonNull Application application){
        super(application, new InvoiceRepository());
    }
}
