package com.job.softclick_mobile.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.job.softclick_mobile.databinding.ActivitySignupBinding;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Role;
import com.job.softclick_mobile.models.TokenPeer;
import com.job.softclick_mobile.models.User;
import com.job.softclick_mobile.services.storage.StoredUser;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.user.IUserViewModel;
import com.job.softclick_mobile.viewmodels.user.UserViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.HttpException;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private IUserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        setContentView(view);
    }

    public void signup(View view){
        String firstName = binding.firstName.getText().toString();
        String lastName = binding.lastName.getText().toString();
        String email = binding.email.getText().toString();
        String phone = binding.phone.getText().toString();
        String password = binding.password.getText().toString();
        String username = binding.username.getText().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || username.isEmpty()){
            Toast.makeText(this, "Please fill the required fields", Toast.LENGTH_SHORT).show();
            return;
        } else {
            binding.signupBody.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);

            Employee employee = new Employee();
            employee.setEmployeeFirstName(firstName);
            employee.setEmployeeLastName(lastName);
            employee.setEmployeeEmail(email);
            employee.setEmployeePhone(phone);
            employee.setEmployeeFunction("Business Engineer");

            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setName(Role.ROLE_DIRECTOR);
            roles.add(role);

            User user = new User();
            user.setPassword(password);
            user.setUsername(username);
            user.setEmployee(employee);
            user.setRoles(roles);

            LiveResponse userCreateLiveResponse = userViewModel.create(user);

            userCreateLiveResponse.gettMutableLiveData().observe(this, new Observer() {
                @Override
                public void onChanged(Object o) {
                    login(user);
                }
            });

            userCreateLiveResponse.geteMutableLiveData().observe(this, new Observer() {
                @Override
                public void onChanged(Object o) {
                    Throwable th = (Throwable) o;
                    if (th instanceof IOException){
                        Toast.makeText(SignupActivity.this, "Check you internet connection and try again", Toast.LENGTH_SHORT).show();
                    } else if (th instanceof HttpException) {
                        Toast.makeText(SignupActivity.this, th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Log.d("ERR", th.getMessage());
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.signupBody.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void goToLoginPage(View view){
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        SignupActivity.this.finish();
    }

    private void login(User user){
        LiveResponse<TokenPeer, Throwable> userLoginLiveResponse = userViewModel.login(user.getUsername(), user.getPassword());

        userLoginLiveResponse.gettMutableLiveData().observe(this, new Observer<TokenPeer>() {
            @Override
            public void onChanged(TokenPeer tokenPeer) {
                tokenPeer.setUsername(user.getUsername());
                StoredUser.save(getApplicationContext(), tokenPeer);
                Toast.makeText(SignupActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, VerificationActivity.class);
                startActivity(intent);
                SignupActivity.this.finish();
            }
        });

        userLoginLiveResponse.geteMutableLiveData().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable th) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.signupBody.setVisibility(View.VISIBLE);
                if (th instanceof IOException){
                    Toast.makeText(SignupActivity.this, "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
                Log.d("ERR", th.getMessage());
            }
        });
    }
}