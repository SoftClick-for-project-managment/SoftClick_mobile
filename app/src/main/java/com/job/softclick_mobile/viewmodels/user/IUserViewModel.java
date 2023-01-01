package com.job.softclick_mobile.viewmodels.user;

import com.job.softclick_mobile.models.TokenPeer;
import com.job.softclick_mobile.models.User;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.IBaseViewModel;

public interface IUserViewModel extends IBaseViewModel<User, Long> {
    LiveResponse<TokenPeer, Throwable> login(String username, String password);
    LiveResponse<User, Throwable> getAuthenticated();
    LiveResponse<Boolean, Throwable> verify(String code);
    LiveResponse<Boolean, Throwable> sendVerificationCode(String username);
}
