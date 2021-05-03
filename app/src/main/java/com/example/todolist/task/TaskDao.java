package com.example.todolist.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getTasks();
}
