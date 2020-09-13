package com.example.taskmanagerhw14.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.Utils.UserType;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "UserTable")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "userName")
    private String mUserName;

    @ColumnInfo(name = "passWord")
    private String mPassword;

    @ColumnInfo(name = "dateCreated")
    private Date mDateCreated;

    @ColumnInfo(name = "userType")
    private UserType mUserType;

    public User(String userName, String password, UserType userType) {
        mUserName = userName;
        mPassword = password;
        mUserType = userType;
        mDateCreated = new Date();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public Date getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        mDateCreated = dateCreated;
    }

    public UserType getUserType() {
        return mUserType;
    }

    public void setUserType(UserType userType) {
        mUserType = userType;
    }

    public static class DateConverter {

        @TypeConverter
        public Long fromDate(Date value) {
            return value.getTime();
        }

        @TypeConverter
        public Date fromLong(Long value) {
            return new Date(value);
        }
    }
    public static class UserTypeConverter {

        @TypeConverter
        public String convertUserTypeToString(UserType value) {
            return value.toString();
        }

        @TypeConverter
        public UserType formString(String value) {
            switch (value) {
                case "ADMIN":
                    return UserType.ADMIN;
                case "USER":
                    return UserType.USER;
            }
            return null;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mDateCreated=" + mDateCreated +
                ", mUserType=" + mUserType +
                '}';
    }
    public String getPhotoFileName() {
        return "IMG_PROFILE_" + getUserName() + ".jpg";
    }
//
//    private String username;
//    private String password;
//    private UserType userType;
//    private int numberOfTasks = 0;
//
//
//    private Date userDateCreated;
//
//    public User(String username, String password, UserType userType) {
//        this.username = username;
//        this.password = password;
//        this.userType = userType;
//        this.userDateCreated = new Date();
//
//    }
//
//    public int getNumberOfTasks() {
//        return numberOfTasks;
//    }
//
//    public void setNumberOfTasks(int numberOfTasks) {
//        this.numberOfTasks = numberOfTasks;
//    }
//
//    public User(){
//        this.userDateCreated = new Date();
//
//    }
//    public Date getUserDateCreated() {
//        return userDateCreated;
//    }
//
//    public void setUserDateCreated(Date mUserDateCreated) {
//        this.userDateCreated = mUserDateCreated;
//    }
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public UserType getUserType() {
//        return userType;
//    }
//
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }
}
