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
    public LiveResponse<User, Throwable> getAuthenticated() {
        return userRepository.getAuthenticated();
    }

    @Override
    public LiveResponse<Boolean, Throwable> sendVerificationCode(String username) {
        return null;
    }

    @Override
    public LiveResponse<Boolean, Throwable> verify(String code) {
        return userRepository.verify(code);
    }
}
