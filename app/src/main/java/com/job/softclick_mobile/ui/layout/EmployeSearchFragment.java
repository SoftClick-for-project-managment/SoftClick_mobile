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
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentClientSearchBinding;
import com.job.softclick_mobile.databinding.FragmentEmployeSearchBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Domain;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Skill;
import com.job.softclick_mobile.viewmodels.ActivitySharedViewModel;
import com.job.softclick_mobile.viewmodels.domains.DomainViewModel;
import com.job.softclick_mobile.viewmodels.employees.ISkillViewModel;
import com.job.softclick_mobile.viewmodels.employees.SkillViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  FragmentEmployeSearchBinding binding;
    private TextView searchbtn;
    private EditText firstName,lastName,function;
    private AutoCompleteTextView skillCombo;
    private ISkillViewModel skillViewModel;
    private ActivitySharedViewModel sharedViewModel;

    HashMap<String,Long> skill_pairs = new HashMap<>();

    public EmployeSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeSearchFragment newInstance(String param1, String param2) {
        EmployeSearchFragment fragment = new EmployeSearchFragment();
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
        binding= FragmentEmployeSearchBinding.inflate(inflater, container, false);
        skillViewModel = new ViewModelProvider(this).get(SkillViewModel.class);


        firstName = binding.firstNameEmployeInput;
        lastName = binding.lastNameEmployeInput;
        function=binding.FunctionEmployeInput;
        skillCombo = binding.skillEmploye;
        searchbtn = binding.searchbtn;

        skillViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Skill>>() {

            @Override
            public void onChanged(List<Skill> skills) {

                try {
                    skills.forEach(skill -> {
                        skill_pairs.put(skill.getSkillName(), skill.getId());
                    });
                    skill_pairs.put(" ",null);
                    List<String> skill_names = new ArrayList<>(skill_pairs.keySet());
                    ArrayAdapter<String> adapter_skill = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, skill_names);
                    skillCombo.setAdapter(adapter_skill);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }

        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = firstName.getText().toString();
                String last_name = lastName.getText().toString();
                String function_employe = function.getText().toString();
                Long skill_id = skill_pairs.get(skillCombo.getText().toString());
                Skill skill = null;

                if(skill_id != null) {
                    skill = new Skill();
                    skill.setId(skill_id);
                }

                Employee searchEmploye = new Employee();
                searchEmploye.setEmployeeFirstName(first_name);
                searchEmploye.setEmployeeLastName(last_name);
                searchEmploye.setEmployeeFunction(function_employe);
                searchEmploye.setSkills(new ArraySet<>());
                searchEmploye.addSkill(skill);
                
                
                sharedViewModel.setSearchEmployee(searchEmploye);
            }
        });

        return binding.getRoot();
    }


}