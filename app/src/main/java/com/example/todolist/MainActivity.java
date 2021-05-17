package com.example.todolist;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.todolist.dialog.AddTaskDialog;
import com.example.todolist.dialog.ClickTaskDialog;
import com.example.todolist.notification_service.AlarmReceiver;
import com.example.todolist.task.Task;
import com.example.todolist.task_adapter.TaskAdapter;
import com.example.todolist.task_database.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskListener {

    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton button;
    private TextView emptyTasks;

    private Date date;
    private DateFormat dateFormat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyTasks = (TextView) findViewById(R.id.empty_tasks);

        taskAdapter =  new TaskAdapter(this, emptyTasks);

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
                addTaskDialog(v);
            }
        });

        // Notifications
        createNotificationChannel();
        setUpNotifications();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "TaskReminderChannel";
            String description = "Channel for Task Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyTask", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setUpNotifications() {
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000 ,pendingIntent);
    }

    private void addTaskDialog(View v) {
        AddTaskDialog addTaskDialog = new AddTaskDialog(this);
        addTaskDialog.show(getSupportFragmentManager(), "Add dialog");
    }

    @Override
    public void taskClick(int position) {
        ClickTaskDialog clickTaskDialog = new ClickTaskDialog(this, taskViewModel.getAllTasks().getValue().get(position));
        clickTaskDialog.show(getSupportFragmentManager(), "Click task dialog");
    }

    @Override
    public void completeClick(int position) {
        updateTask(taskViewModel.getAllTasks().getValue().get(position));
    }

    @Override
    public void addTask(Task task) {
        Log.i("Added task", task.toString());
        taskViewModel.insert(task);
    }

    @Override
    public void deleteTask(Task task) {
        Log.i("Deleted task", task.toString());
        taskViewModel.delete(task);
    }

    @Override
    public void updateTask(Task task) {
        Log.i("Updated task", task.toString());
        taskViewModel.update(task);
    }

}