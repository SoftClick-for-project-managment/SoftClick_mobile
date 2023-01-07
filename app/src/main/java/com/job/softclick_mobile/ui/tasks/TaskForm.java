package com.job.softclick_mobile.ui.tasks;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.EmployeeSelectItemAdapter;
import com.job.softclick_mobile.databinding.FragmentTaskFormBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;
import com.job.softclick_mobile.viewmodels.status.IStatusViewModel;
import com.job.softclick_mobile.viewmodels.status.StatusViewModel;
import com.job.softclick_mobile.viewmodels.task.ITaskViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.Triple;
import retrofit2.HttpException;

public class TaskForm extends Fragment {
    private FragmentTaskFormBinding binding;
    private Task task;
    private Long projectId;
    private ITaskViewModel taskViewModel;
    private IStatusViewModel statusViewModel;
    private IEmployeeViewModel employeeViewModel;
    private Status status;
    private Employee employee;

    public TaskForm() {
        // Required empty public constructor
    }

    public static TaskForm newInstance(Long projectId) {
        TaskForm fragment = new TaskForm();
        Bundle args = new Bundle();
        args.putLong("projectId", projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = (Task) getArguments().getSerializable("task");
            projectId = getArguments().getLong("projectId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;
        binding = FragmentTaskFormBinding.inflate(inflater, container, false);
        View taskView = binding.getRoot();

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        statusViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        // show progress bar and hide the form body initially
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.INVISIBLE);

        MediatorLiveData<Triple<List<Status>, List<Employee>, Throwable>> mediatorLiveData = new MediatorLiveData<>();
        LiveResponse<List<Status>, Throwable> statusLiveResp = statusViewModel.getAll();
        LiveResponse<List<Employee>, Throwable> employeeLiveResp = employeeViewModel.getAll();

        mediatorLiveData.addSource(statusLiveResp.gettMutableLiveData(), new Observer<List<Status>>() {
            @Override
            public void onChanged(@Nullable List<Status> statusList) {
                Triple<List<Status>, List<Employee>, Throwable> triple = mediatorLiveData.getValue();
                mediatorLiveData.setValue(new Triple<>(statusList, triple != null ? triple.getSecond() : null, null));
            }
        });
        mediatorLiveData.addSource(employeeLiveResp.gettMutableLiveData(), new Observer<List<Employee>>() {
            @Override
            public void onChanged(@Nullable List<Employee> employeeList) {
                Triple<List<Status>, List<Employee>, Throwable> triple = mediatorLiveData.getValue();
                mediatorLiveData.setValue(new Triple<>(triple != null ? triple.getFirst() : null, employeeList, null));
            }
        });
        mediatorLiveData.addSource(employeeLiveResp.geteMutableLiveData(), new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                mediatorLiveData.setValue(new Triple<>(null, null, throwable));
            }
        });
        mediatorLiveData.observe(getViewLifecycleOwner(), new Observer<Triple<List<Status>, List<Employee>, Throwable>>() {
            @Override
            public void onChanged(@Nullable Triple<List<Status>, List<Employee>, Throwable> triple) {
                if (triple != null) {
                    if (triple.getFirst() != null && triple.getSecond() != null) {
                        setupStatusSpinner(triple.getFirst());
                        setupEmployeeSpinner(triple.getSecond());
                        if(task != null) {
                            binding.statustask.setSelection(((ArrayAdapter<String>)binding.statustask.getAdapter()).getPosition(task.getStatus().getNameStatus()));
                            binding.taskEmployee.setSelection(((EmployeeSelectItemAdapter)binding.taskEmployee.getAdapter()).getPosition(task.getEmployee().getId()));
                            binding.taskname.setText(task.getName());
                            binding.startdate.setText(task.getStartDate().split(" ")[0]);
                            binding.Enddate.setText(task.getEndDate().split(" ")[0]);
                            binding.timeStart.setText(task.getStartDate().split(" ")[1]);
                            binding.timeEnd.setText(task.getEndDate().split(" ")[1]);
                            binding.taskdescription.setText((task.getDescription()));
                            binding.subheaderTitle.setText("Task Edition ");
                            binding.createtaskBtn.setText("Edit");


                            binding.backArrow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DetailsTask employeeDetailsFragment = new DetailsTask();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("task", task);
                                    employeeDetailsFragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,employeeDetailsFragment).commit();
                                }
                            });

                            binding.createtaskBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("DEBUG", task.getId().toString());
                                    updateTask(task.getId());
                                }
                            });
                        } else {
                            binding.backArrow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TaskList taskList =new TaskList();
                                    FooterFragment footerFragment=new FooterFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,taskList).commit();
                                }
                            });
                        }
                        binding.progressBar.setVisibility(View.GONE);
                        binding.formBody.setVisibility(View.VISIBLE);
                    }
                } else if (triple.getThird() != null) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.formBody.setVisibility(View.VISIBLE);
                    Throwable th = triple.getThird();
                    if (th instanceof HttpException) {
                        binding.backArrow.callOnClick();
                        Toast.makeText(getContext(), "This screen is under maintenance", Toast.LENGTH_SHORT).show();
                    } else if (th instanceof IOException) {
                        Toast.makeText(getContext(), "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("ERR", th.getMessage());
                }
            }
        });

        binding.Enddate.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getActivity(),R.style.datetimepicker,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                                binding.Enddate.setText(sdf.format(c.getTime()));
                            }
                        },
                        year, month, day
                );

                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        binding.timeEnd.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),R.style.datetimepicker,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                binding.timeEnd.setText(sdf.format(c.getTime()));
                            }
                        },
                        hour, minute, false
                );
                timePickerDialog.show();

            }
        });
        binding.startdate.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),R.style.datetimepicker,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                                binding.startdate.setText(sdf.format(c.getTime()));
                            }
                        },
                        year, month, day
                );
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        binding.timeStart.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),R.style.datetimepicker,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                binding.timeStart.setText(sdf.format(c.getTime()));
                            }
                        },
                        hour, minute, false
                );
                timePickerDialog.show();
            }
        });
        binding.createtaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });

        return taskView;
    }

    private void setupEmployeeSpinner(List<Employee> employees) {
        Spinner spinner = binding.taskEmployee;

        employees.forEach(s -> {
            if ((s.getEmployeeFirstName()+" "+s.getEmployeeLastName()).equals(spinner.getSelectedItem())){
                employee = s;
            }
        });

        List<Employee> employeeList = new ArrayList<>();
        Employee dummyEmp = new Employee();
        dummyEmp.setEmployeeFirstName("Select Employee");
        employeeList.add(dummyEmp);
        employees.forEach(e -> employeeList.add(e));

        EmployeeSelectItemAdapter adapter = new EmployeeSelectItemAdapter(getContext(), employeeList);
        spinner.setAdapter(adapter);

        // Spinner on item selected listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent, View view,
                    int position, long id) {
                if (position > 0) {
                    employee = employees.get(position-1);
                }
            }

            @Override
            public void onNothingSelected(
                    AdapterView<?> parent) {
            }
        });
    }

    private void setupStatusSpinner(List<Status> statuses) {
        statuses.forEach(s -> {
            if (s.getNameStatus() == binding.statustask.getSelectedItem()){
                status = s;
            }
        });
        Spinner spinner = binding.statustask;

        List<String> statusList = new ArrayList<>();
        statusList.add("Select status");
        statuses.forEach(s -> {
            statusList.add(s.getNameStatus());
        });

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line,
                statusList
        ){
            @Override
            public boolean isEnabled(int position){
                return position != 0;
            }
            @Override
            public View getDropDownView(
                    int position, View convertView,
                    @NonNull ViewGroup parent) {
                // Get the item view
                View view = super.getDropDownView(
                        position, convertView, parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    textView.setTextColor(Color.GRAY);
                }
                else { textView.setTextColor(Color.BLACK); }
                return view;
            }
        };

        // Set the drop down view resource
        spinnerArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );

        // Spinner on item selected listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent, View view,
                            int position, long id) {
                        // Get the spinner selected item text
//                        String selectedItemText = (String) parent
//                                .getItemAtPosition(position);
                        if (position > 0){
                            status = statuses.get(position-1);
                        }
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                });

        // Finally, data bind the spinner object with adapter
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private void createTask(){
        if (validateForm()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.formBody.setVisibility(View.GONE);

            Task task = new Task(
                    binding.taskname.getText().toString(),
                    binding.startdate.getText().toString()+" "+binding.timeStart.getText().toString(),
                    binding.Enddate.getText().toString()+" "+binding.timeEnd.getText().toString(),
                    binding.taskdescription.getText().toString(),
                    status,
                    projectId,
                    employee,
                    null,
                    null
            );

            LiveResponse createLiveResponse = taskViewModel.create(task);
            createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
                @Override
                public void onChanged(Object o) {
                    if((Boolean) o == true ){
                        Toast.makeText(getContext(), "task created successfully", Toast.LENGTH_SHORT).show();
                        if (projectId != null) {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new FooterFragment()).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TaskList.newInstance(projectId)).commit();
                        } else {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.backArrow.callOnClick();
                        }
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
        } else {
            Toast.makeText(getActivity(), "Oops!, you are missing some fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTask(Long key) {
        if (validateForm()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.formBody.setVisibility(View.GONE);

            Task task = new Task(
                    binding.taskname.getText().toString(),
                    binding.startdate.getText().toString()+" "+binding.timeStart.getText().toString(),
                    binding.Enddate.getText().toString()+" "+binding.timeEnd.getText().toString(),
                    binding.taskdescription.getText().toString(),
                    status,
                    this.task.getProjectId(),
                    employee,
                    null,
                    null
            );
            LiveResponse createLiveResponse = taskViewModel.update(key, task);
            createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
                @Override
                public void onChanged(Object o) {
                    System.out.println("changed");
                    if ((Boolean) o == true) {
                        Toast.makeText(getContext(), "task updated successfully", Toast.LENGTH_SHORT).show();
                        if (projectId != null) {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new FooterFragment()).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TaskList.newInstance(projectId)).commit();
                        } else {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.backArrow.callOnClick();
                        }
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
        } else {
            Toast.makeText(getActivity(), "Oops!, you are missing some fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm() {
        if (
                !binding.taskname.getText().toString().equals("")
                && !binding.startdate.getText().toString().equals("")
                && !binding.timeStart.getText().toString().equals("")
                && !binding.Enddate.getText().toString().equals("")
                && !binding.timeEnd.getText().toString().equals("")
                && status != null
                && employee != null
        ) {
            return true;
        } else {
            return false;
        }
    }
}