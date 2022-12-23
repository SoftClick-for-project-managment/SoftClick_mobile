package com.job.softclick_mobile.repositories;

import androidx.lifecycle.LiveData;

import com.job.softclick_mobile.utils.LiveResponse;

public interface IBaseRepository<T, Key> {
    LiveResponse getAll();
    LiveResponse getSingle(Key key);
    LiveResponse create(T t);
    LiveResponse update(Key key, T t);
    LiveResponse delete(Key key);
}
