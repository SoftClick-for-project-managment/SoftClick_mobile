package com.job.softclick_mobile2.models;

public class TeamDatails {
    String Description;
    Member[] Members;

    public TeamDatails(String description, Member[] members) {
        Description = description;
        Members = members;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Member[] getMembers() {
        return Members;
    }

    public void setMembers(Member[] members) {
        Members = members;
    }
}
