package com.job.softclick_mobile.viewmodels.project;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.Project;

import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.repositories.project.ProjectRepository;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

import java.util.List;
import java.util.Map;


public class ProjectViewModel extends BaseViewModel<Project, Long> implements IProjectViewModel {
    public ProjectViewModel(@NonNull Application application) {
        super(application, new ProjectRepository());
    }
  public void patch(Long id , Map<Object,Object> fields){
        ProjectRepository projectRepository = new ProjectRepository();
        projectRepository.patch(id , fields);
  }

    @Override
    public LiveResponse seach(Project proejct) {
        ProjectRepository projectRepository = new ProjectRepository();
        return projectRepository.seach(proejct);
    }
}
