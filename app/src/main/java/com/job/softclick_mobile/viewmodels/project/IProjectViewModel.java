package com.job.softclick_mobile.viewmodels.project;

import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.viewmodels.IBaseViewModel;

import java.util.Map;

public interface IProjectViewModel extends IBaseViewModel<Project, Long> {
    public void patch(Long id , Map<Object,Object> fields);
}
