package com.job.softclick_mobile.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.Member_list_Adapter;
import com.job.softclick_mobile.databinding.FragmentDetailsBinding;
import com.job.softclick_mobile.models.Member;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.ui.layout.FooterFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailsFragment extends Fragment{

    ArrayList<Member> MembersArrayList;
    private RecyclerView recyclerView;
    private Member_list_Adapter member_list_adapter;
    private int[] MembersName;
    private int[] Memberimage;
    private FragmentDetailsBinding binding;
    private FloatingActionButton addButton;
    private Team team;

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
binding.teamname.setText(team.getTeamName());

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
        dataInitialize();
        recyclerView=view.findViewById(R.id.memberRcyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        member_list_adapter=new Member_list_Adapter(getContext(),MembersArrayList);
        recyclerView.setAdapter(member_list_adapter);
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
                .setMessage("Do you want to Delete this Employee?")
                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
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
}




