package com.job.softclick_mobile2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile2.R;
import com.job.softclick_mobile2.models.TeamDatails;
import com.job.softclick_mobile2.ui.contracts.RecyclerViewHandler;

import java.util.List;

public class DetailsTeamAdapter extends RecyclerView.Adapter<DetailsTeamAdapter.DetailsViewHolder>{
    private List<TeamDatails> teams;
    private final RecyclerViewHandler recyclerViewHandler;

    public DetailsTeamAdapter(List<TeamDatails> teams, RecyclerViewHandler recyclerViewHandler) {
        this.teams = teams;
        this.recyclerViewHandler = recyclerViewHandler;
    }

    @NonNull
    @Override
    public DetailsTeamAdapter.DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item,parent,false);
        return new DetailsTeamAdapter.DetailsViewHolder(view, recyclerViewHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsTeamAdapter.DetailsViewHolder holder, int position) {
        holder.Description.setText(teams.get(position).getDescription());

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder{
        TextView Description;
        RecyclerView memberRecyclerview;
        public DetailsViewHolder(@NonNull View itemView, RecyclerViewHandler rvh) {
            super(itemView);
            Description=itemView.findViewById(R.id.description);
            memberRecyclerview=itemView.findViewById(R.id.memberRcyclerview);
        }
    }
}
