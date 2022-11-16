package com.job.softclick_mobile.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.ItemAdapter;
import com.job.softclick_mobile.models.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskList extends Fragment {
    private RecyclerView recyclerView;
    private List<DataModel> mList;
    private ItemAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskList.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskList newInstance(String param1, String param2) {
        TaskList fragment = new TaskList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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

        //list1
        List<String> nestedList1 = new ArrayList<>();
        nestedList1.add("Jams and Honey");
        nestedList1.add("Pickles and Chutneys");
        nestedList1.add("Readymade Meals");
        nestedList1.add("Chyawanprash and Health Foods");
        nestedList1.add("Pasta and Soups");
        nestedList1.add("Sauces and Ketchup");
        nestedList1.add("Namkeen and Snacks");
        nestedList1.add("Honey and Spreads");

        List<String> nestedList2 = new ArrayList<>();
        nestedList2.add("Book");
        nestedList2.add("Pen");
        nestedList2.add("Office Chair");
        nestedList2.add("Pencil");
        nestedList2.add("Eraser");
        nestedList2.add("NoteBook");
        nestedList2.add("Map");
        nestedList2.add("Office Table");

        List<String> nestedList3 = new ArrayList<>();
        nestedList3.add("Decorates");
        nestedList3.add("Tea Table");
        nestedList3.add("Wall Paint");
        nestedList3.add("Furniture");
        nestedList3.add("Bedsits");
        nestedList3.add("Certain");
        nestedList3.add("Namkeen and Snacks");
        nestedList3.add("Honey and Spreads");

        List<String> nestedList4 = new ArrayList<>();
        nestedList4.add("Pasta");
        nestedList4.add("Spices");
        nestedList4.add("Salt");
        nestedList4.add("Chyawanprash");
        nestedList4.add("Maggie");
        nestedList4.add("Sauces and Ketchup");
        nestedList4.add("Snacks");
        nestedList4.add("Kurkure");



        mList.add(new DataModel(nestedList1 , "TO DO"));
        mList.add(new DataModel( nestedList2,"DONE"));
        mList.add(new DataModel( nestedList3,"ON PROGRESS"));
        mList.add(new DataModel(nestedList4 ,"Overdue"));


        adapter = new ItemAdapter(mList);
        recyclerView.setAdapter(adapter);

        return taskListview;

    }
}