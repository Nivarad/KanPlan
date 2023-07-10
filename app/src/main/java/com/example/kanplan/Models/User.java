package com.example.kanplan.Models;

public class User {

    private String firstname;
    private String lastname;
    private String email;


    private String userID;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }





    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName(){
        return firstname+" "+lastname;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public User(String firstname,String lastname,String email,String userID){
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.userID=userID;
    }

    public User(String firstname,String lastname,String email){
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;

    }
    public User(){}
}
