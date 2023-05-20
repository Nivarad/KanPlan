package com.example.kanplan.Models;

import java.util.ArrayList;

public class Project {


    private String projectName;

    private User projectManager;

    private String description ;

    private ArrayList<User> team ;

    public Project() {

    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<User> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<User> team) {
        this.team = team;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    private ArrayList<Task> tasks;



}
