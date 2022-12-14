package com.job.softclick_mobile.models;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable {
    private long id;
    private String date;
    private String total;
    private Client client;
    private Project project;

    public Invoice(String date, String total, Client client, Project project) {
        this.date = date;
        this.total = total;
        this.client = client;
        this.project = project;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
