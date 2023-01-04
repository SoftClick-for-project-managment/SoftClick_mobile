package com.job.softclick_mobile.ui.layout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.job.softclick_mobile.databinding.FragmentListProjectsBinding;
import com.job.softclick_mobile.databinding.FragmentProjectSearchBinding;
import com.job.softclick_mobile.models.Domain;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Priority;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Project_section;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.viewmodels.ActivitySharedViewModel;
import com.job.softclick_mobile.viewmodels.domains.DomainViewModel;
import com.job.softclick_mobile.viewmodels.domains.IDomainViewModel;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;
import com.job.softclick_mobile.viewmodels.priority.IPriorityViewModel;
import com.job.softclick_mobile.viewmodels.priority.PriorityViewModel;
import com.job.softclick_mobile.viewmodels.status.IStatusViewModel;
import com.job.softclick_mobile.viewmodels.status.StatusViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView searchbtn , nameProject;
    private AutoCompleteTextView Combo_domain , Combo_chef , Combo_status , Combo_priority;
    private  FragmentProjectSearchBinding binding;
    private ActivitySharedViewModel activitySharedViewModel;
    HashMap<String,Long> domains_pairs = new HashMap<>();
    HashMap<String,Long> employe_pairs = new HashMap<>();
    HashMap<String,Long> status_pairs = new HashMap<>();
    HashMap<String,Integer> priority_pairs = new HashMap<>();

    private IDomainViewModel domainViewModel;
    private IEmployeeViewModel employeeViewModel;
    private IStatusViewModel statusViewModel;
    private IPriorityViewModel priorityViewModel;

    public ProjectSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectSearchFragment newInstance(String param1, String param2) {
        ProjectSearchFragment fragment = new ProjectSearchFragment();
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

         binding= FragmentProjectSearchBinding.inflate(inflater, container, false);
        domainViewModel = new ViewModelProvider(this).get(DomainViewModel.class);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        statusViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        priorityViewModel = new ViewModelProvider(this).get(PriorityViewModel.class);

        searchbtn = binding.searchbtn;
        nameProject = binding.nameProjectInput;
        Combo_domain = binding.domainCombo;
        Combo_chef = binding.chefCombo;
        Combo_status = binding.statusCombo;
        Combo_priority = binding.priorityCombo;


        domainViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Domain>>() {

            @Override
            public void onChanged(List<Domain> domains) {

                try {
                    domains.forEach(domain -> {
                        domains_pairs.put(domain.getNameDomain(), domain.getIdDomain());
                    });
                    domains_pairs.put("All domains",null);
                    List<String> domain_names = new ArrayList<>(domains_pairs.keySet());
                    ArrayAdapter<String> adapter_domain = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, domain_names);
                    Combo_domain.setAdapter(adapter_domain);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }

        });
        employeeViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Employee>>() {

            @Override
            public void onChanged(List<Employee> employees) {

                try {
                    employees.forEach(employee -> {
                        employe_pairs.put(employee.getEmployeeLastName()+" "+employee.getEmployeeFirstName(), (long) employee.getId());
                    });
                    employe_pairs.put("All employees",null);
                    List<String> employe_names = new ArrayList<>(employe_pairs.keySet());
                    ArrayAdapter<String> adapter_employe = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, employe_names);
                    Combo_chef.setAdapter(adapter_employe);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });

        statusViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Status>>() {

            @Override
            public void onChanged(List<Status> statuses) {

                try {
                    statuses.forEach(status -> {
                        status_pairs.put(status.getNameStatus(), (long) status.getIdStatus());
                    });
                    status_pairs.put("All status",null);
                    List<String> status_names = new ArrayList<>(status_pairs.keySet());
                    ArrayAdapter<String> adapter_status = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, status_names);
                    Combo_status.setAdapter(adapter_status);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });

        priorityViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Priority>>() {

            @Override
            public void onChanged(List<Priority> priorities) {

                try {
                    priorities.forEach(priority -> {
                        priority_pairs.put(priority.getNamePriority(),  priority.getIdPriority());
                    });
                    priority_pairs.put("All priorities",null);
                    List<String> priority_names = new ArrayList<>(priority_pairs.keySet());
                    ArrayAdapter<String> adapter_prioritiy = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, priority_names);
                    Combo_priority.setAdapter(adapter_prioritiy);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long idDomain = domains_pairs.get(Combo_domain.getText().toString());
                Long idEmploye = employe_pairs.get(Combo_chef.getText().toString());
                Long idStatus = status_pairs.get(Combo_status.getText().toString());
                Integer idPriority = priority_pairs.get(Combo_priority.getText().toString());
                Domain domain = new Domain(idDomain,"");
                Status status = new Status(idStatus,"");
                Priority priority=new Priority(idPriority,1f,"");
                Employee chef = new Employee();chef.setId(idEmploye);
                Project project = new Project(nameProject.getText().toString(), domain,chef,status,priority);
                activitySharedViewModel.setSearchProject(project);
            }
        });
         return  binding.getRoot();
    }
}