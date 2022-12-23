package com.job.softclick_mobile.viewmodels.status;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.repositories.status.StatusRepository;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

public class StatusViewModel extends BaseViewModel<Status, Long> implements IStatusViewModel  {
    public StatusViewModel(@NonNull Application application) {
        super(application, new StatusRepository());
    }
}
