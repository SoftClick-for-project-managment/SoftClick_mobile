package com.job.softclick_mobile.viewmodels.expense;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.Expense;

import com.job.softclick_mobile.repositories.expense.ExpenseRepository;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

public class ExpenseViewModel extends BaseViewModel<Expense, Long> implements IExpenseViewModel{
    public ExpenseViewModel(@NonNull Application application) {
        super(application, new ExpenseRepository());
    }
}
