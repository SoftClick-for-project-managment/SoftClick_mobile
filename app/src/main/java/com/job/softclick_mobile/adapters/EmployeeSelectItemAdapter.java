package com.job.softclick_mobile.adapters;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.models.Employee;

import java.util.List;

public class EmployeeSelectItemAdapter extends BaseAdapter {

    private Context context;
    private List<Employee> employees;

    public EmployeeSelectItemAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.employee_select_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            // Display the dummy item
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.textView.setText(employees.get(position).getEmployeeFirstName());
        } else {
            Employee item = employees.get(position);
            viewHolder.imageView.setImageResource(R.drawable.user_photo);
            viewHolder.textView.setText(item.getEmployeeFirstName() + " " + item.getEmployeeLastName());
        }

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.imageView);
            textView = view.findViewById(R.id.textView);
        }
    }
}
