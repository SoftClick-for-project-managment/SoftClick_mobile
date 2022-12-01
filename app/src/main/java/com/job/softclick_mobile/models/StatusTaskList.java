package com.job.softclick_mobile.models;

import java.io.Serializable;
import java.util.List;

public class StatusTaskList implements Serializable {
    private List<Task> nestedList;
    private String itemText;
    private boolean isExpandable;

    public StatusTaskList() {
    }

    public StatusTaskList(List<Task> itemList, String itemText) {
        this.nestedList = itemList;
        this.itemText = itemText;
        this.isExpandable = false;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public List<Task> getNestedList() {
        return nestedList;
    }

    public String getItemText() {
        return itemText;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

}
