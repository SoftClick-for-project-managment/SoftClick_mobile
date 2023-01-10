package com.job.softclick_mobile.ui.employees;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityMenuBinding;
import com.job.softclick_mobile.databinding.FragmentEmployeeFormBinding;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;

import java.io.IOException;

import retrofit2.HttpException;

public class EmployeeFormFragment extends Fragment {

    private FragmentEmployeeFormBinding binding;
    private ActivityMenuBinding menuBinding;
    private Employee employee;
    IEmployeeViewModel employeeViewModel;


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
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

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

            binding.createEmployeeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateEmployee((long) employee.getId());
                }
            });

        }

        else{
            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmployeeListFragment employeeListFragment=new EmployeeListFragment();
                    FooterFragment footerFragment=new FooterFragment(EmployeeListFragment.class);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,employeeListFragment).commit();
                }
            });

            binding.createEmployeeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createEmployee();
                }
            });

        }





        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        return view;
    }

    public void createEmployee(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);

        Employee employee = new Employee(
                null,
                binding.firstName.getText().toString(),
                binding.lastName.getText().toString(),
                binding.employeeFunction.getText().toString(),
                binding.employeeEmail.getText().toString(),
                binding.employeePhone.getText().toString()
        );

        System.out.println("Employee ::: " + employee.getEmployeeEmail());

        LiveResponse createLiveResponse =  employeeViewModel.create(employee);
        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if((Boolean) o == true ){
                    binding.progressBar.setVisibility(View.GONE);
                    binding.back.callOnClick();
                }
            }
        });

        createLiveResponse.geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                Throwable error = (Throwable) o;
                if (error instanceof HttpException) {
                    Log.d("DEBUG", error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(getContext(), "This screen is under maintenance", Toast.LENGTH_SHORT).show();
                } else if (error instanceof IOException) {

                }
                binding.formBody.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Log.d("ERR", error.getMessage());
            }
        });


    }

    public void updateEmployee(Long key){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);

        Employee employee = new Employee(
                "employee4.png",
                binding.firstName.getText().toString(),
                binding.lastName.getText().toString(),
                binding.employeeFunction.getText().toString(),
                binding.employeeEmail.getText().toString(),
                binding.employeePhone.getText().toString()
        );



        LiveResponse createLiveResponse =  employeeViewModel.update(key, employee);

        this.employee = employee;

        System.out.println("Employee ::: " + employee.getEmployeeEmail());

        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                System.out.println("changed");
                if((Boolean) o == true ){
                    binding.progressBar.setVisibility(View.GONE);
                    binding.back.callOnClick();
                    System.out.println("back called");
                }
            }
        });

        createLiveResponse.geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                Throwable error = (Throwable) o;
                if (error instanceof HttpException) {
                    Log.d("DEBUG", error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(getContext(), "This screen is under maintenance", Toast.LENGTH_SHORT).show();
                } else if (error instanceof IOException) {

                }
                binding.formBody.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Log.d("ERR", error.getMessage());
            }
        });


    }
}