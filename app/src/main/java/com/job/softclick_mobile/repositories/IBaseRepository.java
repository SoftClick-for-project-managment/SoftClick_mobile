package com.job.softclick_mobile.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface IBaseRepository<T, Key> {
    LiveData<List<T>> getAll();
    LiveData<T> getSingle(Key key);
    void create(T t);
    void update(Key key, T t);
    void delete(Key key);
}
