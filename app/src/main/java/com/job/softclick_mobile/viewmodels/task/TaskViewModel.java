package com.job.softclick_mobile.viewmodels.task;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.repositories.task.ITaskRepository;
import com.job.softclick_mobile.repositories.task.TaskRepository;
import com.job.softclick_mobile.services.http.TaskApi;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

import java.util.List;

public class TaskViewModel extends BaseViewModel<Task, Long> implements ITaskViewModel {
    private ITaskRepository taskRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application, new TaskRepository());
        taskRepository = new TaskRepository();
    }

    @Override
    public LiveResponse<List<Task>,Throwable> getAllByProject(Long id) {
        return taskRepository.getAllByProject(id);
    }
}
