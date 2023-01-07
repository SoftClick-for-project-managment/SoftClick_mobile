package com.job.softclick_mobile.adapters;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.models.Project;

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
        holder.chef_projet.setText(title_project.getChefProject().getEmployeeLastName()+" "+title_project.getChefProject().getEmployeeFirstName());
        holder.status_project.setText("En "+title_project.getProjectStatus().getNameStatus());

    }

    @Override
    public int getItemCount() {
        return (projects != null) ? projects.size():0;
    }


    public class Child_viewHolder extends  RecyclerView.ViewHolder{

        TextView title_project , chef_projet , status_project;


        public Child_viewHolder(@NonNull View itemView) {
            super(itemView);
            title_project = itemView.findViewById(R.id.title_project);
            chef_projet = itemView.findViewById(R.id.chef_projet);
            status_project = itemView.findViewById(R.id.status_project);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
