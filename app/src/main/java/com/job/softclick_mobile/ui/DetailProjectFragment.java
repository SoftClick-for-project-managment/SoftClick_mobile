package com.job.softclick_mobile.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentDetailProjectBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailProjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentDetailProjectBinding binding;
    TextView project_name ,domain, date_debut ,date_fin , description , name_chef ,clients,equips,revenue,depense;
    LinearProgressIndicator etat_avancement;
    ImageView flesh_back;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailProjectFragment newInstance(String param1, String param2) {
        DetailProjectFragment fragment = new DetailProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailProjectBinding .inflate(inflater, container, false);

        project_name = binding.projectName;
        domain = binding.domainProject;
        date_debut = binding.dateDebut;
        date_fin = binding.dateFin;
        description = binding.descriptionProjet;
        name_chef = binding.nameChef;
        clients = binding.clientsText;
        equips = binding.equipesText;
        revenue = binding.revenuTotal;
        depense = binding.depenseTotal;
        etat_avancement = binding.etatAvancement;
        flesh_back = binding.fleshBack;
        fetchDate();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        flesh_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getParentFragmentManager().getBackStackEntryCount() > 0){
                    getParentFragmentManager().popBackStackImmediate();
                }
            }
        });

        return binding.getRoot();

    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                return true;

            case R.id.delete:
                showDialogDeleteConfirmation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void showDialogDeleteConfirmation(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(newInstance("","").getContext());
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to delete this project ?");
        builder.setPositiveButton("Yes I'm Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //to do delete project
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss deletion
            }
        });
        builder.show();
    }

    public void fetchDate(){
        project_name.setText("Systém de détéction de violance");
        domain.setText("Intelligance Artificial & Big Data");
        date_debut.setText("18/12/2021");
        date_fin.setText("18/12/2022");
        description.setText("C'est un systém intelligent qui detect les acts violents sur caméra de surveillance");
        name_chef.setText("Mr Youssef Gahi");
        clients.setText("- oukacha prison \n - école sanabil \n - Army American");
        equips.setText("- équipe frontend N° 1 \n équipe fullstack N° 5");
        revenue.setText("5000000 DH");
        depense.setText("200000 DH");
        etat_avancement.setProgress(25);
    }
}