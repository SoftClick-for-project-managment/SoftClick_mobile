package com.job.softclick_mobile.ui.Tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.ItemAdapter;
import com.job.softclick_mobile.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.models.StatusTaskList;
import com.job.softclick_mobile.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends Fragment implements RecyclerViewHandler<Task> {
    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private List<StatusTaskList> mList;// StatusTaskList == String
    private ItemAdapter adapter;
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
        recyclerView =taskListview.findViewById(R.id.main_recyclervie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();

        List<Task> todoTaskList = new ArrayList<>();

        todoTaskList.add(new Task("Pickles and Chutneys","To do","hhhh","gggggg","cnccccnc"));
        todoTaskList.add(new Task("Readymade Meals","To do","hhhh","gggggg","cnccccnc"));
        todoTaskList.add(new Task("Chyawanprash and Health Foods","To do","hhhh","gggggg","cnccccnc"));
        todoTaskList.add(new Task("Pasta and Soups","To do","hhhhh","gggggg","cnccccnc"));
        todoTaskList.add(new Task("Sauces and Ketchup","To do","TO do","gggggg","cnccccnc"));
        todoTaskList.add(new Task("Namkeen and Snacks","To do","TO do","gggggg","cnccccnc"));
        todoTaskList.add(new Task("Honey and Spreads","To do","TO do","gggggg","cnccccnc"));

        StatusTaskList todoTaskListWrapper = new StatusTaskList(todoTaskList, "Todo");

        List<Task> onprogressTaskList= new ArrayList<>();
        onprogressTaskList.add(new Task("Book","On progress","hhhh","gggggg","cnccccnc"));
        onprogressTaskList.add(new Task("Pen","On progress","hhhh","gggggg","cnccccnc"));
        onprogressTaskList.add(new Task("Office Chair","On progress","hhhh","gggggg","cnccccnc"));
        onprogressTaskList.add(new Task("Pencil","On progress","hhhh","gggggg","cnccccnc"));
        onprogressTaskList.add(new Task("Eraser","On progress","hhhh","gggggg","cnccccnc"));
        onprogressTaskList.add(new Task("Map","On progress","hhhh","gggggg","cnccccnc"));
        onprogressTaskList.add(new Task("Office Table","On progress","hhhh","gggggg","cnccccnc"));

        StatusTaskList onprogressTaskListWrapper = new StatusTaskList(onprogressTaskList, "Onprogress");

        List<Task> doneTaskList = new ArrayList<>();
        doneTaskList.add(new Task("Decorates","done","hhhh","gggggg","cnccccnc"));
        doneTaskList.add(new Task("Tea Table","done","hhhh","gggggg","cnccccnc"));
        doneTaskList.add(new Task("Office Chair","done","hhhh","gggggg","cnccccnc"));
        doneTaskList.add(new Task("Pencil","done","hhhh","gggggg","cnccccnc"));
        doneTaskList.add(new Task("Eraser","done","hhhh","gggggg","cnccccnc"));
        doneTaskList.add(new Task("Map","done","hhhh","gggggg","cnccccnc"));
        doneTaskList.add(new Task("Office Table","done","hhhh","gggggg","cnccccnc"));

        StatusTaskList doneTaskListWrapper = new StatusTaskList(doneTaskList, "done");


        List<Task> overdueTaskList = new ArrayList<>();
        overdueTaskList.add(new Task("Decorates","Overdue","hhhh","gggggg","cnccccnc"));
        overdueTaskList.add(new Task("Tea Table","Overdue","hhhh","gggggg","cnccccnc"));
        overdueTaskList.add(new Task("Office Chair","Overdue","hhhh","gggggg","cnccccnc"));
        overdueTaskList.add(new Task("Pencil","Overdue","hhhh","gggggg","cnccccnc"));
        overdueTaskList.add(new Task("Eraser","Overdue","hhhh","gggggg","cnccccnc"));
        overdueTaskList.add(new Task("Map","Overdue","hhhh","gggggg","cnccccnc"));
        overdueTaskList.add(new Task("Office Table","Overdue","hhhh","gggggg","cnccccnc"));

        StatusTaskList overdueTaskListWrapper = new StatusTaskList(overdueTaskList, "done");



        mList.add( todoTaskListWrapper);
        mList.add(overdueTaskListWrapper);
        mList.add(doneTaskListWrapper);
        mList.add(onprogressTaskListWrapper);


        adapter = new ItemAdapter(mList, this);
        recyclerView.setAdapter(adapter);


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