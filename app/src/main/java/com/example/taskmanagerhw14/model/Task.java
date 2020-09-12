package com.example.taskmanagerhw14.model;


import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.taskmanagerhw14.Utils.TaskState;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
@Entity(tableName = "taskTable")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name="uuid")
    private UUID mTaskId;

    @ColumnInfo(name = "userId")
    private Long mUserId;

    @ColumnInfo(name = "title")
    private String mTaskTitle;

    @ColumnInfo(name = "description")
    private String mTaskDescription;

    @ColumnInfo(name = "state")
    private TaskState mTaskState;

    @ColumnInfo(name = "date")
    private Date mTaskDate;

    public Task() {
    }

    public Task(User user) {
        mUserId = user.getId();
        mTaskId = UUID.randomUUID();
        mTaskDate=new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getTaskId() {
        return mTaskId;
    }

    public void setTaskId(UUID taskId) {
        mTaskId = taskId;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        mTaskDescription = taskDescription;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }

    public Date getTaskDate() {
        return mTaskDate;
    }

    public void setTaskDate(Date date) {
        mTaskDate = date;
    }

    public static class UUIDConverter {

        @TypeConverter
        public String fromUUID(UUID value) {
            return value.toString();
        }

        @TypeConverter
        public UUID fromString(String value) {
            return UUID.fromString(value);
        }
    }

    public static class StateConverter {

        @TypeConverter
        public String fromState(TaskState value) {
            return value.toString();
        }

        @TypeConverter
        public TaskState formString(String value) {
            switch (value) {
                case "TODO":
                    return TaskState.TODO;
                case "DOING":
                    return TaskState.DOING;
                case "DONE":
                    return TaskState.DONE;
            }
            return null;
        }
    }


//    private UUID mId;
//    private String mTaskTitle;
//    private TaskState mTaskState;
//    private String mTaskDescription;
//    private Date mTaskDate;
//    private String mUsername;
//
//    public Task(UUID uuid, String title, String description, Date taskDate, String username, TaskState taskState) {
//        this.mId = uuid;
//        this.mTaskTitle = title;
//        this.mTaskState = taskState;
//        this.mTaskDescription = description;
//        this.mTaskDate = taskDate;
//        this.mUsername = username;
//    }
//
//    public UUID getId() {
//        return mId;
//    }
//
//    public String getUsername() {
//        return mUsername;
//    }
//
//    public void setUser(String mUser) {
//        this.mUsername = mUser;
//    }
//
//
//    public String getTaskDescription() {
//        return mTaskDescription;
//    }
//
//    public void setTaskDescription(String mTaskDescription) {
//        this.mTaskDescription = mTaskDescription;
//    }
//
//    public Date getTaskDate() {
//        return mTaskDate;
//    }
//
//    public void setTaskDate(Date mTaskDate) {
//        this.mTaskDate = mTaskDate;
//    }
//
//    public String getTaskTitle() {
//        return mTaskTitle;
//    }
//
//    public void setTaskTitle(String taskName) {
//        mTaskTitle = taskName;
//    }
//
//    public TaskState getTaskState() {
//        return mTaskState;
//    }
//
//    public void setTaskState(TaskState taskState) {
//        mTaskState = taskState;
//    }
//
//    public Task(String mTaskTitle, TaskState mTaskState, String mTaskDescription, Date mTaskDate, String mUsername) {
//        this.mId = UUID.randomUUID();
//        this.mTaskTitle = mTaskTitle;
//        this.mTaskState = mTaskState;
//        this.mTaskDescription = mTaskDescription;
//        this.mTaskDate = mTaskDate;
//        this.mUsername = mUsername;
//    }
//
//    public Task(UUID id) {
//        mId = id;
//        mTaskDate = new Date();
//
//
//    }
//
//    public Task(String username) {
//        this(UUID.randomUUID());
//        this.mUsername = username;
//        mTaskDate = new Date();
//
//    }
//    public Task(){
//        this(UUID.randomUUID());
//
//    }
//
//    @Override
//    public String toString() {
//        return "Task{" +
//                "mId=" + mId +
//                ", mTaskTitle='" + mTaskTitle + '\'' +
//                ", mTaskState=" + mTaskState +
//                ", mTaskDescription='" + mTaskDescription + '\'' +
//                ", mTaskDate=" + mTaskDate +
//                ", mUsername='" + mUsername + '\'' +
//                '}';
//    }
}
