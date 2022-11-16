package com.job.softclick_mobile.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.MainRecyclerAdapter;
import com.job.softclick_mobile.databinding.ActivityMenuBinding;
import com.job.softclick_mobile.databinding.FragmentListProjectsBinding;
import com.job.softclick_mobile.models.Project_section;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListProjectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListProjectsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentListProjectsBinding binding;
    private ActivityMenuBinding menuBinding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Project_section> sections = new ArrayList<>();
    RecyclerView mainRecyclerView;

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


        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        initData();
        mainRecyclerView = binding.mainRecycleView;

        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(sections);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);

        return binding.getRoot();
    }
    private void initData(){
        String priority_1 = "hight priority";
        List<String> section_priority_1 = new ArrayList<>();
        section_priority_1.add("Violance");section_priority_1.add("wizara important");

        String priority_2 = "meduim priority";
        List<String> section_priority_2 = new ArrayList<>();
        section_priority_2.add("mat3am jami3i");section_priority_2.add("stad footbal");

        String priority_3 = "normal priority";
        List<String> section_priority_3 = new ArrayList<>();
        section_priority_3.add("ecommerce site web");section_priority_3.add("mobile app reservation");

        String priority_4 = "not important";
        List<String> section_priority_4 = new ArrayList<>();
        section_priority_4.add("maintenance stock");section_priority_4.add("maintenance inventair");


        sections.add(new Project_section(priority_1,section_priority_1));
        sections.add(new Project_section(priority_2,section_priority_2));
        sections.add(new Project_section(priority_3,section_priority_3));
        sections.add(new Project_section(priority_4,section_priority_4));
    }
}