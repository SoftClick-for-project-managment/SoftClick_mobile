package com.job.softclick_mobile.viewmodels.employees;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.repositories.employees.EmployeeRepository;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

public class EmployeeViewModel extends BaseViewModel<Employee, Long> implements IEmployeeViewModel {

    public EmployeeViewModel(@NonNull Application application) {
        super(application, new EmployeeRepository());
    }

}
