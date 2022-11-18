package com.job.softclick_mobile.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakeExpensesData
{
    static List<ExpenseModel> expenseArrayList= new ArrayList<>();
    public void FakeExpensesData(){

    }
     public void  ExpensesData() {

        expenseArrayList.add(new ExpenseModel(UUID.randomUUID().toString(), "blabla", 8347123, 944809, "expense", "frontend ", "UpWork "));
        expenseArrayList.add(new ExpenseModel(UUID.randomUUID().toString(), "blabla", 384123, 14809, "income", "frontend ", "SGI"));
        expenseArrayList.add(new ExpenseModel(UUID.randomUUID().toString(), "blabla", 348374, 34809, "expense", "UX/UI", "YOUTUBE"));
        expenseArrayList.add(new ExpenseModel(UUID.randomUUID().toString(), "blabla", 123348, 344809, "expense", "backend", "DRIBBBLE "));
        expenseArrayList.add(new ExpenseModel(UUID.randomUUID().toString(), "blabla", 33433, 144809, "expense", "backend", "UpWork "));
        expenseArrayList.add(new ExpenseModel(UUID.randomUUID().toString(), "blabla", 12356, 144809, "income", "frontend ", "UpWork "));
        expenseArrayList.add(new ExpenseModel(UUID.randomUUID().toString(), "blabla", 3847123, 234809, "expense", "frontend d", "SPOTIFY "));

    }


    public List <ExpenseModel> getFakeData(){
         ExpensesData();
        return expenseArrayList;
    }
    public int getExpenseModelById(String id){
        for (int i=0;i<expenseArrayList.size();i++){
             if(expenseArrayList.get(i).getExpenseId()==id){
                 return i;

             }
        }
        return 0;
    }
    public void deleteExpense(String id){
         expenseArrayList.remove(getExpenseModelById(id));
    }
    public void addExpense(ExpenseModel expenseModel){
         expenseArrayList.add(expenseModel);
    }
    public void updateExpense(String id,ExpenseModel expenseModel){
         expenseArrayList.set(getExpenseModelById(id),expenseModel);
     }
}
