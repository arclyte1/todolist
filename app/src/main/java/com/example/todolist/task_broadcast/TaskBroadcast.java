package com.example.todolist.task_broadcast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.SyncStateContract;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.todolist.MainActivity;
import com.example.todolist.R;
import com.example.todolist.TaskListener;
import com.example.todolist.task.Task;

import java.util.List;

public class TaskBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyTask")
                .setSmallIcon(R.drawable.ic_stat_expand_less)
                .setContentTitle("ToDo List")
                .setContentText("Задачи ждут выполнения")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());

    }
}
