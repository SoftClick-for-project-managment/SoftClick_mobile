package com.job.softclick_mobile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.ClientListAdapter;
import com.job.softclick_mobile.adapters.Team_List_Adapter;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Member;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.models.TeamDatails;

import java.util.ArrayList;
import java.util.List;

public class TeamListFragment extends Fragment {
    private RecyclerView recyclerView;
    private Team_List_Adapter team_list_adapter;
    ArrayList<Team> TeamsArrayList;
    private int[] imageTeam;
    private String[] TeamName;
    private List<TeamDatails> teams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

    }
    private void dataInitialize() {
        TeamsArrayList=new ArrayList<>();
        TeamName = new String[]{
                getString(R.string.team1),
                getString(R.string.team2),
                getString(R.string.team3),
                getString(R.string.team4),
                getString(R.string.team5),
                getString(R.string.team6),
                getString(R.string.team7),

        };
        imageTeam = new int[]{
                R.drawable.team,
                R.drawable.team,
                R.drawable.team,
                R.drawable.team,
                R.drawable.team,
                R.drawable.team,
                R.drawable.team,


        };
        for(int i=0;i<TeamName.length;i++){
            Team teams=new Team(TeamName[i],imageTeam[i]);
            TeamsArrayList.add(teams);
        }
    }
    
}