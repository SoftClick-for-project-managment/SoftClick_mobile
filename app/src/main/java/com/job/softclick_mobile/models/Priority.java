package com.job.softclick_mobile.models;

public class Priority {
    private Integer idPriority;
    private float dugreePriority;
    private String namePriority;

    public Priority(Integer idPriority, float dugreePriority, String namePriority) {
        this.idPriority = idPriority;
        this.dugreePriority = dugreePriority;
        this.namePriority = namePriority;
    }

    public Integer getIdPriority() {
        return idPriority;
    }



    public float getDugreePriority() {
        return dugreePriority;
    }

    public void setDugreePriority(float dugreePriority) {
        this.dugreePriority = dugreePriority;
    }

    public String getNamePriority() {
        return namePriority;
    }

    public void setNamePriority(String namePriority) {
        this.namePriority = namePriority;
    }
}
