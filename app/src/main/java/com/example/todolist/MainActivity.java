package com.example.todolist;

import android.os.Bundle;

import com.example.todolist.dialog.OnAddTaskDialog;
import com.example.todolist.dialog.OnClickTaskDialog;
import com.example.todolist.task.Task;
import com.example.todolist.task_adapter.TaskAdapter;
import com.example.todolist.task_database.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnTaskListener {

    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton button;



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
        OnAddTaskDialog onAddTaskDialog = new OnAddTaskDialog(this);
        onAddTaskDialog.show(getSupportFragmentManager(), "Add dialog");
    }

    public void updateTask(Task task) {
        taskViewModel.update(task);
    }

    @Override
    public void onTaskClick(int position) {
        OnClickTaskDialog onClickTaskDialog = new OnClickTaskDialog(this, taskViewModel.getAllTasks().getValue().get(position));
        onClickTaskDialog.show(getSupportFragmentManager(), "Click task dialog");
    }

    @Override
    public void onCompleteClick(int position) {
        updateTask(taskViewModel.getAllTasks().getValue().get(position));
    }

    @Override
    public void onAddTask(Task task) {
        taskViewModel.insert(task);
    }

    @Override
    public void onDeleteTask(Task task) {
        taskViewModel.delete(task);
        Toast.makeText(this, "Task removed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateTask(Task task) {
        updateTask(task);
    }
}