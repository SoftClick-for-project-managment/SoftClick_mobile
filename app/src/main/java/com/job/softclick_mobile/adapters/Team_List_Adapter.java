package com.job.softclick_mobile.adapters;

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

import java.util.List;

public class Team_List_Adapter extends RecyclerView.Adapter<Team_List_Adapter.TeamViewHolder>{

    private List<Team> TeamList;
    private final RecyclerViewHandler recyclerViewHandler;


    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageteam;
        public TextView nameteam;

        public TeamViewHolder(@NonNull View itemView, RecyclerViewHandler recyclerViewHandler) {
            super(itemView);
            imageteam = itemView.findViewById(R.id.imageteam);
            nameteam= itemView.findViewById(R.id.teamname);


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

    public Team_List_Adapter(List<Team> TeamList, RecyclerViewHandler recyclerViewHandler) {
        this.TeamList = TeamList;
        this.recyclerViewHandler = recyclerViewHandler;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);
        TeamViewHolder evh = new TeamViewHolder(v, recyclerViewHandler);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {


        holder.imageteam.setImageResource(TeamList.get(position).TeamImage);
        holder.nameteam.setText(TeamList.get(position).TeamName);
    }

    @Override
    public int getItemCount() {
        return TeamList.size();
    }
}
