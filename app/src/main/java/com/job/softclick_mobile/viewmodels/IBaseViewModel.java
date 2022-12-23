package com.job.softclick_mobile.viewmodels;

import androidx.lifecycle.LiveData;

import com.job.softclick_mobile.utils.LiveResponse;

import java.util.List;

public interface IBaseViewModel<T, Key> {
    LiveResponse getAll();
    LiveResponse getSingle(Key key);
    LiveResponse create(T t);
    LiveResponse update(Key key, T t);
    LiveResponse delete(Key key);
}
