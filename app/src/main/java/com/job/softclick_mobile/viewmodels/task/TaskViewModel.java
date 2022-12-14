package com.job.softclick_mobile.viewmodels.task;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.repositories.task.ITaskRepository;
import com.job.softclick_mobile.repositories.task.TaskRepository;
import com.job.softclick_mobile.services.http.TaskApi;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

public class TaskViewModel extends BaseViewModel<Task, Long> implements ITaskViewModel {
    public TaskViewModel(@NonNull Application application) {
        super(application, new TaskRepository());
    }


}
