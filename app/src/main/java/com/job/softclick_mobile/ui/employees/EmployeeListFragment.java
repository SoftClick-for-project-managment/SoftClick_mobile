package com.job.softclick_mobile.ui.employees;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.EmployeeListAdapter;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class EmployeeListFragment extends Fragment implements RecyclerViewHandler<Employee>{
    private RecyclerView recyclerView;
    private EmployeeListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addButton;
    private List<Employee> employeeList = new ArrayList<>();
    private ArrayList<Employee> employeeArrayList;
    private IEmployeeViewModel employeeViewModel;

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
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        employeeViewModel.getAll().geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                Throwable error = (Throwable) o;
                Log.d("ERR", error.getMessage());
            }
        });

        employeeViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Employee>>() {
            
            @Override
            public void onChanged(List<Employee> employees) {
                //employeeList = employees;
                employeeArrayList = new ArrayList<>();
                AtomicReference<ArrayList<Employee>> sEmployeeList = new AtomicReference<>(new ArrayList<>());

                employees.forEach(employee -> {
                        employeeArrayList.add(employee);
                });

                refreshUi();
            }
        });

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

    private void refreshUi(){
        Toast.makeText(getActivity(), "refresh uui : " , Toast.LENGTH_SHORT).show();

        adapter = new EmployeeListAdapter(employeeArrayList, this);
        recyclerView.setAdapter(adapter);
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