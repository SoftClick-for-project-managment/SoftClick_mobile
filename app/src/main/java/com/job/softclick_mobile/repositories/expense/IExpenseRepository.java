package com.job.softclick_mobile.repositories.expense;

import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

public interface IExpenseRepository extends IBaseRepository<Expense, Long> {
    LiveResponse<List<Expense>,Throwable> getAllByTask(Long id);


}
