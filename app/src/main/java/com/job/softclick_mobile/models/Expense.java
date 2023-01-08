package com.job.softclick_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable {

    private Long id;
    private Long amount;
    private String typeExpense;
    private Date date;

    private ExpenseCategory expenseCategory;
   private Task task;

    public Expense( Long amount, String typeExpense, Date date, ExpenseCategory expenseCategory, Task task) {
        this.amount = amount;
        this.typeExpense = typeExpense;
        this.date = date;
        this.expenseCategory = expenseCategory;
        this.task = task;
    }

    public Expense() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTypeExpense() {
        return typeExpense;
    }

    public void setTypeExpense(String typeExpense) {
        this.typeExpense = typeExpense;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
