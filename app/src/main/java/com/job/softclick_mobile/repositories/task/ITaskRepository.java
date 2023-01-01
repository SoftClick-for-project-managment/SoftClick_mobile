package com.job.softclick_mobile.repositories.task;

import androidx.lifecycle.LiveData;

import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

public interface ITaskRepository extends IBaseRepository<Task, Long> {

    LiveResponse <List<Task>,Throwable> getAllByProject(Long id);

}
