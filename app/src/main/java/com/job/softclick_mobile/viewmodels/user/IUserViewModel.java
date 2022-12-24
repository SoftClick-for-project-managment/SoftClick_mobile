package com.job.softclick_mobile.viewmodels.user;

import com.job.softclick_mobile.models.User;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.IBaseViewModel;

public interface IUserViewModel extends IBaseViewModel<User, Long> {
    LiveResponse login(String username, String password);
    LiveResponse getSingleByUsername(String username);
    LiveResponse verify(String code);
}
