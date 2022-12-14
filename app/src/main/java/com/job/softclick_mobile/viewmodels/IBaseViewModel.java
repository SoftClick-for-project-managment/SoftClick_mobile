package com.job.softclick_mobile.viewmodels;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface IBaseViewModel<T, Key> {
    LiveData<List<T>> getAll();
    LiveData<T> getSingle(Key key);
    void create(T t);
    void update(Key key, T t);
    void delete(Key key);
}
