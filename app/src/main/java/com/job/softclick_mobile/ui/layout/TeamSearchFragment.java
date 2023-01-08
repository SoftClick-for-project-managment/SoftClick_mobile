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
import android.widget.EditText;
import android.widget.TextView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentEmployeSearchBinding;
import com.job.softclick_mobile.databinding.FragmentTeamSearchBinding;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Skill;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.viewmodels.ActivitySharedViewModel;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.ISkillViewModel;
import com.job.softclick_mobile.viewmodels.employees.SkillViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentTeamSearchBinding binding;
    private EditText nameTeam;
    private TextView searchbtn;
    private AutoCompleteTextView memberCombo;
    private IEmployeeViewModel employeeViewModel;
    private ActivitySharedViewModel sharedViewModel;

    HashMap<String,Long> employe_pairs = new HashMap<>();

    public TeamSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamSearchFragment newInstance(String param1, String param2) {
        TeamSearchFragment fragment = new TeamSearchFragment();
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
        binding= FragmentTeamSearchBinding.inflate(inflater, container, false);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        nameTeam = binding.TeamNameInput;
        memberCombo = binding.memberTeam;
        searchbtn = binding.searchbtn;


        employeeViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Employee>>() {

            @Override
            public void onChanged(List<Employee> employees) {

                try {
                    employees.forEach(employee -> {
                        String fullName = employee.getEmployeeLastName()+" "+employee.getEmployeeFirstName();
                        employe_pairs.put(fullName, employee.getId());
                    });
                    employe_pairs.put("ALL",null);
                    List<String> employe_names = new ArrayList<>(employe_pairs.keySet());
                    ArrayAdapter<String> adapter_employe = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, employe_names);
                    memberCombo.setAdapter(adapter_employe);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }

        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_team = nameTeam.getText().toString();
                Long employe_id = employe_pairs.get(memberCombo.getText().toString());
                Employee employee = null;

                if(employe_id != null) {
                    employee = new Employee();
                    employee.setId(employe_id);
                }

                Team searchTeam = new Team();
                searchTeam.setTeamName(name_team);
              //  searchTeam.setMembes(new ArraySet<>());//TODO complete it
               // searchTeam.addMember(employee);


                sharedViewModel.setSearchTeam(searchTeam);
            }
        });

        return  binding.getRoot();
    }
}