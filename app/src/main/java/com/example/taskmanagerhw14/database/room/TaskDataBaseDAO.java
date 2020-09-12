package com.example.taskmanagerhw14.database.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.model.Task;
import com.example.taskmanagerhw14.model.User;

import java.util.List;

@Dao
public interface TaskDataBaseDAO {
    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM taskTable")
    List<Task> getTasks();

    @Query("SELECT * FROM taskTable WHERE id=:id")
    Task getTask(Long id);

    @Query("SELECT * FROM taskTable WHERE userId=:userId")
    List<Task> getUserTasks(Long userId);

    @Query("SELECT * FROM taskTable WHERE state =:state AND userId=:userId")
    List<Task> getUserTasksByState(TaskState state, Long userId);

    @Query("DELETE FROM taskTable")
    void deleteTasks();

}
