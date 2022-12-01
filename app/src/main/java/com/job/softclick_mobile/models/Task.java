package com.job.softclick_mobile.models;

import java.io.Serializable;

public class Task implements Serializable {
    private String taskname;
    private String taskstatus;
    private  String dateStart;
    private String dateEnd ;
    private String Description ;

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public void setTaskstatus(String taskstatus) {
        this.taskstatus = taskstatus;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTaskname() {
        return taskname;
    }

    public String getTaskstatus() {
        return taskstatus;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getDescription() {
        return Description;
    }

    public Task(String taskname, String taskstatus, String dateStart, String dateEnd, String description) {
        this.taskname = taskname;
        this.taskstatus = taskstatus;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.Description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskname='" + taskname + '\'' +
                ", taskstatus='" + taskstatus + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

}
