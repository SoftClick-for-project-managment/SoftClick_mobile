package com.job.softclick_mobile.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Project;

public class ActivitySharedViewModel extends ViewModel {
    private MutableLiveData<Project> searchProject = new MutableLiveData<>();
    private MutableLiveData<Client> searchClient = new MutableLiveData<>();
    private MutableLiveData<Employee> searchEmployee = new MutableLiveData<>();

    public void setSearchProject(Project project) {
        this.searchProject.setValue(project);
    }
    public LiveData<Project> getSearchProject(){
        return  searchProject;
    }

    public void setSearchClient(Client client) {
        this.searchClient.setValue(client);
    }
    public LiveData<Client> getSearchClient(){
        return  searchClient;
    }

    public void setSearchEmployee(Employee employee) {
        this.searchEmployee.setValue(employee);
    }
    public LiveData<Employee> getSearchEmployee(){
        return  searchEmployee;
    }
}
