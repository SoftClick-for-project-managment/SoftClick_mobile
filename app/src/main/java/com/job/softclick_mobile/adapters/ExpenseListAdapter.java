package com.job.softclick_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.models.ExpenseModel;
import com.job.softclick_mobile.ui.OnItemsClick;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.MyViewHolder>{
    private OnItemsClick onItemsClick;

    private List<ExpenseModel> expenseModelList;
    public ExpenseListAdapter(List <ExpenseModel> expensesList, OnItemsClick onItemsClick){
        expenseModelList=expensesList;
        this.onItemsClick=onItemsClick;
    }
    public void add(ExpenseModel expenseModel){
            expenseModelList.add(expenseModel);
            notifyDataSetChanged();
    }
    public void clear(){
        expenseModelList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExpenseModel expenseModel=expenseModelList.get(position);
        holder.project.setText(expenseModel.getProject());
        holder.category.setText(expenseModel.getCategory());
        holder.amount.setText(String.valueOf(expenseModel.getAmount()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemsClick.onClick(expenseModel);
            }
        });
    }

    @Override
    public int getItemCount() {

        return expenseModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView project,category,amount,date;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            project=itemView.findViewById(R.id.project);
            category=itemView.findViewById(R.id.category);
            amount=itemView.findViewById(R.id.amount);
            date=itemView.findViewById(R.id.date);
        }
    }
}
