package com.job.softclick_mobile.adapters;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Project_section;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    List<Project_section> project_sectionList;
    RvItemClickListener rvItemClickListener;



    public MainRecyclerAdapter(List<Project_section> project_sectionList ,RvItemClickListener rvItemClickListener) {
        this.project_sectionList = project_sectionList;
        this.rvItemClickListener = rvItemClickListener;

    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View my_view = layoutInflater.inflate(R.layout.section_row,parent,false);
        return new MainViewHolder(my_view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Project_section project_section = project_sectionList.get(position);
        String priority = project_section.getPriority();
        List<Project> projects = project_section.getProjects();

        holder.priority.setText(priority);

        Child_recyclerAdapter child_recyclerAdapter = new Child_recyclerAdapter(projects);

        child_recyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int childPosition) {
                Project item = projects.get(childPosition);
                rvItemClickListener.onChildItemClick(holder.getAdapterPosition(), childPosition, item.getNameProject());
            }
        });



        holder.child_recycler_view.setAdapter(child_recyclerAdapter);


    }

    @Override
    public int getItemCount() {
        return project_sectionList.size();
    }

    public class MainViewHolder extends  RecyclerView.ViewHolder{

        TextView priority;
        RecyclerView child_recycler_view;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            priority = itemView.findViewById(R.id.priority);
            child_recycler_view = itemView.findViewById(R.id.child_recycle_view);

        }
    }

    public void setProject_sectionList(List<Project_section> project_sectionList) {
        this.project_sectionList = project_sectionList;
        notifyDataSetChanged();
    }
}
