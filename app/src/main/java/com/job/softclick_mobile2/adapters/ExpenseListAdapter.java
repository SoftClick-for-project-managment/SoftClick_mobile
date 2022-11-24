package com.job.softclick_mobile2.adapters;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.job.softclick_mobile2.R;
import com.job.softclick_mobile2.models.Expense;
import com.job.softclick_mobile2.ui.contracts.RecyclerViewHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>{
    private ArrayList<Expense> mExpensesList;
    private final RecyclerViewHandler recyclerViewHandler;


    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        public TextView amount;
        public TextView description;
        public TextView date;
        public TextView category;


        public ExpenseViewHolder(@NonNull View itemView, RecyclerViewHandler recyclerViewHandler) {
            super(itemView);
            amount= itemView.findViewById(R.id.amount);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            category = itemView.findViewById(R.id.category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewHandler != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            recyclerViewHandler.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ExpenseListAdapter(ArrayList<Expense> expenseList, RecyclerViewHandler recyclerViewHandler) {
        this.mExpensesList = expenseList;
        this.recyclerViewHandler = recyclerViewHandler;
    }

    @NonNull
    @Override
    public ExpenseListAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_row, parent, false);
        ExpenseListAdapter.ExpenseViewHolder evh = new ExpenseListAdapter.ExpenseViewHolder(v, recyclerViewHandler);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListAdapter.ExpenseViewHolder holder, int position) {
        Expense currentExpense = mExpensesList.get(position);
        Date date = new Date(currentExpense.getTime());
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatted = sdfDate.format(date);
        holder.date.setText(dateFormatted);
        if(currentExpense.getType()=="expense"){
            holder.amount.setTextColor(Color.parseColor("#E91E3C"));
        }else{
            holder.amount.setTextColor(Color.parseColor("#018786"));
        }
        holder.amount.setText(String.valueOf(currentExpense.getAmount()));
        holder.category.setText(currentExpense.getCategory());
    }

    @Override
    public int getItemCount() {
        return mExpensesList.size();
    }


    public void add(Expense expenseModel) {
        mExpensesList.add(expenseModel);
        notifyDataSetChanged();
    }

    public void clear() {
        mExpensesList.clear();
        notifyDataSetChanged();
    }

}
