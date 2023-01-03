package com.job.softclick_mobile.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.job.softclick_mobile.models.Project;

public class ActivitySharedViewModel extends ViewModel {
    private MutableLiveData<Project> searchProject = new MutableLiveData<>();

    public void setSearchProject(Project project) {
        this.searchProject.setValue(project);
    }

    public LiveData<Project> getSearchProject(){
        return  searchProject;
    }
}
