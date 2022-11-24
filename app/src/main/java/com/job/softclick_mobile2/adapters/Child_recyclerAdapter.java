package com.job.softclick_mobile2.adapters;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile2.R;
import com.job.softclick_mobile2.models.Project;

import java.util.List;

public class Child_recyclerAdapter extends RecyclerView.Adapter<Child_recyclerAdapter.Child_viewHolder> {

    List<Project> projects;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Child_recyclerAdapter(List<Project> projects) {
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
        Project title_project = projects.get(position);

        holder.title_project.setText(title_project.getNameProject());

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
