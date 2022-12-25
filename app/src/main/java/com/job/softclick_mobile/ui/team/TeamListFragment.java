package com.job.softclick_mobile.ui.team;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;

import com.job.softclick_mobile.adapters.Team_List_Adapter;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.ui.team.AddteamFragment;
import com.job.softclick_mobile.ui.team.DetailsFragment;

import java.util.ArrayList;

public class TeamListFragment extends Fragment implements RecyclerViewHandler {
    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    ArrayList<Team> TeamsArrayList;
    private int[] imageTeam;
    private String[] TeamName;

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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new Team_List_Adapter(TeamsArrayList, this));


        addButton = this.getActivity().findViewById(R.id.addButton);
        if(addButton != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,(Fragment) AddteamFragment.class.newInstance()).commit() ;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            });
        } }
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
                getString(R.string.team8),
                getString(R.string.team9),
                getString(R.string.team10)

        };
        imageTeam = new int[]{
                R.drawable.team,
                R.drawable.team,
                R.drawable.team,
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

    @Override
    public void onItemClick( int position) {
      Team team = TeamsArrayList.get(position);
        Fragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("team",team);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent,fragment);
        fragmentTransaction.commit();
    }
}