package com.job.softclick_mobile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.Team_List_Adapter;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.ui.clients.ClientDetailsFragment;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TeamListFragment extends Fragment  implements RecyclerViewHandler {
    private RecyclerView recyclerView;
    private Team_List_Adapter team_list_adapter;
    ArrayList<Team> TeamsArrayList;
    private int[] imageTeam;
    private String[] TeamName;
    private List<Team> teams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataInitialize();
        View view= inflater.inflate(R.layout.fragment_team_list, container, false);

        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new Team_List_Adapter(teams, this));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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
        teams =new ArrayList<>();
        for(int i=0;i<TeamName.length;i++){
            teams.add(new Team(TeamName[i],imageTeam[i],TeamName));
        }
    }

    @Override
    public void onItemClick(int position) {
        Team team = teams.get(position);

        Fragment fragment = new ClientDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("team", (Serializable) team);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}