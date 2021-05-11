package com.example.todolist.task_database;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist.task.Task;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;

    private final LiveData<List<Task>> mAllTasks;

    public TaskViewModel (Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTasks = mRepository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() { return mAllTasks; }

    public void insert(Task task) {
        mRepository.insert(task);
    }

    public void delete(Task task) {
        mRepository.delete(task);
    }

    public void update(Task task) {
        mRepository.update(task);
    }
}