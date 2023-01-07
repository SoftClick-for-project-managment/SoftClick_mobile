package com.job.softclick_mobile.viewmodels.priority;

import android.app.Application;
import androidx.annotation.NonNull;
import com.job.softclick_mobile.models.Priority;
import com.job.softclick_mobile.repositories.priority.PriorityRepository;
import com.job.softclick_mobile.viewmodels.BaseViewModel;


public class PriorityViewModel extends BaseViewModel<Priority, Long> implements IPriorityViewModel {

    public PriorityViewModel(@NonNull Application application) {
        super(application, new PriorityRepository());
    }
}
