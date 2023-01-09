package com.job.softclick_mobile.ui.layout;

import android.nfc.TagLostException;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentEmployeSearchBinding;
import com.job.softclick_mobile.databinding.FragmentExpenseSearchBinding;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.ExpenseCategory;
import com.job.softclick_mobile.models.Skill;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.viewmodels.ActivitySharedViewModel;
import com.job.softclick_mobile.viewmodels.employees.ISkillViewModel;
import com.job.softclick_mobile.viewmodels.employees.SkillViewModel;
import com.job.softclick_mobile.viewmodels.expense.IExpenseViewModel;
import com.job.softclick_mobile.viewmodels.expenseCategories.ExpenseCategoryViewModel;
import com.job.softclick_mobile.viewmodels.expenseCategories.IExpenseCategoryViewModel;
import com.job.softclick_mobile.viewmodels.task.ITaskViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenseSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentExpenseSearchBinding binding;
    private AutoCompleteTextView taskInvoiceCombo , expenseCategoryConbo;
    private TextView searchbtn ;

    private IExpenseCategoryViewModel expenseCategoryViewModel;
    private ITaskViewModel taskViewModel;
    private ActivitySharedViewModel sharedViewModel;
    HashMap<String,Long> category_pairs = new HashMap<>();
    HashMap<String,Long> task_pairs = new HashMap<>();
    String type ="";
    RadioButton income,expense,allTypes;

    public ExpenseSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpenseSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenseSearchFragment newInstance(String param1, String param2) {
        ExpenseSearchFragment fragment = new ExpenseSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel =  new ViewModelProvider(getActivity()).get(ActivitySharedViewModel.class);

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
        binding= FragmentExpenseSearchBinding.inflate(inflater, container, false);
        expenseCategoryViewModel = new ViewModelProvider(this).get(ExpenseCategoryViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        expenseCategoryConbo = binding.categoryInvoiceCombo;
        taskInvoiceCombo = binding.taskInvoiceCombo;
        income = binding.incomeRadioSearch;
        expense = binding.expenseRadioSearch;
        allTypes = binding.allRadioSearch;
        searchbtn = binding.searchbtn;

        expenseCategoryViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<ExpenseCategory>>() {

            @Override
            public void onChanged(List<ExpenseCategory> expenseCategories) {

                try {
                    expenseCategories.forEach(expenseCategory -> {
                        category_pairs.put(expenseCategory.getCategory(), expenseCategory.getId());
                    });
                    category_pairs.put("ALL",null);
                    List<String> expense_names = new ArrayList<>(category_pairs.keySet());
                    ArrayAdapter<String> adapter_expense = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, expense_names);
                    expenseCategoryConbo.setAdapter(adapter_expense);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }

        });

        taskViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {

            @Override
            public void onChanged(List<Task> tasks) {

                try {
                    tasks.forEach(task -> {
                        task_pairs.put(task.getName(), task.getId());
                    });
                    task_pairs.put("ALL",null);
                    List<String> task_names = new ArrayList<>(task_pairs.keySet());
                    ArrayAdapter<String> adapter_task = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, task_names);
                    taskInvoiceCombo.setAdapter(adapter_task);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }

        });

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="income";
                searchbtn.callOnClick();
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="expense";
                searchbtn.callOnClick();
            }
        });
        allTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="";
                searchbtn.callOnClick();
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long category_id = category_pairs.get(expenseCategoryConbo.getText().toString());
                Long task_id = task_pairs.get(taskInvoiceCombo.getText().toString());


                Task task = null;
                if(task_id != null) {
                    task = new Task();
                    task.setId(task_id);
                }

                ExpenseCategory expenseCategory = null;
                if(category_id != null) {
                    expenseCategory = new ExpenseCategory();
                    expenseCategory.setId(category_id);
                }

                Expense searchExpense = new Expense();
                searchExpense.setTypeExpense(type);
                searchExpense.setExpenseCategory(expenseCategory);
                searchExpense.setTask(task);


                sharedViewModel.setSearchExpense(searchExpense);
            }
        });

        return  binding.getRoot();
    }
}