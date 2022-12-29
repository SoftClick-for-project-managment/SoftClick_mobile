package com.job.softclick_mobile.viewmodels.task;

import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.IBaseViewModel;

import java.util.List;

public interface ITaskViewModel extends IBaseViewModel<Task, Long> {

    LiveResponse <List<Task>,Throwable> getAllByProject(Long id);
}
