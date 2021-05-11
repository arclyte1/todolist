package com.example.todolist.dialog;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.todolist.MainActivity;
import com.example.todolist.TaskListener;
import com.example.todolist.R;
import com.example.todolist.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTaskDialog extends DialogFragment{

    private Button addButton;
    private TextView taskName;
    private Button dateButton;
    private TextView taskNotify;
    private TaskListener taskListener;
    private long date;

    public AddTaskDialog(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_task_dialog, container, false);
        taskName = v.findViewById(R.id.task_name);
        dateButton = v.findViewById(R.id.date_button);
        taskNotify = v.findViewById(R.id.task_notify);
        // Date Picker
        date = 0;
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dateAndTime = Calendar.getInstance();
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String mDate = year + String.format("%02d", month + 1) + String.format("%02d", dayOfMonth);
                        Log.i("Selected Date",year + "/" + (month + 1) + "/" + dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                        try {
                            date = simpleDateFormat.parse(mDate).getTime();
                            Log.i("Selected Date (Millis)", Long.toString(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        // Add Task
        addButton = (Button)v.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = taskName.getText().toString();
                String notify = taskNotify.getText().toString();
                if (!name.isEmpty()) {
                    if (name.length() > 20)
                        Toast.makeText(getContext(), "Имя задачи не больше 20 символов", Toast.LENGTH_LONG).show();
                    else {
                        int notifyInt = 0;
                        if (!notify.isEmpty())
                            notifyInt = Integer.parseInt(notify);
                        Task task = new Task(name, date, notifyInt);
                        taskListener.addTask(task);
                        onDestroyView();
                    }
                } else
                    onDestroyView();
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
