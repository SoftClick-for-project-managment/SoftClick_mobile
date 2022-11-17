package com.job.softclick_mobile.adapters;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;

import java.util.List;

public class Child_recyclerAdapter extends RecyclerView.Adapter<Child_recyclerAdapter.Child_viewHolder> {

    List<String> projects;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public Child_recyclerAdapter(List<String> projects) {
        this.projects = projects;
    }

    @NonNull
    @Override
    public Child_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item_view = layoutInflater.inflate(R.layout.item_row , parent,false);


        return new Child_viewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull Child_viewHolder holder, int position) {
        String title_project = projects.get(position);

        holder.title_project.setText(title_project);

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }


    public class Child_viewHolder extends  RecyclerView.ViewHolder{

        TextView title_project;

        public Child_viewHolder(@NonNull View itemView) {
            super(itemView);
            title_project = itemView.findViewById(R.id.title_project);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
