package com.job.softclick_mobile.models;

public class Etat {
    private Integer idEtat;
    private String nameEtat;
    private Integer nbrJourRetard;

    public Etat(Integer idEtat, String nameEtat, Integer nbrJourRetard) {
        this.idEtat = idEtat;
        this.nameEtat = nameEtat;
        this.nbrJourRetard = nbrJourRetard;
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
