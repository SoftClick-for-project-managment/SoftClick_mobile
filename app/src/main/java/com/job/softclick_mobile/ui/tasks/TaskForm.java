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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentTaskFormBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.status.IStatusViewModel;
import com.job.softclick_mobile.viewmodels.status.StatusViewModel;
import com.job.softclick_mobile.viewmodels.task.ITaskViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.HttpException;

public class TaskForm extends Fragment {
    private FragmentTaskFormBinding binding;
    private Task task ;
    private ITaskViewModel taskViewModel;
    private IStatusViewModel statusViewModel;

    public TaskForm() {
        // Required empty public constructor
    }

    public static TaskForm newInstance(String param) {
        TaskForm fragment = new TaskForm();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = (Task) getArguments().getSerializable("task");
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

        statusViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Status>>() {
            @Override
            public void onChanged(List<Status> sList) {
                setupStatusSpinner(sList);

                if(task != null) {
                    binding.statustask.setSelection(((ArrayAdapter<String>)binding.statustask.getAdapter()).getPosition(task.getStatus().getNameEtat()));

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
                            updateTask((long) task.getId());
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
                                binding.Enddate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        year, month, day
                );
                datePickerDialog.show();
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
                                binding.timeEnd.setText(String.format("%d:%d", hourOfDay, minute));
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
                                // on below line we are setting date to our edit text.
                                binding.startdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        },
                        year, month, day
                );
                datePickerDialog.show();
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
                                binding.timeStart.setText(String.format("%d:%d", hourOfDay, minute));
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

    private void setupStatusSpinner(List<Status> statuses) {
        Spinner spinner = binding.statustask;

        // Initializing a String List
//        List<String> statusList = new ArrayList<>(Arrays.asList(new String[]{
//                "Select Task status",
//                "OPEN",
//                "IN PROGRESS",
//                "DONE"
//        }));
        List<String> statusList = new ArrayList<>();
        statusList.add("Select Task status");
        statuses.forEach(s -> {
            statusList.add(s.getNameEtat());
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
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent, View view,
                            int position, long id) {

                        // Get the spinner selected item text
                        String selectedItemText = (String) parent
                                .getItemAtPosition(position);

                        // If user change the default selection
                        // First item is disable and
                        // it is used for hint
                        if(position > 0){
                            // Notify the selected item text
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Selected : "
                                            + selectedItemText,
                                    Toast.LENGTH_SHORT).show();
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
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);

        Task task = new Task(
            binding.taskname.getText().toString(),
            binding.startdate.getText().toString()+" "+binding.timeStart.getText().toString(),
            binding.Enddate.getText().toString()+" "+binding.timeEnd.getText().toString(),
            binding.taskdescription.getText().toString(),
            new Status((String)binding.statustask.getSelectedItem()),
            null,
            null,
            null,
            null
        );

        LiveResponse createLiveResponse = taskViewModel.create(task);
        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if((Boolean) o == true ){
                    binding.progressBar.setVisibility(View.GONE);
                    binding.backArrow.callOnClick();
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

    private void updateTask(Long key) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);

        Task task = new Task(
                binding.taskname.getText().toString(),
                binding.startdate.getText().toString()+" "+binding.timeStart.getText().toString(),
                binding.Enddate.getText().toString()+" "+binding.timeEnd.getText().toString(),
                binding.taskdescription.getText().toString(),
                new Status((String)binding.statustask.getSelectedItem()),
                null,
                null,
                null,
                null
        );

        LiveResponse createLiveResponse = taskViewModel.update(key, task);
//        this.task = task;
        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                System.out.println("changed");
                if ((Boolean) o == true) {
                    Toast.makeText(getContext(), "task updated successfully", Toast.LENGTH_SHORT).show();

                    binding.progressBar.setVisibility(View.GONE);
                    binding.backArrow.callOnClick();
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