package com.job.softclick_mobile.ui.team;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListPopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.Team_List_Adapter;
import com.job.softclick_mobile.databinding.FragmentTeamListBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.viewmodels.ActivitySharedViewModel;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;
import com.job.softclick_mobile.viewmodels.teams.TeamViewModel;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class TeamListFragment extends Fragment implements RecyclerViewHandler {
    private FragmentTeamListBinding binding;
    private FloatingActionButton addButton;
    private Button addMember;
    private RecyclerView recyclerView;
    ArrayList<Team> TeamsArrayList;
    private int[] imageTeam;
    private String[] TeamName;
    private TeamViewModel teamViewModel;
    private Team_List_Adapter adapter;
    private ProgressBar progressBar;
    private List<Team> teamList = new ArrayList<>();
    private ActivitySharedViewModel activitySharedViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEAM_LIST_FRAGMENT", "onCreate got called");
        if (getArguments() != null) {
//            mParam1 = getArguments().getString("ARG_PARAM1");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activitySharedViewModel =  new ViewModelProvider(getActivity()).get(ActivitySharedViewModel.class);
        activitySharedViewModel.getSearchTeam().observe(getViewLifecycleOwner(), new Observer<Team>() {
            @Override
            public void onChanged(Team team) {

                searchTeam(team);
                Toast.makeText(getActivity().getApplicationContext(), team.getTeam_Name(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        //dataInitialize();
        recyclerView = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);


        teamViewModel.getAll().geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                progressBar.setVisibility(View.INVISIBLE);
                Throwable error = (Throwable) o;
                Log.d("ERR", error.getMessage());
            }
        });

        teamViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Team>>() {

            @Override
            public void onChanged(List<Team> teams) {
                TeamsArrayList = new ArrayList<>();
                //AtomicReference<ArrayList<Employee>> sEmployeeList = new AtomicReference<>(new ArrayList<>());

                teams.forEach(team -> {
                    TeamsArrayList.add(team);
                });

                progressBar.setVisibility(View.INVISIBLE);
                refreshUi();
            }
        });

        addButton = this.getActivity().findViewById(R.id.addButton);
        if (addButton != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, (Fragment) AddteamFragment.class.newInstance()).commit();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }
    /*private void dataInitialize() {
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
    }*/
    private void refreshUi(){
        adapter = new Team_List_Adapter(TeamsArrayList, this);
        recyclerView.setAdapter(adapter);
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

    public void searchTeam(Team team){
        teamViewModel.search(team).gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                TeamsArrayList = new ArrayList<>();
                teams.forEach(client -> {
                    TeamsArrayList.add(client);
                });
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                refreshUi();
                //mainRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
}