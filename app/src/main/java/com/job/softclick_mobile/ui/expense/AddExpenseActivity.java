package com.job.softclick_mobile.ui.expense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityAddExpenseBinding;
import com.job.softclick_mobile.models.ExpenseModel;
import com.job.softclick_mobile.models.FakeExpensesData;
import com.job.softclick_mobile.ui.layout.MenuActivity;

import java.util.Calendar;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    private String type;
    private ActivityAddExpenseBinding binding;
    private ExpenseModel expenseModel;
    FakeExpensesData fakeData=new FakeExpensesData();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        expenseModel=(ExpenseModel) getIntent().getSerializableExtra("model");
         toolbar = findViewById(R.id.toolbar);



        if(expenseModel!=null){
            type=expenseModel.getType();
            toolbar.setTitle(type.toUpperCase()+" Details");
            binding.amount.setText(String.valueOf(expenseModel.getAmount()));
            binding.category.setText(expenseModel.getCategory());
            binding.project.setText(expenseModel.getProject());
            binding.description.setText(expenseModel.getDescription());
            if(type.equals("income")){
                binding.incomeRadio.setChecked(true);
            }else{
                binding.expenseRadio.setChecked(true);
            }
        }else{
            toolbar.setTitle("Expense's Form");
        }


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

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(expenseModel!=null){
            getMenuInflater().inflate(R.menu.update_expense_menu,menu);
        }else {
            getMenuInflater().inflate(R.menu.add_expense_menu, menu);
        }
            return true;

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id==R.id.saveExpense ){

            if(expenseModel==null){
                createExpense();
            }else{

                updateExpense();
            }
            Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(this, MenuActivity.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.deleteExpense){
            //delete(id)
            Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(this,MenuActivity.class);
            startActivity(intent);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }




    private void createExpense()  {
            String expenseId = UUID.randomUUID().toString();
            String amount = binding.amount.getText().toString();
            String description = binding.description.getText().toString();
            String project=binding.project.getText().toString();
            String category=binding.category.getText().toString();
            boolean incomeChecked = binding.incomeRadio.isChecked();
            String type;
            if (incomeChecked) {
                type = "income";
            } else {
                type = "expense";
            }
            if (amount.trim().length() == 0) {
                binding.amount.setError("Empty ");
                return;
            }

            long time = Calendar.getInstance().getTimeInMillis();
            ExpenseModel expenseModel = new ExpenseModel(expenseId, description, Long.parseLong(amount), time, type,category,project);
            fakeData.addExpense(expenseModel);
// Define th
    }
    private void updateExpense()  {
        String expenseId =expenseModel.getExpenseId();
        String amount = binding.amount.getText().toString();
        String description = binding.description.getText().toString();
        String project=binding.project.getText().toString();
        String category=binding.category.getText().toString();
        boolean incomeChecked = binding.incomeRadio.isChecked();
        String type;
        if (incomeChecked) {
            type = "income";
        } else {
            type = "expense";
        }
        if (amount.trim().length() == 0) {
            binding.amount.setError("Empty ");
            return;
        }

        long time = Calendar.getInstance().getTimeInMillis();
        ExpenseModel model = new ExpenseModel(expenseId, description, Long.parseLong(amount), time, type,category,project);
        fakeData.addExpense(model);

// Define th
    }



    }

