package com.job.softclick_mobile.ui.layout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
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
    private  FragmentProjectSearchBinding binding;
    private ActivitySharedViewModel activitySharedViewModel;

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
        searchbtn = binding.searchbtn;
        nameProject = binding.nameProjectInput;

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Domain domain = new Domain(2l, "info");
                Status status = new Status(1l,"testing");
                Priority priority=new Priority(1,5f,"uergent");
                Employee chef = new Employee();chef.setId(5l);
                Project project = new Project(nameProject.getText().toString(), domain,chef,status,priority);
                activitySharedViewModel.setSearchProject(project);
            }
        });
         return  binding.getRoot();
    }
}