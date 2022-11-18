package com.job.softclick_mobile.models;

public class Team {
    public String TeamName;
    public int TeamImage;
    public String [] members;

    public Team(String teamName, int teamImage,String [] members) {
        this.TeamName = teamName;
        this.TeamImage = teamImage;
        this.members=members;
    }

}
