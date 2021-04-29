package com.example.todolist.task;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;

class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<ArrayList<Task>> mAllTasks;

    TaskRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mAllTasks = mTaskDao.getTasks();
    }

    LiveData<ArrayList<Task>> getAllTasks() {
        return mAllTasks;
    }

    void insert(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insert(task);
        });
    }
}
