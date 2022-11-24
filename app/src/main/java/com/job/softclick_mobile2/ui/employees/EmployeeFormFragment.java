package com.job.softclick_mobile2.ui.employees;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.softclick_mobile2.R;
import com.job.softclick_mobile2.databinding.ActivityMenuBinding;
import com.job.softclick_mobile2.databinding.FragmentEmployeeFormBinding;
import com.job.softclick_mobile2.models.Employee;
import com.job.softclick_mobile2.ui.layout.FooterFragment;

public class EmployeeFormFragment extends Fragment {

    private FragmentEmployeeFormBinding binding;
    private ActivityMenuBinding menuBinding;
    private Employee employee;


    public EmployeeFormFragment() {
        // Required empty public constructor
    }

    public static EmployeeFormFragment newInstance(String param1, String param2) {
        EmployeeFormFragment fragment = new EmployeeFormFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
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
        binding = FragmentEmployeeFormBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if(employee != null) {
            binding.firstName.setText(employee.getEmployeeFirstName());
            binding.lastName.setText(employee.getEmployeeLastName());
            binding.employeeEmail.setText(employee.getEmployeeEmail());
            binding.employeePhone.setText(employee.getEmployeePhone());
            binding.employeeFunction.setText(employee.getEmployeeFunction());

            binding.pageTitle.setText("Employee Edition ");
            binding.createEmployeeBtn.setText("Edit");

            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmployeeDetailsFragment employeeDetailsFragment = new EmployeeDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("employee", employee);
                    employeeDetailsFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,employeeDetailsFragment).commit();
                }
            });
        }

        else{
            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmployeeListFragment employeeListFragment=new EmployeeListFragment();
                    FooterFragment footerFragment=new FooterFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,employeeListFragment).commit();
                }
            });}


        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        return view;
    }
}