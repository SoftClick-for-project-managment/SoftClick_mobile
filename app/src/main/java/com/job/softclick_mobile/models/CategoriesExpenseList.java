package com.job.softclick_mobile.models;

import java.io.Serializable;
import java.util.List;

public class CategoriesExpenseList implements Serializable {
    private List<Expense> nestedList;
    private String itemText;
    private boolean isExpandable;

    public CategoriesExpenseList(List<Expense> nestedList, String itemText, boolean isExpandable) {
        this.nestedList = nestedList;
        this.itemText = itemText;
        this.isExpandable = isExpandable;
    }

    public List<Expense> getNestedList() {
        return nestedList;
    }

    public void setNestedList(List<Expense> nestedList) {
        this.nestedList = nestedList;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
