package com.example.kanplan.Models;

import java.util.ArrayList;
import java.util.Date;

public class Task {

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

    private String taskName;

    private String taskDescription;

    private Date startDate;

    private Date endDate;

    private ArrayList<String> comments;

    private ArrayList<User> assigned;

    private Complexity complexity;

    private Size size;

    private Emergency emergency;
    private String projectID;

    public Size getSize() {
        return size;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public Task(String taskName, String taskDescription, Date startDate, Date endDate, ArrayList<String> comments, ArrayList<User> assigned, Complexity complexity, Size size, Emergency emergency, String projectID) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comments = comments;
        this.assigned = assigned;
        this.complexity = complexity;
        this.size = size;
        this.emergency = emergency;
        this.projectID = projectID;
    }
    public Task(){}

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public ArrayList<User> getAssigned() {
        return assigned;
    }

    public void setAssigned(ArrayList<User> assigned) {
        this.assigned = assigned;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }

    public Size getTaskSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    public void setEmergency(Emergency emergency) {
        this.emergency = emergency;
    }
    public String getComplexityString() {
        if(this.complexity == Task.Complexity.COMPLEX)
            return "Complex";
        if(this.complexity == Task.Complexity.VERY_COMPLEX)
            return "Very Complex";
        if(this.complexity == Task.Complexity.REGULAR)
            return "Regular";
        return "Easy";
    }
    public String getSizeString() {

        if(this.size == Task.Size.VERY_BIG)
            return "Very Big";
        if(this.size == Task.Size.BIG)
            return "Big";
        if(this.size == Task.Size.REGULAR)
            return "Regular";
        return "Small";
    }

    public String getEmergencyString() {

        if(this.emergency == Task.Emergency.ASAP)
            return "ASAP";
        if(this.emergency == Task.Emergency.HIGH)
            return "High";
        if(this.emergency == Task.Emergency.MEDIUM)
            return "Medium";
        return "Low";
    }






}
