package com.job.softclick_mobile.models;

import java.io.Serializable;
import java.util.Set;

public class Team implements Serializable {
    private Long idTeam;

    private String team_Name;

    private String description;
    private Set<Employee> members;

    public Team() {
    }

    public Set<Employee> getMembers() {
        return members;
    }

    public void setMembers(Set<Employee> members) {
        this.members = members;
    }

    public Long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Long idTeam) {
        this.idTeam = idTeam;
    }

    public String getTeam_Name() {
        return team_Name;
    }

    public void setTeam_Name(String team_Name) {
        this.team_Name = team_Name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   /* public Team(String teamName, int teamImage) {
        TeamName = teamName;
        TeamImage = teamImage;
    }*/

    public Team(String team_Name, String description) {
        this.team_Name = team_Name;
        this.description = description;

    }



    @Override
    public String toString() {
        return "Team{" +
                "idTeam=" + idTeam +
                ", team_Name='" + team_Name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
