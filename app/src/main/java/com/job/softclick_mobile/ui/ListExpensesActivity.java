package com.job.softclick_mobile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityLoginBinding;
import com.job.softclick_mobile.databinding.ActivitySignupBinding;
import com.job.softclick_mobile.databinding.ExpensesListBinding;

public class ListExpensesActivity extends AppCompatActivity {
    private ExpensesListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ExpensesListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
}
