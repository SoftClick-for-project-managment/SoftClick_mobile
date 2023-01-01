package com.job.softclick_mobile.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.job.softclick_mobile.databinding.ActivityVerificationBinding;
import com.job.softclick_mobile.ui.layout.MenuActivity;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.user.IUserViewModel;
import com.job.softclick_mobile.viewmodels.user.UserViewModel;

import java.io.IOException;

import retrofit2.HttpException;

public class VerificationActivity extends AppCompatActivity {
    private ActivityVerificationBinding binding;
    private IUserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Intent intent = new Intent(VerificationActivity.this, MenuActivity.class);
        startActivity(intent);
        VerificationActivity.this.finish();

        setContentView(view);
    }

    public void verify(View view){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.verificationBody.setVisibility(View.INVISIBLE);

        LiveResponse<Boolean, Throwable> userVerifyLiveResponse = userViewModel.verify(binding.code.getText().toString());

        userVerifyLiveResponse.gettMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent = new Intent(VerificationActivity.this, MenuActivity.class);
                startActivity(intent);
                VerificationActivity.this.finish();
            }
        });

        userVerifyLiveResponse.geteMutableLiveData().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable th) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.verificationBody.setVisibility(View.VISIBLE);
                if (th instanceof IOException){
                    Toast.makeText(VerificationActivity.this, "Check internet connection and try again", Toast.LENGTH_SHORT).show();
                } else if (th instanceof HttpException){
                    Toast.makeText(VerificationActivity.this, "Code is not correct", Toast.LENGTH_SHORT).show();
                }
                Log.d("ERR", th.getMessage());
            }
        });

    }
}