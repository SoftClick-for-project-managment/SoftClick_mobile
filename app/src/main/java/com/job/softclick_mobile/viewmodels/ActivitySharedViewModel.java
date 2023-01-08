package com.job.softclick_mobile.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Team;

public class ActivitySharedViewModel extends ViewModel {
    private MutableLiveData<Project> searchProject = new MutableLiveData<>();
    private MutableLiveData<Client> searchClient = new MutableLiveData<>();
    private MutableLiveData<Employee> searchEmployee = new MutableLiveData<>();
    private MutableLiveData<Team> searchTeam = new MutableLiveData<>();
    private MutableLiveData<Invoice> searchInvoice = new MutableLiveData<>();

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

    public void setSearchTeam(Team team) {
        this.searchTeam.setValue(team);
    }
    public LiveData<Team> getSearchTeam(){
        return  searchTeam;
    }

    public void setSearchInvoice(Invoice invoice) {
        this.searchInvoice.setValue(invoice);
    }
    public LiveData<Invoice> getSearchInvoice(){
        return  searchInvoice;
    }
}
