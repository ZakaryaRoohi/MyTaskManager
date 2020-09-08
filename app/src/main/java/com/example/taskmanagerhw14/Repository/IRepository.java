package com.example.taskmanagerhw14.Repository;

import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.model.Task;

import java.util.List;
import java.util.UUID;

public interface IRepository<E> {
    List<E> getList(TaskState taskState);
    List<E> getList();
    E get(UUID uuid);
    void setList(List<E> list);
    void update(E e);
    void delete(E e);
    void insert(E e);
    void insertList(List<E> list);
    int getPosition(UUID uuid);
    void addTask(TaskState e);
    void clearTaskRepository();
    void deleteUserTask(String username);
    void delete(UUID taskId);
    boolean checkTaskExists(Task task);
    List<Task> getList(TaskState taskState, String username);
}
