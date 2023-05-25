package com.example.kanplan.Models;

public class Comment {


    private String name;
    private String commentText;

    public Comment(){}
    public Comment(String name,String commentText){
        this.name =name;
        this.commentText=commentText;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

}
