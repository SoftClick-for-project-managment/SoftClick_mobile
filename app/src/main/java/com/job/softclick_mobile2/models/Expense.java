package com.job.softclick_mobile2.models;

import java.io.Serializable;

public class Expense implements Serializable {
    private String expenseId;
    private String description;
    private long amount;
    private long time;
    private String type;
    private String category;
    private String project;

    public Expense(){

    }
    public Expense(String expenseId, String description, long amount, long time, String type, String category, String project){
        this.expenseId=expenseId;
        this.description=description;
        this.amount=amount;
        this.time=time;
        this.type=type;
        this.category=category;
        this.project=project;
    }
    public String getCategory(){
        return this.category;
    }
    public String getProject(){
        return this.project;
    }
    public void setCategory(String category){
        this.category=category;

    }
    public void setProject(String project){
        this.project=project;

    }
    public long getAmount(){
        return amount;
    }
    public void setAmount(long amount){
        this.amount=amount;
    }
    public String getExpenseId(){
        return this.expenseId;
    }
    public void setExpenseId(String expenseId){
        this.expenseId=expenseId;
    }
    public long getTime(){
        return time;
    }
    public void setTime(long time){
        this.time=time;
    }
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
}
