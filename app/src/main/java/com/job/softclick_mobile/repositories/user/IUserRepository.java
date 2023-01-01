package com.job.softclick_mobile.repositories.user;

import com.job.softclick_mobile.models.User;
import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.utils.LiveResponse;

public interface IUserRepository extends IBaseRepository<User, Long> {
    LiveResponse getAuthenticated();
    LiveResponse verify(String code);
}
