package com.example.taskmanagerhw14.Repository;

import com.example.taskmanagerhw14.Utils.TaskState;

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
}
