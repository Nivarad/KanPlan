package com.example.kanplan.Models;

public class Comment {


    private String commentWriterName;
    private String commentText;
    private String commentTitle;

    public Comment(){}
    public Comment(String commentWriterName,String commentText,String commentTitle){
        this.commentWriterName =commentWriterName;
        this.commentText=commentText;
        this.commentTitle=commentTitle;
    }
    public String getCommentWriterName() {
        return commentWriterName;
    }

    public void setCommentWriterName(String name) {
        this.commentWriterName = name;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

}
