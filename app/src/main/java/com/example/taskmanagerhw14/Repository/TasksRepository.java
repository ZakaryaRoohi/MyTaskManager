package com.example.taskmanagerhw14.Repository;


import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.model.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TasksRepository implements Serializable {
    private static TasksRepository sTasksRepository;
    private List<Task> mTasks;
    public static int mNumberOfTasks;

    public static TasksRepository getInstance() {
        if (sTasksRepository == null)
            sTasksRepository = new TasksRepository();
        return sTasksRepository;
    }

    private TasksRepository() {

        mTasks = new ArrayList<>();
        for (int i = 0; i < mNumberOfTasks; i++) {
            Task task = new Task();
//            task.setTaskTitle("Task : " + (i + 1));
//            task.setTaskState(randomTaskState());
            mTasks.add(task);
        }
    }

    public List<Task> getList() {
        return mTasks;
    }

    private TaskState randomTaskState() {
        Random random = new Random();
        int rand = random.nextInt(3);
        switch (rand) {
            case 0:
                return TaskState.DONE;
            case 1:
                return TaskState.DOING;
            case 2:
                return TaskState.TODO;
        }
        return null;
    }

    public List<Task> getList(TaskState taskState) {
        List<Task> taskList = new ArrayList<>();
        for (Task task : mTasks) {
            if (task.getTaskState() == taskState)
                taskList.add(task);
        }
        return taskList;
    }

    public List<Task> getList(TaskState taskState, String username) {
        List<Task> taskList = new ArrayList<>();
        for (Task task : mTasks) {
            if (task.getTaskState() == taskState && task.getUsername().equals(username))
                taskList.add(task);
        }
        return taskList;
    }

    public void addTask() {
        Task task = new Task();
        task.setTaskTitle("Task : " + (sTasksRepository.getList().size() + 1));
//        task.setTaskState(randomTaskState());
        mTasks.add(task);
    }

    public void addTask(Task task) {
//        task.setTaskTitle("Task : " + (sTasksRepository.getList().size()+1));
//        task.setTaskDescription("new");
        mTasks.add(task);
    }

    public int getIndexOfTask(Task task) {
        return mTasks.indexOf(task);
    }

    public void clearTaskRepository() {
        sTasksRepository = null;
    }

    public void deleteUserTask(String username){
        for (Task task:mTasks) {
            if (task.getUsername().equals(username))
                mTasks.remove(task);
        }
    }
    public void delete(UUID taskId){
        for (Task task:mTasks) {
            if (task.getId().equals(taskId))
                mTasks.remove(task);
        }
    }
    public Task get(UUID uuid) {
        for (Task task : mTasks) {
            if (task.getId().equals(uuid))
                return task;
        }
        return null;
    }

    public void update(Task task) {
        Task updateTask = get(task.getId());
        updateTask.setTaskTitle(task.getTaskTitle());
        updateTask.setTaskDescription(task.getTaskDescription());
        updateTask.setTaskState(task.getTaskState());
        updateTask.setTaskDate(task.getTaskDate());

    }
    public boolean checkTaskExists(Task searchTask){
        for (Task task : mTasks) {
            if (task.equals(searchTask))
                return  false;
        }
        return  true;
    }
}
