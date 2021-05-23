package com.example.todolist;

import androidx.lifecycle.LiveData;

import com.example.todolist.task.Task;

import java.util.List;

public interface TaskListener {
    void taskClick(int position);
    void addTask(Task task);
    void deleteTask(Task task);
    void updateTask(Task task);
}
