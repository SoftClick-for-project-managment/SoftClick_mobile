package com.job.softclick_mobile2.ui.expense;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.job.softclick_mobile2.R;
import com.job.softclick_mobile2.databinding.ActivityMenuBinding;
import com.job.softclick_mobile2.databinding.FragmentExpenseFormBinding;
import com.job.softclick_mobile2.models.Expense;
import com.job.softclick_mobile2.ui.layout.FooterFragment;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenseFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseFormFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String type;
    private FragmentExpenseFormBinding binding;
    private Expense expense;
    private ActivityMenuBinding menuBinding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpenseFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpenseFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenseFormFragment newInstance(String param1, String param2) {
        ExpenseFormFragment fragment = new ExpenseFormFragment();
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
            expense = (Expense) getArguments().getSerializable("expense");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExpenseFormBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        if(expense!=null){
            type=expense.getType();
            binding.ExpenseFormTitle.setText(type.toUpperCase()+" Edit");
            binding.amount.setText(String.valueOf(expense.getAmount()));
            binding.category.setText(expense.getCategory());
            binding.project.setText(expense.getProject());
            binding.description.setText(expense.getDescription());
            binding.createExpenseBtn.setText("Edit");

            if(type.equals("income")){
                binding.incomeRadio.setChecked(true);
            }else{
                binding.expenseRadio.setChecked(true);
            }
            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailExpenseFragment expensesDetailsFragment = new DetailExpenseFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("expense", expense);
                    expensesDetailsFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,expensesDetailsFragment).commit();
                }
            });

        }else{
            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpensesListFragment expensesListFragment=new ExpensesListFragment();
                    FooterFragment footerFragment=new FooterFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,expensesListFragment).commit();
                }
            });}
        binding.incomeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="income";
            }
        });
        binding.expenseRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="expense";
            }
        });

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;
        return view;
    }
}