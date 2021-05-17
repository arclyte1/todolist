package com.example.todolist.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.todolist.TaskListener;
import com.example.todolist.R;
import com.example.todolist.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTaskDialog extends DialogFragment {

    private Button editButton;
    private TextView taskName;
    private Button dateButton;
    private Button notifyButton;
    private TaskListener taskListener;
    private Task task;
    private long date;
    private long notify;

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
        taskName = v.findViewById(R.id.edit_task_name);
        taskName.setText(task.getTask());
        dateButton = v.findViewById(R.id.edit_date_button);
        notifyButton = v.findViewById(R.id.edit_notify_date_button);
        // Date Picker for date
        date = task.getDate();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dateAndTime = Calendar.getInstance();
                long savedDate = date;
                if (date != 0)
                    dateAndTime.setTimeInMillis(date);

                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String mDate = year + String.format("%02d", month + 1) + String.format("%02d", dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                        try {
                            date = simpleDateFormat.parse(mDate).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date += hourOfDay * 60 * 60 * 1000 + minute * 60 * 1000;
                                if (date < Calendar.getInstance().getTimeInMillis() && date != 0) {
                                    Toast.makeText(getContext(), "Введена некорректная дата", Toast.LENGTH_LONG).show();
                                    date = savedDate;
                                }
                            }
                        },
                                dateAndTime.get(Calendar.HOUR),
                                dateAndTime.get(Calendar.MINUTE),
                                true
                        ).show();
                    }
                },
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        // Date Picker for notify
        notify = task.getNotify();
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dateAndTime = Calendar.getInstance();
                long savedNotify = notify;
                if (notify != 0)
                    dateAndTime.setTimeInMillis(notify);

                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String mDate = year + String.format("%02d", month + 1) + String.format("%02d", dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                        try {
                            notify = simpleDateFormat.parse(mDate).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                notify += hourOfDay * 60 * 60 * 1000 + minute * 60 * 1000;
                                if (notify < Calendar.getInstance().getTimeInMillis() && notify != 0) {
                                    Toast.makeText(getContext(), "Введена некорректная дата", Toast.LENGTH_LONG).show();
                                    notify = savedNotify;
                                }
                            }
                        },
                                dateAndTime.get(Calendar.HOUR),
                                dateAndTime.get(Calendar.MINUTE),
                                true
                        ).show();
                    }
                },
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();

            }
        });


        // Add Task
        editButton = (Button)v.findViewById(R.id.edit_add_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = taskName.getText().toString();
                if (!name.isEmpty()) {
                    if (name.length() > 30)
                        Toast.makeText(getContext(), "Имя задачи не больше 30 символов", Toast.LENGTH_LONG).show();
                    else {
                        task.setTask(name);
                        task.setDate(date);
                        task.setNotify(notify);
                        taskListener.updateTask(task);
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
