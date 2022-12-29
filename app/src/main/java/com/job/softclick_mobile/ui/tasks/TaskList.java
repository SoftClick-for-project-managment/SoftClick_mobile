package com.job.softclick_mobile.ui.tasks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.ItemAdapter;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.models.StatusTaskList;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.viewmodels.status.IStatusViewModel;
import com.job.softclick_mobile.viewmodels.status.StatusViewModel;
import com.job.softclick_mobile.viewmodels.task.ITaskViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TaskList extends Fragment implements RecyclerViewHandler<Task> {
    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private List<StatusTaskList> mList = new ArrayList<>();
    private List<Status> statusList = new ArrayList<>();
    private ItemAdapter adapter;
    private ITaskViewModel taskViewModel;
    private IStatusViewModel statusViewModel;
    private ProgressBar progressBar;

    public TaskList() {
        // Required empty public constructor
    }

    public static TaskList newInstance(String param1, String param2) {
        TaskList fragment = new TaskList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View taskListview =  inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = taskListview.findViewById(R.id.main_recyclervie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = taskListview.findViewById(R.id.progressBar);

        // ViewModels
        statusViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        statusViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Status>>() {
            @Override
            public void onChanged(List<Status> sList) {
                statusList = sList;
            }
        });

        statusViewModel.getAll().geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                Throwable error = (Throwable) o;
                Log.d("ERR", error.getMessage());
            }
        });

        taskViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                mList = new ArrayList<>();
                AtomicReference<StatusTaskList> statusTaskList = new AtomicReference<>(new StatusTaskList());
                AtomicReference<ArrayList<Task>> sTaskList = new AtomicReference<>(new ArrayList<>());
                statusList.forEach(s -> {
                    statusTaskList.set(new StatusTaskList());
                    sTaskList.set(new ArrayList<>());
                    statusTaskList.get().setItemText(s.getNameStatus());
                    tasks.forEach(t -> {
                        if (t.getStatus().getIdStatus() == s.getIdStatus()){
                            sTaskList.get().add(t);
                        }
                    });
                    statusTaskList.get().setNestedList(sTaskList.get());
                    mList.add(statusTaskList.get());
                });

                progressBar.setVisibility(View.INVISIBLE);
                refreshUi();
            }
        });

        taskViewModel.getAll().geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                progressBar.setVisibility(View.INVISIBLE);
                Throwable error = (Throwable) o;
                Log.d("ERR", error.getMessage());
            }
        });

        addButton = this.getActivity().findViewById(R.id.addButton);

        if(addButton != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,(Fragment)TaskForm.class.newInstance()).commit() ;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return taskListview;

    }

    private void refreshUi(){
        adapter = new ItemAdapter(mList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(List<Task> taskList, int position) {
        Task task = taskList.get(position);

        Fragment fragment = new DetailsTask();

        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}