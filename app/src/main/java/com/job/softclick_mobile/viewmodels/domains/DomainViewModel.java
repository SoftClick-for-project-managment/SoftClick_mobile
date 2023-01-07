package com.job.softclick_mobile.viewmodels.domains;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.Domain;
import com.job.softclick_mobile.repositories.domains.DomainRepository;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

public class DomainViewModel extends BaseViewModel<Domain, Long> implements IDomainViewModel {
    public DomainViewModel(@NonNull Application application) {
        super(application, new DomainRepository());
    }
}
