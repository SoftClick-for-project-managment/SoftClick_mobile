package com.job.softclick_mobile.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class TaskList extends Fragment implements RecyclerViewHandler<Task> {
    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private List<StatusTaskList> mList;
    private List<Status> statusList;
    private ItemAdapter adapter;
    private ITaskViewModel taskViewModel;
    private IStatusViewModel statusViewModel;

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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View taskListview =  inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = taskListview.findViewById(R.id.main_recyclervie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ViewModels
        statusViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        statusViewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<Status>>() {
            @Override
            public void onChanged(List<Status> sList) {
                statusList = sList;
            }
        });

        taskViewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                mList = new ArrayList<>();
                StatusTaskList statusTaskList = new StatusTaskList();
                ArrayList<Task> sTaskList = new ArrayList<>();
                statusList.forEach(s -> {
                    statusTaskList.setItemText(s.getNameEtat());
                    tasks.forEach(t -> {
                        if (t.getStatus().getIdEtat() == s.getIdEtat()){
                            sTaskList.add(t);
                        }
                    });
                    statusTaskList.setNestedList(sTaskList);
                    mList.add(statusTaskList);
                    sTaskList.clear();
                });

                refreshUi();
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