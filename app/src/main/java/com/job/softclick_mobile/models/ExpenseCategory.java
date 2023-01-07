package com.job.softclick_mobile.models;

import java.io.Serializable;

public class ExpenseCategory implements Serializable {

    private Long id;
    private String category;

    public ExpenseCategory(){}
    public ExpenseCategory(String category){
        this.category=category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
