package com.example.taskmanagerhw14.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;

import com.example.taskmanagerhw14.model.Task;
import com.example.taskmanagerhw14.model.User;

@Database(entities ={Task.class, User.class},version =1,exportSchema = false)
//@TypeConverter({Task.UUIDConverter.class,Task.StateConverter.class,User.DateConverter.class,User.RoleConverter.class})
public abstract class TaskManagerDB extends RoomDatabase {

    public abstract TaskDataBaseDAO taskDataBaseDAO();
    public abstract UserDataBaseDAO userDateBaseDAO();
}
