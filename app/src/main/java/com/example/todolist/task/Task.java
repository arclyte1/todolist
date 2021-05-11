package com.example.todolist.task;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

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

    @NonNull
    @ColumnInfo
    private long date;

    @NonNull
    @ColumnInfo
    private int notify;

    public Task(@NonNull String task, @NonNull long date, @NonNull int notify) {
        this.task = task;
        this.date = date;
        this.notify = notify;
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

    @NonNull
    public long getDate() {
        return date;
    }

    public void setDate(@NonNull long date) {
        this.date = date;
    }

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", isCompleted=" + isCompleted +
                ", date=" + date +
                ", notify=" + notify +
                '}';
    }
}
