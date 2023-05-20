package com.example.kanplan.Models;

import java.util.ArrayList;
import java.util.List;

public class Project {

    public enum Complexity {
        VERY_COMPLEX,
        COMPLEX,
        REGULAR,
        EASY
    }

    public enum Size {
        VERY_BIG,
        BIG,
        REGULAR,
        SMALL
    }

    public enum Emergency {
        ASAP,
        HIGH,
        MEDIUM,
        LOW
    }

    private String projectName;

    private User projectManager;

    private String description ;

    private List<String> team;



    private Complexity complexity;

    private Size size;

    private Emergency emergency;

    private ArrayList<Task> tasks;

    public Project(String projectName, User projectManager, String description, List<String> team, Complexity complexity, Size size, Emergency emergency) {
        this.projectName = projectName;
        this.projectManager = projectManager;
        this.description = description;
        this.team = team;
        this.complexity = complexity;
        this.size = size;
        this.emergency = emergency;
    }
    public Project(){}

    public String getComplexityString() {
        if(this.complexity ==Complexity.COMPLEX)
            return "Complex";
        if(this.complexity == Complexity.VERY_COMPLEX)
            return "Very Complex";
        if(this.complexity == Complexity.REGULAR)
            return "Regular";
        return "Easy";
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }

    public String getSizeString() {

        if(this.size ==Size.VERY_BIG)
            return "Very Big";
        if(this.size == Size.BIG)
            return "Big";
        if(this.size == Size.REGULAR)
            return "Regular";
        return "Small";
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getEmergencyString() {

        if(this.emergency ==Emergency.ASAP)
            return "ASAP";
        if(this.emergency == Emergency.HIGH)
            return "High";
        if(this.emergency == Emergency.MEDIUM)
            return "Medium";
        return "Low";
    }

    public void setEmergency(Emergency emergency) {
        this.emergency = emergency;
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

    public List<String> getTeam() {
        return team;
    }

    public void setTeam(List<String> team) {
        this.team = team;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public Complexity getComplexity() {
        return complexity;
    }

    public Size getSize() {
        return size;
    }

    public Emergency getEmergency() {
        return emergency;
    }





}
