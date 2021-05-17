package com.example.todolist.notification_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.RoomDatabase;

import com.example.todolist.R;
import com.example.todolist.task.Task;
import com.example.todolist.task_database.TaskRoomDatabase;
import com.example.todolist.task_database.TaskViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class NotificationService extends Service {
    private static final String TAG = "NotificationService";

    private List<Task> allTasks = new ArrayList<>();

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TaskViewModel taskViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(TaskViewModel.class);
        taskViewModel.getAllTasks().observeForever(tasks -> {allTasks = tasks;});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent _intent, int flags, int startId) {

        long time = Calendar.getInstance().getTimeInMillis();

        // Parse all tasks
        for (Task task : allTasks){
            if (task.getNotify() != 0 && !task.isNotified() && time > task.getNotify()) {
                task.setNotified(true);
                Log.i("Notify Service", "Task notified: " + task.toString());
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notifyTask")
                        .setSmallIcon(R.drawable.ic_stat_expand_less)
                        .setContentTitle("ToDoList")
                        .setContentText("Задача " + task.getTask() + " ждет выполнения")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                notificationManager.notify(200, builder.build());
            }
        }
        return super.onStartCommand(_intent, flags, startId);
    }
}
