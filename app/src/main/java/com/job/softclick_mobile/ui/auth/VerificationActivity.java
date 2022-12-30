package com.job.softclick_mobile.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.job.softclick_mobile.databinding.ActivityVerificationBinding;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.user.IUserViewModel;
import com.job.softclick_mobile.viewmodels.user.UserViewModel;

public class VerificationActivity extends AppCompatActivity {
    private ActivityVerificationBinding binding;
    private IUserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setContentView(view);
    }

    public void verify(View view){
        /*binding.progressBar.setVisibility(View.INVISIBLE);

        LiveResponse<Boolean, Throwable> userVerifyLiveResponse = userViewModel.getAll();


        userVerifyLiveResponse.geteMutableLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                Throwable error = (Throwable) o;
                Log.d("ERR", error.getMessage());
            }
        });*/

    }
}