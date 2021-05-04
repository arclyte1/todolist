package com.example.todolist;

import com.example.todolist.task.Task;

public interface OnTaskListener {
    void onTaskClick(int position);
    void onCompleteClick(int position);
    void onAddTask(Task task);
    void onDeleteTask(Task task);
    void onUpdateTask(Task task);
}
