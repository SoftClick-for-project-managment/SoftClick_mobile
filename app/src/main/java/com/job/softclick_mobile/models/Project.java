package com.job.softclick_mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Project implements Serializable {
    private Long idProject;
    private String nameProject;


    private String descriptionProject;

    private Double revenueProject;


    private Domain domainProjet;


    private Timestamp dateDebut;


    private Timestamp dateFin;


    private Employee chefProject;


    private Status projectStatus;


    private Priority projectPriority;


    private Set<Invoice> invoices = new HashSet<>();


    private Set<Task> tasks = new HashSet<>();

    private Set<Team> teams = new HashSet<>();


    public Project( String nameProject, String descriptionProject, Double revenueProject, Domain domainProjet, Timestamp dateDebut, Timestamp dateFin, Employee chefProject, Status projectStatus, Priority projectPriority) {

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


    public Project(String nameProject, Domain domainProjet, Employee chefProject, Status projectStatus, Priority projectPriority) {
        this.nameProject = nameProject;
        this.domainProjet = domainProjet;
        this.chefProject = chefProject;
        this.projectStatus = projectStatus;
        this.projectPriority = projectPriority;
    }

    public Project() {
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

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
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
                "idProject=" + idProject +
                ", nameProject='" + nameProject + '\'' +
                ", descriptionProject='" + descriptionProject + '\'' +
                ", revenueProject=" + revenueProject +
                ", domainProjet=" + domainProjet +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", chefProject=" + chefProject +
                ", projectStatus=" + projectStatus +
                ", projectPriority=" + projectPriority +
                ", invoices=" + invoices.toString() +
                ", tasks=" + tasks.toString() +
                '}';
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
    public void addTeam(Team team){
        this.teams.add(team);
    }
}
