package com.example.taskmanagerhw14.Repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.database.room.TaskManagerDB;
import com.example.taskmanagerhw14.model.Task;

import java.io.File;
import java.util.List;

public class TaskDBRoomRepository {
    private TaskManagerDB mDataBase;
    private static Context sContext;
    private static TaskDBRoomRepository sTaskRepository;

    private TaskDBRoomRepository() {
        mDataBase = Room.databaseBuilder(sContext, TaskManagerDB.class, "TaskDB.db")
                .allowMainThreadQueries().build();
    }

    public static TaskDBRoomRepository getInstance(Context context) {
        sContext = context.getApplicationContext();
        if (sTaskRepository == null)
            sTaskRepository = new TaskDBRoomRepository();
        return sTaskRepository;
    }

    public List<Task> getUserTaskListByState(TaskState taskState, Long userID) {
        return mDataBase.taskDataBaseDAO().getUserTasksByState(taskState, userID);
    }

    public List<Task> getTaskListByState(TaskState taskState) {
        return mDataBase.taskDataBaseDAO().getTasks(taskState);
    }

    public List<Task> getUserTask(Long userId) {
        return mDataBase.taskDataBaseDAO().getUserTasks(userId);
    }


    public Task get(Long id) {
        return mDataBase.taskDataBaseDAO().getTask(id);
    }


    public void insert(Task task) {
        mDataBase.taskDataBaseDAO().insertTask(task);
    }


    public void remove(Task task) {
        mDataBase.taskDataBaseDAO().deleteTask(task);
    }

    public void update(Task task) {
        mDataBase.taskDataBaseDAO().updateTask(task);

    }

    public void removeAllTasks() {
        mDataBase.taskDataBaseDAO().deleteTasks();
    }

    public void removeAllUserTasks(long userId) {
        for (Task task : mDataBase.taskDataBaseDAO().getUserTasks(userId)) {
            remove(task);
        }
    }
    public File getPhotoFile(Context context, Task task) {
        File photoFile = new File(context.getFilesDir(), task.getPhotoFileName());
        return photoFile;
    }

}
