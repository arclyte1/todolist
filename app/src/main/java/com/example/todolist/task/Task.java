package com.example.todolist.task;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "task")
    private String task;

    @NonNull
    @ColumnInfo(name = "isCompleted")
    private boolean isCompleted;

    public Task(@NonNull String task) {
        this.task = task;
        this.isCompleted = false;
    }

    public void setTask(@NonNull String task) {
        this.task = task;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public @NonNull String getTask() {
        return task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
