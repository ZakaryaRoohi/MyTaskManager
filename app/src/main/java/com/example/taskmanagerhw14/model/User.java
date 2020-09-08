package com.example.taskmanagerhw14.model;

import com.example.taskmanagerhw14.Utils.UserType;

import java.util.Date;

public class User {
    private String username;
    private String password;
    private UserType userType;
    private int numberOfTasks = 0;


    private Date userDateCreated;

    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.userDateCreated = new Date();

    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setNumberOfTasks(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    public User(){
        this.userDateCreated = new Date();

    }
    public Date getUserDateCreated() {
        return userDateCreated;
    }

    public void setUserDateCreated(Date mUserDateCreated) {
        this.userDateCreated = mUserDateCreated;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
