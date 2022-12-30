package com.job.softclick_mobile.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.job.softclick_mobile.databinding.ActivitySignupBinding;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.task.ITaskViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;
import com.job.softclick_mobile.viewmodels.user.IUserViewModel;
import com.job.softclick_mobile.viewmodels.user.UserViewModel;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private IUserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    public void signup(View view){

        /*LiveResponse userGetAllLiveResponse = userViewModel.getAll();

        userGetAllLiveResponse.geteMutableLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                Throwable error = (Throwable) o;
                Log.d("ERR", error.getMessage());
            }
        });*/

    }

    public void goToLoginPage(View view){
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}