package com.job.softclick_mobile.models;

import java.util.List;

public class Project_section {
    String priority ;
    List<String> projects;

    public Project_section() {
    }

    public Project_section(String priority, List<String> projects) {
        this.priority = priority;
        this.projects = projects;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }
}

