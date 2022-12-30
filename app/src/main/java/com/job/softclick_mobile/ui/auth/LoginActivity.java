package com.job.softclick_mobile.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.job.softclick_mobile.databinding.ActivityLoginBinding;
import com.job.softclick_mobile.models.StatusTaskList;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.models.TokenPeer;
import com.job.softclick_mobile.models.User;
import com.job.softclick_mobile.services.storage.StoredUser;
import com.job.softclick_mobile.ui.layout.MenuActivity;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.user.IUserViewModel;
import com.job.softclick_mobile.viewmodels.user.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private IUserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    public void login(View view){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loginBody.setVisibility(View.INVISIBLE);

        String username = binding.username.getText().toString();
        String password =  binding.password.getText().toString();

        LiveResponse<TokenPeer, Throwable> userLoginLiveResponse =  userViewModel.login(username, password);

        userLoginLiveResponse.gettMutableLiveData().observe(this, new Observer<TokenPeer>() {
            @Override
            public void onChanged(TokenPeer tokenPeer) {
                tokenPeer.setUsername(username);
                StoredUser.save(getApplicationContext(), tokenPeer);
                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                startActivity(intent);
            }
        });

        userLoginLiveResponse.geteMutableLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.progressBar.setVisibility(View.GONE);
                binding.loginBody.setVisibility(View.VISIBLE);
                Throwable error = (Throwable) o;
                Log.d("ERR", error.getMessage());
            }
        });
    }

    public void goToSignupPage(View view){
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}