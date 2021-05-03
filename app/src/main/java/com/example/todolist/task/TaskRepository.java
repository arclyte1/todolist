package com.example.todolist.task;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;

    TaskRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mAllTasks = mTaskDao.getTasks();
    }

    LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    void insert(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insert(task);
        });
    }

    void delete(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.delete(task);
        });
    }

    void update(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.update(task);
        });
    }

}
