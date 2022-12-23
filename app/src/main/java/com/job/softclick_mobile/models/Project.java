package com.job.softclick_mobile.models;

import java.io.Serializable;
import java.util.Date;


public class Project implements Serializable {
    private Long idProject;
    private String nameProject;


    private String descriptionProject;

    private Double revenueProject;


    private Domain domainProjet;


    private Date dateDebut;


    private Date dateFin;


    private Employee chefProject;


    private Status projectStatus;


    private Priority projectPriority;


    public Project( String nameProject, String descriptionProject, Double revenueProject, Domain domainProjet, Date dateDebut, Date dateFin, Employee chefProject, Status projectStatus, Priority projectPriority) {

        this.nameProject = nameProject;
        this.descriptionProject = descriptionProject;
        this.revenueProject = revenueProject;
        this.domainProjet = domainProjet;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chefProject = chefProject;
        this.projectStatus = projectStatus;
        this.projectPriority = projectPriority;
    }

    public Project(String nameProject , String descriptionProject , Double revenueProject) {
        this.nameProject = nameProject;
        this.descriptionProject = descriptionProject;
        this.revenueProject = revenueProject;

    }

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
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

    public Double getRevenueProject() {
        return revenueProject;
    }

    public void setRevenueProject(Double revenueProject) {
        this.revenueProject = revenueProject;
    }

    public Domain getDomainProjet() {
        return domainProjet;
    }

    public void setDomainProjet(Domain domainProjet) {
        this.domainProjet = domainProjet;
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

    public Status getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Status projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Priority getProjectPriority() {
        return projectPriority;
    }

    public void setProjectPriority(Priority projectPriority) {
        this.projectPriority = projectPriority;
    }

    @Override
    public String toString() {
        return "Project{" +
                "nameProject='" + nameProject + '\'' +
                ", descriptionProject='" + descriptionProject + '\'' +
                ", revenueProject=" + revenueProject +
                ", domainProjet=" + domainProjet +
                ", dateDebut=" + dateDebut.toString() +
                ", dateFin=" + dateFin +
                ", chefProject=" + chefProject.getEmployeeFirstName() +
                ", projectStatus=" + projectStatus +
                ", projectPriority=" + projectPriority +
                '}';
    }
}
