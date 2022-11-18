package com.job.softclick_mobile.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.ExpenseListAdapter;
import com.job.softclick_mobile.databinding.ExpensesListBinding;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.ExpenseModel;
import com.job.softclick_mobile.models.FakeExpensesData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpensesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensesListFragment extends Fragment implements OnItemsClick {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ExpenseListAdapter expenseListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    Intent intent;
    private long income=0;
    private long expense=0;
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpensesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpensesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpensesListFragment newInstance(String param1, String param2) {
        ExpensesListFragment fragment = new ExpensesListFragment();
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
        List <ExpenseModel> data=getData();

        expenseListAdapter=new ExpenseListAdapter(data,this);
        view= inflater.inflate(R.layout.fragment_expenses_list, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(expenseListAdapter);
        intent=new Intent(getActivity(),AddExpenseActivity.class);
        for(ExpenseModel e:data){
            if (e.getType()=="income"){
                income+=e.getAmount();
            }else{
                expense+=e.getAmount();
            }
        }
        setUpGraph();
        return view;
    }

    private void setUpGraph() {
        List <PieEntry> pieEntryList=new ArrayList<>();
        List <Integer> colorsList =new ArrayList<>();
        if(income!=0){
            pieEntryList.add(new PieEntry(income,"Income"));
            colorsList.add(getResources().getColor((R.color.teal_700)));

        }
        if(expense!=0){
            pieEntryList.add(new PieEntry(expense,"Expense"));
            colorsList.add(getResources().getColor((R.color.red)));

        }
        PieDataSet pieDataSet=new PieDataSet(pieEntryList,String.valueOf(income=expense));
        pieDataSet.setColors(colorsList);
        pieDataSet.setValueTextColor(getResources().getColor((R.color.white)));
        PieData pieData=new PieData(pieDataSet);
        PieChart piechart= view.findViewById(R.id.pieChart);
        piechart.setData(pieData);
        piechart.invalidate();
        addButton = this.getActivity().findViewById(R.id.addButton);
        if(addButton != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),AddExpenseActivity.class);
                    startActivity(intent);

                }
            });
        }


    }

    private List<ExpenseModel> getData(){

        FakeExpensesData fakeData=new FakeExpensesData();
        return fakeData.getFakeData();

    }

    @Override
    public void onClick(ExpenseModel expenseModel) {
        intent.putExtra("model",expenseModel);
        startActivity(intent);
    }
}