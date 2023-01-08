package com.job.softclick_mobile.ui.layout;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentEmployeSearchBinding;
import com.job.softclick_mobile.databinding.FragmentInvoiceSearchBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Skill;
import com.job.softclick_mobile.viewmodels.ActivitySharedViewModel;
import com.job.softclick_mobile.viewmodels.clients.ClientViewModel;
import com.job.softclick_mobile.viewmodels.clients.IClientViewModel;
import com.job.softclick_mobile.viewmodels.employees.ISkillViewModel;
import com.job.softclick_mobile.viewmodels.employees.SkillViewModel;
import com.job.softclick_mobile.viewmodels.project.IProjectViewModel;
import com.job.softclick_mobile.viewmodels.project.ProjectViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentInvoiceSearchBinding binding;
    private TextView searchbtn;
    private AutoCompleteTextView clientCombo;
    private AutoCompleteTextView projetCombo;
    private IClientViewModel clientViewModel;
    private IProjectViewModel projectViewModel;
    private ActivitySharedViewModel sharedViewModel;

    HashMap<String,Long> client_pairs = new HashMap<>();
    HashMap<String,Long> project_pairs = new HashMap<>();

    public InvoiceSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoiceSearchFragment newInstance(String param1, String param2) {
        InvoiceSearchFragment fragment = new InvoiceSearchFragment();
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
        sharedViewModel =  new ViewModelProvider(getActivity()).get(ActivitySharedViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentInvoiceSearchBinding.inflate(inflater, container, false);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        clientCombo = binding.clientInvoicCombo;
        projetCombo = binding.projectInvoiceCombo;
        searchbtn = binding.searchbtn;

        clientViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Client>>() {

            @Override
            public void onChanged(List<Client> clients) {

                try {
                    client_pairs.put("All",null);
                    clients.forEach(client -> {
                        client_pairs.put(client.getNom(), client.getId());
                    });

                    List<String> client_names = new ArrayList<>(client_pairs.keySet());
                    ArrayAdapter<String> adapter_client= new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, client_names);
                    clientCombo.setAdapter(adapter_client);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }

        });

        projectViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Project>>() {

            @Override
            public void onChanged(List<Project> projects) {

                try {
                    project_pairs.put("ALL",null);
                    projects.forEach(project -> {
                        project_pairs.put(project.getNameProject(), project.getIdProject());
                    });

                    List<String> project_names = new ArrayList<>(project_pairs.keySet());
                    ArrayAdapter<String> adapter_project= new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, project_names);
                    projetCombo.setAdapter(adapter_project);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }

        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long client_id = client_pairs.get(clientCombo.getText().toString());
                Client client = null;

                if(client_id != null) {
                    client = new Client();
                    client.setId(client_id);
                }

                Long project_id = project_pairs.get(projetCombo.getText().toString());
                Project project = null;

                if(project_id != null) {
                    project = new Project();
                    project.setIdProject(project_id);
                }

                Invoice searchInvoice = new Invoice("","",client,project);

                sharedViewModel.setSearchInvoice(searchInvoice);
            }
        });

      return binding.getRoot();
    }
}