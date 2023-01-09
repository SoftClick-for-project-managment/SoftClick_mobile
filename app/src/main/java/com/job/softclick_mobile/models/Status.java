package com.job.softclick_mobile.models;

import java.io.Serializable;

public class Status implements Serializable {
    private Long idStatus;

    private String nameStatus;

    public Status() {
    }

    public Status(Long idStatus, String nameStatus) {
        this.idStatus = idStatus;
        this.nameStatus = nameStatus;
    }

    public Long getIdStatus() {
        return idStatus;
    }

    public Status(String nameStatus) {
        this.nameStatus = nameStatus;
    }

    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
    }

    public String getNameStatus() {
        return nameStatus;
    }

    public void setNameStatus(String nameStatus) {
        this.nameStatus = nameStatus;
    }
}
