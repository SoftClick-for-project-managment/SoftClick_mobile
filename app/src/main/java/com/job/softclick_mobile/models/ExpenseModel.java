package com.job.softclick_mobile.models;

public class ExpenseModel {
    private String expenseId;
    private String description;
    private long amount;
    private long time;
    private String type;
    public ExpenseModel(){

    }
    public ExpenseModel(String expenseId, String description,long amount, long time,  String type){
        this.expenseId=expenseId;
        this.description=description;
        this.amount=amount;
        this.time=time;
        this.type=type;
    }
    public long getAmount(){
        return amount;
    }
    public void setAmount(long amount){
        this.amount=amount;
    }
    public String getExpenseId(){
        return expenseId;
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
}
