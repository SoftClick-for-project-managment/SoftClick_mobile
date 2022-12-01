package com.job.softclick_mobile.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentAddProjectBinding;
import com.job.softclick_mobile.databinding.FragmentAddteamBinding;
import com.job.softclick_mobile.databinding.FragmentDetailsBinding;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.ui.invoices.InvoiceListFragment;
import com.job.softclick_mobile.ui.layout.FooterFragment;

import java.util.ArrayList;
import java.util.List;


public class AddteamFragment extends Fragment implements View.OnClickListener {
private AppCompatEditText Add_Member;
private FragmentAddteamBinding binding;
    private Team team;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            team = (Team) getArguments().getSerializable("team");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddteamBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Button createButton = binding.createbutton;
        createButton.setOnClickListener( this);
        Add_Member=binding.addmember;
        setPopUpList();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Team team=(Team) getArguments().getSerializable("team");
            createButton.setText("Edit");
            binding.teampagetitle.setText("Team Edition");
            binding.teamname.setText(team.getTeamName());
            binding.description.setText("c'est une équipe de développement resposable sur la création d'une application mobile appelée softclick pour gérer les projets.Elle est composée de 7 membres.");
            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("team",team);

                    DetailsFragment teamDetailsFragment = new DetailsFragment();
                    teamDetailsFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, teamDetailsFragment).commit();
                }
            });

        } else {
            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InvoiceListFragment invoiceListFragment = new InvoiceListFragment();
                    FooterFragment footerFragment = new FooterFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, invoiceListFragment).commit();
                }
            });
        }
        // Inflate the layout for this fragment
        return view;
    }


    private void setPopUpList() {
        ListView listView=new ListView(getContext());
        List<String> members=new ArrayList<>();
        members.add("Hajar");
        members.add("soukiana");
        members.add("manal");
        members.add("hind");
        members.add("othman");
        members.add("youssef");
        members.add("wafae");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.item_simple_member,R.id.member_item,members);
listView.setAdapter(adapter);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setView(listView);
        final AlertDialog dialog=builder.create();
        Add_Member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Add_Member.setText(members.get(i));
                dialog.hide();
            }
        });
    }


    @Override
    public void onClick(View view) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContent, new TeamListFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}