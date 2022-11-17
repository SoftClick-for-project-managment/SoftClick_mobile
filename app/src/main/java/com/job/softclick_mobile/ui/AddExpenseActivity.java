package com.job.softclick_mobile.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityAddExpenseBinding;
import com.job.softclick_mobile.models.ExpenseModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {

    private ActivityAddExpenseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add_expense_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id==R.id.saveExpense){
                createExpense();
            Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();

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

// Define th
    }



    }

