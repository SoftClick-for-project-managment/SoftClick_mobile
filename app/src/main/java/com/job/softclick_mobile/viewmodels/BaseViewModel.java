package com.job.softclick_mobile.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.job.softclick_mobile.repositories.IBaseRepository;
import com.job.softclick_mobile.utils.LiveResponse;

public class BaseViewModel<T, Key> extends AndroidViewModel implements IBaseViewModel<T, Key> {
    private IBaseRepository<T, Key> repository;

    public BaseViewModel(@NonNull Application application, @NonNull IBaseRepository repository) {
        super(application);
        this.repository = repository;
    }

    @Override
    public LiveResponse getAll() {
        return repository.getAll();
    }

    @Override
    public LiveResponse getSingle(Key key) {
        return repository.getSingle(key);
    }

    @Override
    public LiveResponse create(T item){
        return repository.create(item);
    }

    @Override
    public LiveResponse update(Key key, T item){
        return new LiveResponse<>();
    }


    @Override
    public LiveResponse delete(Key key) {
        return repository.delete(key);
    }


}
