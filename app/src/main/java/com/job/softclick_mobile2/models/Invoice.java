package com.job.softclick_mobile2.models;

import java.io.Serializable;

public class Invoice implements Serializable {
    private long id;
    private String date;
    private String total;


    public Invoice() {
    }

    public Invoice(String date, String total) {
        this.date = date;
        this.total = total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
