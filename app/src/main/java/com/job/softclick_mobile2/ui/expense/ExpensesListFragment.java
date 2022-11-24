package com.job.softclick_mobile2.ui.expense;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.job.softclick_mobile2.R;
import com.job.softclick_mobile2.adapters.ExpenseListAdapter;
import com.job.softclick_mobile2.models.Expense;
import com.job.softclick_mobile2.ui.contracts.RecyclerViewHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpensesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensesListFragment extends Fragment  implements RecyclerViewHandler {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addButton;
    private ArrayList<Expense> expensesArrayList;
    private long income=0;
    private long expense=0;
    private View view;

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

        expensesArrayList= new ArrayList<>();
        expensesArrayList.add(new Expense(UUID.randomUUID().toString(), "blabla", 8347123, 944809, "expense", "frontend ", "UpWork "));
        expensesArrayList.add(new Expense(UUID.randomUUID().toString(), "blabla", 384123, 14809, "income", "frontend ", "SGI"));
        expensesArrayList.add(new Expense(UUID.randomUUID().toString(), "blabla", 348374, 34809, "expense", "UX/UI", "YOUTUBE"));
        expensesArrayList.add(new Expense(UUID.randomUUID().toString(), "blabla", 123348, 344809, "expense", "backend", "DRIBBBLE "));
        expensesArrayList.add(new Expense(UUID.randomUUID().toString(), "blabla", 33433, 144809, "expense", "backend", "UpWork "));
        expensesArrayList.add(new Expense(UUID.randomUUID().toString(), "blabla", 12356, 144809, "income", "frontend ", "UpWork "));
        expensesArrayList.add(new Expense(UUID.randomUUID().toString(), "blabla", 3847123, 234809, "expense", "frontend d", "SPOTIFY "));

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new ExpenseListAdapter(expensesArrayList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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


        for(Expense e:expensesArrayList){
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
        List<PieEntry> pieEntryList = new ArrayList<>();
        List<Integer> colorsList = new ArrayList<>();
        if (income != 0) {
            pieEntryList.add(new PieEntry(income, "Income"));
            colorsList.add(getResources().getColor((R.color.teal_700)));

        }
        if (expense != 0) {
            pieEntryList.add(new PieEntry(expense, "Expense"));
            colorsList.add(getResources().getColor((R.color.red)));

        }
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, String.valueOf(income = expense));
        pieDataSet.setColors(colorsList);
        pieDataSet.setValueTextColor(getResources().getColor((R.color.white)));
        PieData pieData = new PieData(pieDataSet);
        PieChart piechart = view.findViewById(R.id.pieChart);
        piechart.setData(pieData);
        piechart.invalidate();
    }

    @Override
    public void onItemClick(int position) {
        Expense expense= expensesArrayList.get(position);

        Fragment fragment = new DetailExpenseFragment();

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