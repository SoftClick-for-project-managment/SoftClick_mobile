package com.job.softclick_mobile.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.job.softclick_mobile.repositories.IBaseRepository;

import java.util.List;

public class BaseViewModel<T, Key> extends AndroidViewModel implements IBaseViewModel<T, Key> {
    private IBaseRepository<T, Key> repository;

    public BaseViewModel(@NonNull Application application, @NonNull IBaseRepository repository) {
        super(application);
        this.repository = repository;
    }

    @Override
    public LiveData<List<T>> getAll() {
        return repository.getAll();
    }

    @Override
    public LiveData<T> getSingle(Key key) {
        return null;
    }

    @Override
    public void create(T item){
        repository.create(item);
    }

    @Override
    public void update(Key key, T item){

    }

    @Override
    public void delete(Key key) {
        repository.delete(key);
    }


}
