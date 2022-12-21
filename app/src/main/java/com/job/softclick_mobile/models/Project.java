package com.job.softclick_mobile.models;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable {
    private Integer idProject,imageProject , revenue;
    private String nameProject , descriptionProject , domain ;
    private Date dateDebut , dateFin;
    private Employee chefProject;
    private Status projectStatus;
    private Priority projectPriority;

    public Project(Integer idProject, Integer imageProject, Integer revenue, String nameProject, String descriptionProject, String domain, Date dateDebut, Date dateFin, Employee chefProject, Status projectStatus, Priority projectPriority) {
        this.idProject = idProject;
        this.imageProject = imageProject;
        this.revenue = revenue;
        this.nameProject = nameProject;
        this.descriptionProject = descriptionProject;
        this.domain = domain;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chefProject = chefProject;
        this.projectStatus = projectStatus;
        this.projectPriority = projectPriority;
    }

    public Project(String nameProject , String descriptionProject , Integer revenue) {
        this.nameProject = nameProject;
        this.descriptionProject = descriptionProject;
        this.revenue = revenue;

    }


    public Integer getIdProject() {
        return idProject;
    }


    public Integer getImageProject() {
        return imageProject;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public void setImageProject(Integer imageProject) {
        this.imageProject = imageProject;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getDescriptionProject() {
        return descriptionProject;
    }

    public void setDescriptionProject(String descriptionProject) {
        this.descriptionProject = descriptionProject;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Employee getChefProject() {
        return chefProject;
    }

    public void setChefProject(Employee chefProject) {
        this.chefProject = chefProject;
    }

    public Status getProjectEtat() {
        return projectStatus;
    }

    public void setProjectEtat(Status projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Priority getProjectPriority() {
        return projectPriority;
    }

    public void setProjectPriority(Priority projectPriority) {
        this.projectPriority = projectPriority;
    }
}
