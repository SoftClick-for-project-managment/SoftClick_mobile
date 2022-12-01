package com.job.softclick_mobile.models;

import java.io.Serializable;

public class Team implements Serializable {
    public String TeamName;
    public int id, TeamImage;

    public Team() {
    }



    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public int getTeamImage() {
        return TeamImage;
    }

    public void setTeamImage(int teamImage) {
        TeamImage = teamImage;
    }

    public Team(String teamName, int teamImage) {
        TeamName = teamName;
        TeamImage = teamImage;

    }

}
