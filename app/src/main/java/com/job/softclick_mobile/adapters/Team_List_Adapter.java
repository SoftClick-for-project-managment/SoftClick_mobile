package com.job.softclick_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;

import java.util.ArrayList;
import java.util.List;

public class Team_List_Adapter extends RecyclerView.Adapter<Team_List_Adapter.ViewHolder> {
    private final RecyclerViewHandler recyclerViewHandler;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Team_image;
        TextView Team_name;

        public ViewHolder(View itemView, RecyclerViewHandler rvh){
            super(itemView);
                Team_image=itemView.findViewById(R.id.imageteam);
                Team_name=itemView.findViewById(R.id.teamname);
        }
    }

    List<Team> TeamsArrayList;
    public Team_List_Adapter(List<Team> TeamsArrayList, RecyclerViewHandler recyclerViewHandler ){
        this.TeamsArrayList=TeamsArrayList;
        this.recyclerViewHandler = recyclerViewHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item,parent,false);
        return new ViewHolder(v,recyclerViewHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Team teams=TeamsArrayList.get(position);
    holder.Team_name.setText(teams.TeamName);
    holder.Team_image.setImageResource(teams.TeamImage);


    }

    @Override
    public int getItemCount() {
        return TeamsArrayList.size();
    }
}
