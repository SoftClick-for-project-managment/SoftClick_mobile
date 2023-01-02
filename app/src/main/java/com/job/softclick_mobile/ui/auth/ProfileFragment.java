package com.job.softclick_mobile.ui.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentProfileBinding;
import com.job.softclick_mobile.models.User;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.user.IUserViewModel;
import com.job.softclick_mobile.viewmodels.user.UserViewModel;

import java.io.IOException;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private IUserViewModel userViewModel;
    private User authUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new Fragment()).commit() ;
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.profileBody.setVisibility(View.INVISIBLE);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        LiveResponse<User, Throwable> authUserLiveResp = userViewModel.getAuthenticated();
        authUserLiveResp.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                authUser = user;
                binding.employeeName.setText(user.getEmployee().getEmployeeFirstName() +" "+user.getEmployee().getEmployeeLastName());
                binding.employeeEmail.setText(user.getEmployee().getEmployeeEmail());
                binding.username.setText(user.getUsername());
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.profileBody.setVisibility(View.VISIBLE);
            }
        });

        authUserLiveResp.geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable th) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.profileBody.setVisibility(View.VISIBLE);
                if (th instanceof IOException) {
                    Toast.makeText(getActivity(), "Check internet connection and try again", Toast.LENGTH_SHORT).show();
                }
                Log.d("ERR", th.getMessage());
            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });
        return view;
    }

    public void update(View view) {
        String password = binding.password.getText().toString();
        String passwordConf = binding.passwordConf.getText().toString();

        if ( password.equals("") ) {
            Toast.makeText(getActivity(), "Password can not be empty", Toast.LENGTH_SHORT).show();
        } else if ( password.equals(passwordConf) ) {
            binding.profileBody.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);

            User user = new User();
            user.setPassword(password);

            LiveResponse<Boolean, Throwable> updateUserLiveResp = userViewModel.update(authUser.getId(), user);
            updateUserLiveResp.gettMutableLiveData().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.profileBody.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                }
            });
            updateUserLiveResp.geteMutableLiveData().observe(this, new Observer<Throwable>() {
                @Override
                public void onChanged(Throwable th) {
                    if ( th instanceof IOException ) {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        binding.profileBody.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Check internet connection and try again", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("ERR", th.getMessage());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Password did not match", Toast.LENGTH_SHORT).show();
        }
    }
}