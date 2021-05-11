package com.example.todolist.dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.todolist.TaskListener;
import com.example.todolist.R;
import com.example.todolist.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTaskDialog extends DialogFragment {

    private TextView taskName;
    private Button dateButton;
    private TextView taskNotify;
    private Button editButton;
    private TaskListener taskListener;
    private Task task;
    private long date;

    public EditTaskDialog(TaskListener taskListener, Task task) {
        this.taskListener = taskListener;
        this.task = task;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.on_edit_task_dialog, container, false);
        taskName = (TextView)v.findViewById(R.id.edit_task_name);
        taskName.setText(task.getTask());
        taskNotify = (TextView)v.findViewById(R.id.edit_task_notify);
        if (task.getNotify() != 0)
            taskNotify.setText(Integer.toString(task.getNotify()));

        date = task.getDate();
        dateButton = (Button)v.findViewById(R.id.edit_date_button);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dateAndTime = Calendar.getInstance();
                dateAndTime.setTimeInMillis(date);
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

        editButton = (Button)v.findViewById(R.id.edit_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setTask(taskName.getText().toString());
                task.setDate(date);
                task.setNotify(Integer.parseInt(taskNotify.getText().toString()));
                taskListener.updateTask(task);
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
