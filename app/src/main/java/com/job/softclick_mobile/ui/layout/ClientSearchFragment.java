package com.job.softclick_mobile.ui.layout;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentClientSearchBinding;
import com.job.softclick_mobile.databinding.FragmentProjectSearchBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Domain;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Priority;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.viewmodels.ActivitySharedViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentClientSearchBinding binding;
    private EditText nomClient , prenomClient , Organisation , ville,pay;
    private ActivitySharedViewModel activitySharedViewModel;
    private TextView searchbtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClientSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientSearchFragment newInstance(String param1, String param2) {
        ClientSearchFragment fragment = new ClientSearchFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activitySharedViewModel =  new ViewModelProvider(getActivity()).get(ActivitySharedViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentClientSearchBinding.inflate(inflater, container, false);
        nomClient = binding.nameClientInput;
        prenomClient = binding.prenomClientInput;
        Organisation = binding.nameOrganisationInput;
        searchbtn = binding.searchbtn;
        ville = binding.villeClientInput;
        pay = binding.payClientInput;


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nomClient.getText().toString();
                String prenom = prenomClient.getText().toString();
                String organisation_name = Organisation.getText().toString();
                String ville_name = ville.getText().toString();
                String pay_name = pay.getText().toString();

                Client client = new Client();
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setNomEntreprise(organisation_name);
                client.setVille(ville_name);
                client.setPays(pay_name);
                activitySharedViewModel.setSearchClient(client);
            }
        });

        return  binding.getRoot();
    }
}