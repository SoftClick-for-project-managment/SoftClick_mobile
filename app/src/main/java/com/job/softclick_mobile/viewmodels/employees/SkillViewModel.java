package com.job.softclick_mobile.viewmodels.employees;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Skill;
import com.job.softclick_mobile.repositories.employees.EmployeeRepository;
import com.job.softclick_mobile.repositories.employees.SkillRepository;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

public class SkillViewModel extends BaseViewModel<Skill, Long> implements ISkillViewModel {

    public SkillViewModel(@NonNull Application application) {
        super(application, new SkillRepository());
    }
}
