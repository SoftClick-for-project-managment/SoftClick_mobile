package com.job.softclick_mobile.viewmodels.user;

import android.app.Application;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.TokenPeer;
import com.job.softclick_mobile.models.User;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.repositories.user.UserRepository;
import com.job.softclick_mobile.services.http.UserApi;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.BaseViewModel;

public class UserViewModel extends BaseViewModel<User, Long> implements IUserViewModel {
    private UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application, new UserRepository());
        userRepository = new UserRepository();
    }

    @Override
    public LiveResponse<TokenPeer, Throwable> login(String username, String password) {
        return userRepository.login(username, password);
    }

    @Override
    public LiveResponse getSingleByUsername(String username) {
        return userRepository.getSingleByUsername(username);
    }

    @Override
    public LiveResponse verify(String code) {
        return userRepository.verify(code);
    }
}
