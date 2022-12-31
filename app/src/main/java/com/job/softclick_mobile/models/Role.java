package com.job.softclick_mobile.models;

public class Role {
    private Long id;
    private String name;
    public static final String ROLE_DIRECTOR = "ROLE_DIRECTOR";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    public static final String ROLE_PROJECT_MANAGER = "ROLE_PROJECT_MANAGER";
    public static final String ROLE_TEAM_MANAGER = "ROLE_TEAM_MANAGER";

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
