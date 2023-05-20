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

    public enum TaskSize {
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

    private TaskSize taskSize;

    private Emergency emergency;


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

    public TaskSize getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(TaskSize taskSize) {
        this.taskSize = taskSize;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    public void setEmergency(Emergency emergency) {
        this.emergency = emergency;
    }






}
