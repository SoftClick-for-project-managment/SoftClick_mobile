package com.job.softclick_mobile.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentEmployeeDetailsBinding;
import com.job.softclick_mobile.models.Employee;

public class EmployeeDetailsFragment extends Fragment {

    private FragmentEmployeeDetailsBinding binding;
    private Employee employee;

    public EmployeeDetailsFragment() {
        // Required empty public constructor
    }

    public static EmployeeDetailsFragment newInstance(String param1, String param2) {
        EmployeeDetailsFragment fragment = new EmployeeDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            employee = (Employee) getArguments().getSerializable("employee");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEmployeeDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.employeeFirstName.setText(employee.getEmployeeFirstName());
        binding.employeeLastName.setText(employee.getEmployeeLastName());
        binding.employeeEmail.setText(employee.getEmployeeEmail());
        binding.employeePhone.setText(employee.getEmployeePhone());
        binding.employeeFunction.setText(employee.getEmployeeFunction());

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        return view;
    }
}