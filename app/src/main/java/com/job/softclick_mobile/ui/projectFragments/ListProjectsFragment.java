package com.job.softclick_mobile.ui.projectFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.MainRecyclerAdapter;
import com.job.softclick_mobile.adapters.RvItemClickListener;

import com.job.softclick_mobile.databinding.FragmentListProjectsBinding;
import com.job.softclick_mobile.databinding.FragmentProjectSearchBinding;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Project_section;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.viewmodels.ActivitySharedViewModel;
import com.job.softclick_mobile.viewmodels.project.IProjectViewModel;
import com.job.softclick_mobile.viewmodels.project.ProjectViewModel;
import com.job.softclick_mobile.viewmodels.task.ITaskViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListProjectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListProjectsFragment extends Fragment implements RvItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentListProjectsBinding binding;
    private FragmentProjectSearchBinding searchbind;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ActivitySharedViewModel activitySharedViewModel;
    private IProjectViewModel projectViewModel;
    List<Project_section> sections = new ArrayList<>();
    RecyclerView mainRecyclerView;
    private FloatingActionButton addButton;
    private TextView search;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private ProgressBar progressBar;

    public ListProjectsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListProjectsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListProjectsFragment newInstance(String param1, String param2) {
        ListProjectsFragment fragment = new ListProjectsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activitySharedViewModel =  new ViewModelProvider(getActivity()).get(ActivitySharedViewModel.class);
        activitySharedViewModel.getSearchProject().observe(getViewLifecycleOwner(), new Observer<Project>() {
            @Override
            public void onChanged(Project project) {

                searchProject(project,mainRecyclerAdapter );
                Toast.makeText(getActivity().getApplicationContext(), project.getNameProject(), Toast.LENGTH_SHORT).show();
            }
        });
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

        binding = FragmentListProjectsBinding.inflate(inflater, container, false);
        searchbind = FragmentProjectSearchBinding.inflate(inflater, container, false);
        progressBar = binding.progressBar;
    //    initData();
        mainRecyclerView = binding.mainRecycleView;
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
         mainRecyclerAdapter = new MainRecyclerAdapter(sections, this);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        projectViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {

                HashMap<String,Project_section> projects_pairs = new HashMap<>();

                for (Project project:projects
                     ) {
                    String priorityName = project.getProjectPriority().getNamePriority();

                    if(projects_pairs.containsKey(priorityName)){
                        projects_pairs.get(priorityName).addProjectToSection(project);
                    }else{
                        Project_section new_project_section = new Project_section(priorityName,new ArrayList<>());
                        new_project_section.addProjectToSection(project);
                        projects_pairs.put(priorityName,new_project_section);
                    }
                }

                List<Project_section> project_sectionList = new ArrayList<Project_section>(projects_pairs.values());
                sections = project_sectionList;
                mainRecyclerAdapter.setProject_sectionList(sections);
                mainRecyclerAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });


        addButton = this.getActivity().findViewById(R.id.addButton);

        if (addButton != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, (Fragment) AddProjectFragment.class.newInstance(), "ADD").addToBackStack("ADD").commit();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            });
        }



        return binding.getRoot();
    }




    @Override
    public void onChildItemClick(int parentPosition, int childPosition, String item) {
        Toast.makeText(getContext(), item + "is selected", Toast.LENGTH_SHORT).show();
        Project project = sections.get(parentPosition).getProjects().get(childPosition);
        Fragment fragment = new DetailProjectFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        //          getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,(Fragment) DetailProjectFragment.class.newInstance(),"DET").addToBackStack("DET").commit() ;

    }
    public void searchProject(Project project,MainRecyclerAdapter mainRecyclerAdapter ){
        projectViewModel.search(project).gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                HashMap<String,Project_section> projects_pairs = new HashMap<>();

                for (Project project:projects
                ) {
                    String priorityName = project.getProjectPriority().getNamePriority();

                    if(projects_pairs.containsKey(priorityName)){
                        projects_pairs.get(priorityName).addProjectToSection(project);
                    }else{
                        Project_section new_project_section = new Project_section(priorityName,new ArrayList<>());
                        new_project_section.addProjectToSection(project);
                        projects_pairs.put(priorityName,new_project_section);
                    }
                }

                List<Project_section> project_sectionList = new ArrayList<Project_section>(projects_pairs.values());
                sections = project_sectionList;
                mainRecyclerAdapter.setProject_sectionList(sections);
                //mainRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
}