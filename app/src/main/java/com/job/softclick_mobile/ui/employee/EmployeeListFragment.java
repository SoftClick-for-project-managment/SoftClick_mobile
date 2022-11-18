package com.job.softclick_mobile.ui.employee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.EmployeeListAdapter;
import com.job.softclick_mobile.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.models.Employee;

import java.util.ArrayList;

public class EmployeeListFragment extends Fragment implements RecyclerViewHandler {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addButton;
    private ArrayList<Employee> employeeArrayList;

    public EmployeeListFragment() {
        // Required empty public constructor
    }

    public static EmployeeListFragment newInstance(String param1, String param2) {
        EmployeeListFragment fragment = new EmployeeListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_list, container, false);
        recyclerView = view.findViewById(R.id.employeeListRecyclerView) ;

        employeeArrayList= new ArrayList<>();
        employeeArrayList.add(new Employee(R.drawable.user_photo, "Tiger", "Nixon", "Talent Acquisition Specialist", "tigernixon@gmail.com", "+2120065354675"));
        employeeArrayList.add(new Employee(R.drawable.user_photo, "Garrett", "Winters", "FullStack Developer", "garrettwinters@gmail.com", "+2120065354675"));
        employeeArrayList.add(new Employee(R.drawable.user_photo, "Brielle", "Williamson", "Front-End Developer", "briellewilliamson@gmail.com", "+2120065354675"));

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new EmployeeListAdapter(employeeArrayList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        addButton = this.getActivity().findViewById(R.id.addButton);
        if(addButton != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,(Fragment) EmployeeFormFragment.class.newInstance()).commit() ;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        return view;
    }

    @Override
    public void onItemClick(int position) {
        Employee employee = employeeArrayList.get(position);

        Fragment fragment = new EmployeeDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("employee", employee);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}