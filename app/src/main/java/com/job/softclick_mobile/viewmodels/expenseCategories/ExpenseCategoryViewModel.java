package com.job.softclick_mobile.viewmodels.expenseCategories;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.ExpenseCategory;
import com.job.softclick_mobile.repositories.expenseCategories.ExpenseCategoryRepository;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

public class ExpenseCategoryViewModel  extends BaseViewModel<ExpenseCategory, Long> implements IExpenseCategoryViewModel {
    public ExpenseCategoryViewModel(@NonNull Application application) {
        super(application, new ExpenseCategoryRepository());
    }
}