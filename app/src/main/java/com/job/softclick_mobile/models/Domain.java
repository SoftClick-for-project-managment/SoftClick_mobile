package com.job.softclick_mobile.models;

import java.io.Serializable;

public class Domain implements Serializable {
    private  Long idDomain;
    private String nameDomain;

    public Domain() {
    }

    public Domain(Long idDomain, String nameDomain) {
        this.idDomain = idDomain;
        this.nameDomain = nameDomain;
    }


    public Long getIdDomain() {
        return idDomain;
    }

    public void setIdDomain(Long idDomain) {
        this.idDomain = idDomain;
    }

    public String getNameDomain() {
        return nameDomain;
    }

    public void setNameDomain(String nameDomain) {
        this.nameDomain = nameDomain;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "idDomain=" + idDomain +
                ", nameDomain='" + nameDomain + '\'' +
                '}';
    }
}
