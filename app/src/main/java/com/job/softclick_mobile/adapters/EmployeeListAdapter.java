package com.job.softclick_mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.models.Employee;

import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>{

    private ArrayList<Employee> mEmployeeList;

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView fullNameView;
        public TextView functionView;
        public TextView emailView;
        public TextView phoneView;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.employeePhoto);
            fullNameView = itemView.findViewById(R.id.employeeFullName);
            functionView = itemView.findViewById(R.id.employeeFunction);
            emailView = itemView.findViewById(R.id.employeeEmail);
            phoneView = itemView.findViewById(R.id.employeePhone);
        }
    }

    public EmployeeListAdapter(ArrayList<Employee> employeeList) {
        mEmployeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_card, parent, false);
        EmployeeViewHolder evh = new EmployeeViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee currentEmployee = mEmployeeList.get(position);

        holder.imageView.setImageResource(currentEmployee.getEmployeeImage());
        holder.fullNameView.setText(currentEmployee.getEmployeeFirstName()+" "+currentEmployee.getEmployeeLastName());
        holder.functionView.setText(currentEmployee.getEmployeeFunction());
        holder.emailView.setText(currentEmployee.getEmployeeEmail());
        holder.phoneView.setText(currentEmployee.getEmployeePhone());
    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }
}
