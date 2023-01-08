package com.job.softclick_mobile.ui.team;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
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
import com.job.softclick_mobile.adapters.Member_list_Adapter;
import com.job.softclick_mobile.adapters.Team_List_Adapter;
import com.job.softclick_mobile.databinding.FragmentDetailsBinding;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Member;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.teams.TeamViewModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import retrofit2.HttpException;

public class DetailsFragment extends Fragment implements RecyclerViewHandler{

    ArrayList<Member> MembersArrayList;
    private RecyclerView recyclerView;
    private Member_list_Adapter member_list_adapter;
    private int[] MembersName;
    private int[] Memberimage;
    private FragmentDetailsBinding binding;
    private FloatingActionButton addButton;
    private Team team;
    private TeamViewModel teamViewModel;
    private Member_list_Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            team = (Team) getArguments().getSerializable("team");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.teamname.setText(team.getTeam_Name());
        binding.description.setText(team.getDescription());
        recyclerView = view.findViewById(R.id.memberRcyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Set<Employee> members=team.getMembers();
        adapter = new Member_list_Adapter(members, this);
        recyclerView.setAdapter(adapter);




       /* recyclerView = view.findViewById(R.id.memberRcyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        teams=new ArrayList<>();

        teams.add(new TeamDatails("c'est une equipe developement", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));
        teams.add(new TeamDatails("c'est une equipe IT Operation", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));
        teams.add(new TeamDatails("c'est une equipe DevOps", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));
        teams.add(new TeamDatails("c'est une equipe Security", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));
        teams.add(new TeamDatails("c'est une equipe Process", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));
        teams.add(new TeamDatails("c'est une equipe Testing", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));

        return view;*/

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //dataInitialize();


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
        }
        if (binding.back != null) {
            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeamListFragment teamListFragment = new TeamListFragment();
                    FooterFragment footerFragment = new FooterFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, teamListFragment).commit();
                }
            });
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new Fragment()).commit();
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);



        if (binding.moreOptions != null) {
            binding.moreOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getActivity(), binding.moreOptions);
                    popupMenu.getMenuInflater().inflate(R.menu.details_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            // Toast message on menu item clicked
                            Toast.makeText(getActivity(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            switch (menuItem.getItemId()) {
                                case R.id.edit:

                                    Fragment fragment = new AddteamFragment();

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("team", (Serializable) team);
                                    fragment.setArguments(bundle);

                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.flContent, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    break;

                                case R.id.delete:
                                    Toast.makeText(getActivity(), "Under Construction ", Toast.LENGTH_LONG).show();
                                    AlertDialog diaBox = AskOption();
                                    diaBox.show();
                                    break;

                                default:
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

    }
    private void dataInitialize() {
        MembersArrayList=new ArrayList<>();
        MembersName = new int[]{
                R.string.member1,
                R.string.member2,
                R.string.member3,
                R.string.member4,
                R.string.member5,
                R.string.member6,
                R.string.member7,
        };
        Memberimage = new int[]{
                R.drawable.profile5,
                R.drawable.profile6,
                R.drawable.profil4,
                R.drawable.profile5,
                R.drawable.profil3,
                R.drawable.profil4,
                R.drawable.profile2,
        };
        for(int i=0;i<MembersName.length;i++){
            Member members=new Member(MembersName[i],Memberimage[i]);
            MembersArrayList.add(members);
        }
    }
    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getContext())
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete this Team?")
                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        deleteTeam();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void deleteTeam(){
        binding.progressBar.setVisibility(View.VISIBLE);



        System.out.println("Team ::: " + this.team.getTeam_Name());

        LiveResponse createLiveResponse =  teamViewModel.delete((long) this.team.getIdTeam());
        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if((Boolean) o == true ){
                    binding.progressBar.setVisibility(View.GONE);
                    binding.back.callOnClick();
                }
            }
        });

        createLiveResponse.geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                Throwable error = (Throwable) o;
                if (error instanceof HttpException) {
                    Log.d("DEBUG", error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(getContext(), "This screen is under maintenance", Toast.LENGTH_SHORT).show();
                } else if (error instanceof IOException) {

                }
                //binding.formBody.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Log.d("ERR", error.getMessage());
            }
        });


    }
}




