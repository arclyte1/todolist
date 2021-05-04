package com.example.todolist.task;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task implements Comparable<Task>{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "task")
    private String task;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Task o) {
        if (this.isCompleted() && !o.isCompleted())
            return 1;
        else if (!this.isCompleted() && o.isCompleted())
            return -1;
        else
            return 0;
    }
}
