package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.todolist.task.Task;
import com.example.todolist.task.TaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    private ArrayList<Task> tasksList;
    private TaskAdapter tasksAdapter;
    private RecyclerView tasksView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTaskView();

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask(v);
            }
        });
    }

    private void initTaskView(){
        tasksView = findViewById(R.id.task_view);
        tasksView.setLayoutManager(new LinearLayoutManager(this));
        tasksList = new ArrayList<>();
        tasksAdapter =  new TaskAdapter(tasksList, this);
        tasksView.setAdapter(tasksAdapter);
    }

    private void addTask(View v) {
        EditText input = findViewById(R.id.editText1);
        String text = input.getText().toString();
        Task task = new Task(text);
        tasksList.add(task);

        if(!text.equals("")) {
            tasksAdapter.setItems(tasksList);
            input.setText("");
        }

    }

    private void removeTask(int position){
        tasksList.remove(position);
        tasksAdapter.setItems(tasksList);
        Toast.makeText(this, "Task removed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskClick(int position) {
        removeTask(position);
    }
}