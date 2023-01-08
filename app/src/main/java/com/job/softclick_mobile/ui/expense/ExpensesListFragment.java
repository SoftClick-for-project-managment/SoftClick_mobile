package com.job.softclick_mobile.ui.expense;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.ExpenseListAdapter;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.viewmodels.expense.ExpenseViewModel;
import com.job.softclick_mobile.viewmodels.expense.IExpenseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExpensesListFragment extends Fragment implements RecyclerViewHandler<Expense>{
    private RecyclerView recyclerView;
    private ExpenseListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addButton;
    private List<Expense> expenseList = new ArrayList<>();
    private ArrayList<Expense> expenseArrayList;
    private IExpenseViewModel expenseViewModel;
    private ProgressBar progressBar;
    private long incomeValue=0;
    private long expenseValue=0;
    View view;

    public ExpensesListFragment() {
        // Required empty public constructor
    }

    public static ExpensesListFragment newInstance(String param1, String param2) {
        ExpensesListFragment fragment = new ExpensesListFragment();
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
        view = inflater.inflate(R.layout.fragment_expenses_list, container, false);
        recyclerView = view.findViewById(R.id.recycler) ;
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);


        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        expenseViewModel.getAll().geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {

            @Override
            public void onChanged(Object o) {
                progressBar.setVisibility(View.INVISIBLE);
                Throwable error = (Throwable) o;
                Log.d("ERR", error.getMessage());
            }
        });

        expenseViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {

            @Override
            public void onChanged(List<Expense> expenses) {
                Log.d("expenses: ",expenses.toString());
                expenseArrayList = new ArrayList<>();

                expenses.forEach(expense -> {
                    expenseArrayList.add(expense);
                });

                progressBar.setVisibility(View.INVISIBLE);
                incomeValue=0;expenseValue=0;
                for(Expense e:expenseArrayList){
                    if (e.getTypeExpense().equals("income")){
                        incomeValue+=e.getAmount();
                        Log.d("3","incomeValue"+incomeValue);


                    }else {
                        expenseValue+=e.getAmount();
                        Log.d("4","expenseValue"+expenseValue);

                    }
                }
                setUpGraph();
                refreshUi();
            }
        });

        addButton = this.getActivity().findViewById(R.id.addButton);
        if(addButton != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,(Fragment) ExpenseFormFragment.class.newInstance()).commit() ;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return view;

    }
    private void setUpGraph() {
        List<PieEntry> pieEntryList = new ArrayList<>();
        List<Integer> colorsList = new ArrayList<>();
        if (incomeValue != 0) {
            pieEntryList.add(new PieEntry(incomeValue, "Income"));
            colorsList.add(getResources().getColor((R.color.teal_700)));

        }
        if (expenseValue != 0) {
            pieEntryList.add(new PieEntry(expenseValue, "Expense"));
            colorsList.add(getResources().getColor((R.color.red)));

        }
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, String.valueOf(incomeValue = expenseValue));
        pieDataSet.setColors(colorsList);
        pieDataSet.setValueTextColor(getResources().getColor((R.color.white)));
        PieData pieData = new PieData(pieDataSet);
        PieChart piechart = view.findViewById(R.id.pieChart);
        piechart.setData(pieData);
        piechart.invalidate();
    }
    private void refreshUi(){
        adapter = new ExpenseListAdapter(expenseArrayList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Expense expense = expenseArrayList.get(position);

        Fragment fragment = new ExpenseDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("expense", expense);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}