package com.job.softclick_mobile.ui.projectFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.MainRecyclerAdapter;
import com.job.softclick_mobile.adapters.RvItemClickListener;
import com.job.softclick_mobile.databinding.FragmentListProjectsBinding;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Project_section;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.viewmodels.project.IProjectViewModel;
import com.job.softclick_mobile.viewmodels.project.ProjectViewModel;
import com.job.softclick_mobile.viewmodels.task.ITaskViewModel;

import java.util.ArrayList;
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


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IProjectViewModel projectViewModel;
    List<Project_section> sections = new ArrayList<>();
    RecyclerView mainRecyclerView;
    private FloatingActionButton addButton;

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
    //    initData();
        mainRecyclerView = binding.mainRecycleView;
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(sections, this);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        projectViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
               Toast.makeText(getActivity().getApplicationContext(), projects.toString(), Toast.LENGTH_SHORT).show();
                Project_section project_section = new Project_section(projects.get(0).getProjectPriority().getNamePriority(),projects);
                sections = new ArrayList<>();
                sections.add(project_section);
                mainRecyclerAdapter.setProject_sectionList(sections);

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

    private void initData() {
        String priority_1 = "hight priority";
        List<Project> section_priority_1 = new ArrayList<>();
        section_priority_1.add(new Project("Violance", "projet detection de violance based A I", 500000d));
        section_priority_1.add(new Project("gestion de dossier", "stocker les dossier legaliseés et les chercher par une simple scan intelligent", 600000d));

        String priority_2 = "meduim priority";
        List<Project> section_priority_2 = new ArrayList<>();
        section_priority_2.add(new Project("mat3am jami3i", "mat3am jami3i , payment reservation , validation de repas ...", 300000d));

        String priority_3 = "normal priority";
        List<Project> section_priority_3 = new ArrayList<>();
        section_priority_3.add(new Project("ecommerce site web", "t-shirt plateform qui automatise les adds on fb et google", 800000d));
        section_priority_3.add(new Project("mobile app reservation", "application mobile de reservation de rendez vous", 200000d));

        String priority_4 = "not important";
        List<Project> section_priority_4 = new ArrayList<>();
        section_priority_4.add(new Project("maintenance stock", "maintenir un site web avec nouvelle technologies", 15000d));
        section_priority_4.add(new Project("maintenance inventair", "maintenir un site web qui suite inventaires dans les entreprises", 350000d));


        sections.add(new Project_section(priority_1, section_priority_1));
        sections.add(new Project_section(priority_2, section_priority_2));
        sections.add(new Project_section(priority_3, section_priority_3));
        sections.add(new Project_section(priority_4, section_priority_4));
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
}