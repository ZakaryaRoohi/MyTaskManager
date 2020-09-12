package com.example.taskmanagerhw14.database.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.taskmanagerhw14.model.User;

@Dao
public interface TaskDataBaseDAO {
    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

}
