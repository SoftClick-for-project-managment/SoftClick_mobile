package com.job.softclick_mobile.viewmodels.clients;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.repositories.clients.ClientRepository;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

public class ClientViewModel extends BaseViewModel<Client, Long> implements IClientViewModel {

    public ClientViewModel(@NonNull Application application) {
        super(application, new ClientRepository());
    }

}
