package com.example.kanplan.Models;

import android.os.Build;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment {


    private String commentWriterName;
    private String commentText;
    private String commentTitle;
    private String date;

    public Comment(){}
    public Comment(String commentWriterName,String commentText,String commentTitle){
        this.commentWriterName =commentWriterName;
        this.commentText=commentText;
        this.commentTitle=commentTitle;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDateTime.now().toString().replace("T"," ").substring(0,16);
        }

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
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
