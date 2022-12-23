package com.job.softclick_mobile.utils;

import androidx.lifecycle.MutableLiveData;

public class LiveResponse<T, E> {
    private MutableLiveData<T> tMutableLiveData;
    private MutableLiveData<E> eMutableLiveData;

    public LiveResponse() {
        tMutableLiveData = new MutableLiveData<>();
        eMutableLiveData = new MutableLiveData<>();
    }

    public LiveResponse(MutableLiveData<T> tMutableLiveData, MutableLiveData<E> eMutableLiveData) {
        this.tMutableLiveData = tMutableLiveData;
        this.eMutableLiveData = eMutableLiveData;
    }

    public MutableLiveData<T> gettMutableLiveData() {
        return tMutableLiveData;
    }

    public void settMutableLiveData(MutableLiveData<T> tMutableLiveData) {
        this.tMutableLiveData = tMutableLiveData;
    }

    public MutableLiveData<E> geteMutableLiveData() {
        return eMutableLiveData;
    }

    public void seteMutableLiveData(MutableLiveData<E> eMutableLiveData) {
        this.eMutableLiveData = eMutableLiveData;
    }
}
