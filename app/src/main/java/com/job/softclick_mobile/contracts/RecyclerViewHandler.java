package com.job.softclick_mobile.contracts;

import java.util.List;

public interface RecyclerViewHandler<T> {
    default void onItemClick(int position){

    }
    default void onItemClick(List<T> tList, int position) {

    }
}