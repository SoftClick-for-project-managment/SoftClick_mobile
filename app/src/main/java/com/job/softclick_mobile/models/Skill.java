package com.job.softclick_mobile.models;

import java.io.Serializable;
import java.util.Set;

public class Skill implements Serializable {

    private Long id;
    private String skillName;

    private Set<Employee> employees;

    public Long getId() {
        return id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Skill(Long id, String skillName) {
        this.id = id;
        this.skillName = skillName;
    }

    public Skill() {
    }

    public Skill(String skillName) {
        this.skillName = skillName;
    }
}
