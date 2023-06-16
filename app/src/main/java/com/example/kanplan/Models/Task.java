package com.example.kanplan.Models;

import java.util.ArrayList;
import java.util.List;
import com.example.kanplan.Data.DataManager.Emergency;
import com.example.kanplan.Data.DataManager.Size;
import com.example.kanplan.Data.DataManager.Complexity;
import com.example.kanplan.Data.DataManager.Status;
public class Task {



    private String taskName;

    private String taskDescription;

    private List<String> assigned;

    private Complexity complexity;

    private Size size;

    private Emergency emergency;

    private Status status;
    private String projectID;

    private String taskID;


    private List<Comment> comments;



    public Task(String taskName, String taskDescription, List<String> assigned, Complexity complexity, Size size, Emergency emergency, Status status, String projectID) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.assigned = assigned;
        this.complexity = complexity;
        this.size = size;
        this.emergency = emergency;
        this.status = status;
        this.projectID = projectID;
        Comment comment= new Comment("initial","initial","initial");
        comments = new ArrayList<>();
        comments.add(comment);
    }
    public Task(){}

    public Size getSize() {
        return size;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
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

    public List<String> getAssigned() {
        return assigned;
    }

    public void setAssigned(List<String> assigned) {
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

    public void setTaskSize(Size size){this.size=size;}

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
        if(this.complexity == Complexity.COMPLEX)
            return "Complex";
        if(this.complexity == Complexity.VERY_COMPLEX)
            return "Very Complex";
        if(this.complexity == Complexity.REGULAR)
            return "Regular";
        return "Easy";
    }
    public String getSizeString() {

        if(this.size == Size.VERY_BIG)
            return "Very Big";
        if(this.size == Size.BIG)
            return "Big";
        if(this.size == Size.REGULAR)
            return "Regular";
        return "Small";
    }

    public String getEmergencyString() {

        if(this.emergency == Emergency.ASAP)
            return "ASAP";
        if(this.emergency == Emergency.HIGH)
            return "High";
        if(this.emergency == Emergency.MEDIUM)
            return "Medium";
        return "Low";
    }
    public Status getStatus(){return this.status;}
    public String getStatusString(){
        if(this.status == Status.BACKLOG)
            return "Backlog";
        if(this.status == Status.DOING)
            return "Doing";
        if(this.status == Status.DONE)
            return "Done";
        else
            return "";

    }
    public void setStatus(Status status){this.status=status;}
    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }








}
