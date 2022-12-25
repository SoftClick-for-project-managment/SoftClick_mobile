package com.job.softclick_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Status implements Serializable {
    @SerializedName("idStatus")
    @Expose
    private Integer idEtat;
    @SerializedName("nameStatus")
    @Expose
    private String nameEtat;
    private Integer nbrJourRetard;

    public Status(Integer idEtat, String nameEtat, Integer nbrJourRetard) {
        this.idEtat = idEtat;
        this.nameEtat = nameEtat;
        this.nbrJourRetard = nbrJourRetard;
    }

    public Status(String nameEtat) {
        this.nameEtat = nameEtat;
    }

    public Integer getIdEtat() {
        return idEtat;
    }

    public String getNameEtat() {
        return nameEtat;
    }

    public void setNameEtat(String nameEtat) {
        this.nameEtat = nameEtat;
    }

    public Integer getNbrJourRetard() {
        return nbrJourRetard;
    }

    public void setNbrJourRetard(Integer nbrJourRetard) {
        this.nbrJourRetard = nbrJourRetard;
    }
}
