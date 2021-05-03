package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.todolist.task.Task;
import com.example.todolist.task.TaskAdapter;
import com.example.todolist.task.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MotionEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskListener {

    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskAdapter =  new TaskAdapter(this);

        // Init task recycler view
        recyclerView = findViewById(R.id.task_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Init taskViewModel
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> {
            taskAdapter.setItems(tasks);
        });

        // Init add button
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask(v);
            }
        });
    }

    private void addTask(View v) {
        EditText input = findViewById(R.id.editText1);
        String text = input.getText().toString();
        Task task = new Task(text);

        if(!text.equals("")) {
            taskViewModel.insert(task);
            input.setText("");
        }

    }

    private void removeTask(Task task) {
        taskViewModel.delete(task);
        Toast.makeText(this, "Task removed", Toast.LENGTH_LONG).show();
    }

    public void updateTask(Task task) {
        taskViewModel.update(task);
    }

    @Override
    public void onTaskClick(int position) {
        removeTask(taskViewModel.getAllTasks().getValue().get(position));
    }

    @Override
    public void onCompleteClick(int position) {
        updateTask(taskViewModel.getAllTasks().getValue().get(position));
    }

}