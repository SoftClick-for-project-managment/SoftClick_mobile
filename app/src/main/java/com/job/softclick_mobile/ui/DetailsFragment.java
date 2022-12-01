package com.job.softclick_mobile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.Member_list_Adapter;
import com.job.softclick_mobile.databinding.ActivityMenuBinding;
import com.job.softclick_mobile.models.Member;
import com.job.softclick_mobile.models.TeamDatails;

import java.util.ArrayList;

public class DetailsFragment extends Fragment{

    ArrayList<Member> MembersArrayList;
    private RecyclerView recyclerView;
    private Member_list_Adapter member_list_adapter;
    private int[] MembersName;
    private int[] Memberimage;
    private ActivityMenuBinding binding;
    private ArrayList<TeamDatails> teams;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details, container, false);
        recyclerView = view.findViewById(R.id.memberRcyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        teams=new ArrayList<>();

        teams.add(new TeamDatails("c'est une equipe developement", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));
        teams.add(new TeamDatails("c'est une equipe IT Operation", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));
        teams.add(new TeamDatails("c'est une equipe DevOps", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));
        teams.add(new TeamDatails("c'est une equipe Security", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));
        teams.add(new TeamDatails("c'est une equipe Process", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));
        teams.add(new TeamDatails("c'est une equipe Testing", new Member[]{new Member(R.string.member1,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6),new Member(R.string.member2,R.drawable.profile6)}));

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
        member_list_adapter.notifyDataSetChanged();


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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.header_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}