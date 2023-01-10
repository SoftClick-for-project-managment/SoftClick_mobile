package com.job.softclick_mobile.ui.expense;

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
import com.job.softclick_mobile.databinding.FragmentExpenseFormBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.ExpenseCategory;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.repositories.task.TaskRepository;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.expense.IExpenseViewModel;
import com.job.softclick_mobile.viewmodels.expenseCategories.ExpenseCategoryViewModel;
import com.job.softclick_mobile.viewmodels.expenseCategories.IExpenseCategoryViewModel;

import com.job.softclick_mobile.viewmodels.expense.IExpenseViewModel;
import com.job.softclick_mobile.viewmodels.expense.ExpenseViewModel;
import com.job.softclick_mobile.viewmodels.task.ITaskViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.HttpException;

public class ExpenseFormFragment extends Fragment {
    private FragmentExpenseFormBinding binding;
    private Expense expense;
    private Task task;
    String type;
    private IExpenseViewModel expenseViewModel;
    private IExpenseCategoryViewModel expenseCategoryViewModel;
    private ITaskViewModel taskViewModel;
    private ExpenseCategory expenseCategory;
    private TaskRepository taskRepository;
    public ExpenseFormFragment() {
        // Required empty public constructor
    }

    public static ExpenseFormFragment newInstance(String param) {
        ExpenseFormFragment fragment = new ExpenseFormFragment();
        Bundle args = new Bundle();
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

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;
        binding = FragmentExpenseFormBinding.inflate(inflater, container, false);
        View expenseView = binding.getRoot();
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseCategoryViewModel = new ViewModelProvider(this).get(ExpenseCategoryViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        expenseCategoryViewModel= new ViewModelProvider(this).get(ExpenseCategoryViewModel.class);
        // show progress bar and hide the form body initially
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.INVISIBLE);
        taskViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> sList) {
                setupTaskSpinner(sList);
                if(expense != null) {
                    binding.task.setSelection(((ArrayAdapter<String>)binding.task.getAdapter()).getPosition(expense.getTask().getName()));

                    type=expense.getTypeExpense();
                    binding.amount.setText(String.valueOf(expense.getAmount()));
                    binding.createExpenseBtn.setText("Edit");

                    if(type.equals("income")){
                        binding.incomeRadio.setChecked(true);
                    }else{
                        binding.expenseRadio.setChecked(true);
                    }

                    binding.backArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ExpenseDetailsFragment expenseDetailsFragment = new ExpenseDetailsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("expense", expense);
                            expenseDetailsFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,expenseDetailsFragment).commit();
                        }
                    });

                    binding.createExpenseBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("DEBUG", expense.getId().toString());
                            updateExpense(expense.getId());
                        }
                    });
                }
                else {
                    binding.backArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ExpensesListFragment expenseList =new ExpensesListFragment();
                            FooterFragment footerFragment=new FooterFragment(ExpensesListFragment.class);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,expenseList).commit();
                        }
                    });
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
                }
                binding.progressBar.setVisibility(View.GONE);
                binding.formBody.setVisibility(View.VISIBLE);
            }
        });
        expenseCategoryViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<ExpenseCategory>>() {
            @Override
            public void onChanged(List<ExpenseCategory> sList) {
                setupExpenseCategorySpinner(sList);
                if(expense != null) {
                    binding.category.setSelection(((ArrayAdapter<String>)binding.category.getAdapter()).getPosition(expense.getExpenseCategory().getCategory()));
                    type=expense.getTypeExpense();
                    binding.amount.setText(String.valueOf(expense.getAmount()));
                    binding.createExpenseBtn.setText("Edit");

                    if(type.equals("income")){
                        binding.incomeRadio.setChecked(true);
                    }else{
                        binding.expenseRadio.setChecked(true);
                    }

                    binding.backArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ExpenseDetailsFragment expenseDetailsFragment = new ExpenseDetailsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("expense", expense);
                            expenseDetailsFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,expenseDetailsFragment).commit();
                        }
                    });

                    binding.createExpenseBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("DEBUG", expense.getId().toString());
                            updateExpense(expense.getId());
                        }
                    });
                }
                else {
                    binding.backArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ExpensesListFragment expenseList =new ExpensesListFragment();
                            FooterFragment footerFragment=new FooterFragment(ExpensesListFragment.class);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,expenseList).commit();
                        }
                    });
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
                }
                binding.progressBar.setVisibility(View.GONE);
                binding.formBody.setVisibility(View.VISIBLE);
            }
        });

        binding.createExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExpense();
            }
        });

        return expenseView;
    }

    private void setupTaskSpinner(List<Task> tasks) {
        tasks.forEach(t -> {
            if (t.getName() == binding.task.getSelectedItem()){
                task = t;
            }
        });
        Spinner spinner = binding.task;

        List<String> taskList = new ArrayList<>();
        taskList.add("Select Task ");
        tasks.forEach(t -> {
            taskList.add(t.getName());
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line,
                taskList
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(
                    AdapterView<?> parent, View view,
                    int position, long id) {

                // Get the spinner selected item text
//                        String selectedItemText = (String) parent
//                                .getItemAtPosition(position);
                if (position > 0){
                    task = tasks.get(position-1);
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
    private void setupExpenseCategorySpinner(List<ExpenseCategory> expenseCategories) {
        expenseCategories.forEach(e -> {
            if (e.getCategory()== binding.task.getSelectedItem()){
                expenseCategory=e;
            }
        });
        Spinner spinner = binding.category;

        List<String> expenseCategoryList = new ArrayList<>();
        expenseCategoryList.add("Select Expense Category");
        expenseCategories.forEach(e -> {
            expenseCategoryList.add(e.getCategory());
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line,
                expenseCategoryList
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(
                    AdapterView<?> parent, View view,
                    int position, long id) {

                // Get the spinner selected item text
//                        String selectedItemText = (String) parent
//                                .getItemAtPosition(position);
                if (position > 0){
                    expenseCategory = expenseCategories.get(position-1);
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

    private void createExpense(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);
        long millis = System.currentTimeMillis();
        Date date =new Date(millis);
        Task newTask = new Task();
        newTask.setId(task.getId());
        // ExpenseCategory expenseCategory=new ExpenseCategory(binding.category.getSelectedItem().toString());
        ExpenseCategory expenseCategoryNew =new ExpenseCategory();
        expenseCategoryNew.setId(expenseCategory.getId());
        Expense expense = new Expense(
                Long.parseLong(binding.amount.getText().toString()),
                type,
                date,
                expenseCategoryNew,
                newTask
        );

        LiveResponse createLiveResponse = expenseViewModel.create(expense);
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

    private void updateExpense(Long key) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);

        long millis = System.currentTimeMillis();
        Date date =new Date(millis);
        Timestamp dateStamp =new Timestamp(date.getTime());
        Task taskNew = new Task();
        taskNew.setId(task.getId());
        ExpenseCategory expenseCategoryNew =new ExpenseCategory();
        expenseCategoryNew.setId(expenseCategory.getId());
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
        Expense expense = new Expense(
                Long.parseLong(binding.amount.getText().toString()),
                type,
                dateStamp,
                expenseCategoryNew,
                taskNew
        );
        Log.d("DEBUG", key.toString());
        LiveResponse createLiveResponse = expenseViewModel.update(key, expense);
//        this.expense = expense;
        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                System.out.println("changed");
                if ((Boolean) o == true) {
                    Toast.makeText(getContext(), "expense updated successfully", Toast.LENGTH_SHORT).show();

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