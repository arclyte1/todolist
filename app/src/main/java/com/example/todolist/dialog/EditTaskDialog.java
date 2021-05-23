package com.example.todolist.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTaskDialog extends DialogFragment {

    private MaterialButton editButton;
    private TextView taskName;
    private MaterialButton dateButton;
    private MaterialButton notifyButton;
    private MaterialButton repeatButton;
    private TaskListener taskListener;
    private RepeatTaskDialog repeatTaskDialog;
    private long date;
    private long notify;
    private int repeat;
    private Task task;

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
        View v = inflater.inflate(R.layout.add_task_dialog, container, false);
        taskName = v.findViewById(R.id.task_name);
        taskName.setText(task.getTask());

        // Date Button
        dateButton = v.findViewById(R.id.date_button);
        date = task.getDate();
        if (date != 0)
            dateButton.setIconTintResource(R.color.purple_200);
        dateButton.setOnTouchListener(new View.OnTouchListener() {
            Calendar calendar = Calendar.getInstance();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    calendar = Calendar.getInstance();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis() > 500) {
                        date = 0;
                        dateButton.setIconTintResource(R.color.grey);
                        repeatTaskDialog.setRepeat(0);
                    } else {
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
                                        if (date == 0) {
                                            dateButton.setIconTintResource(R.color.grey);
                                            repeatTaskDialog.setRepeat(0);
                                        }
                                        else
                                            dateButton.setIconTintResource(R.color.purple_200);
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
                }
                return false;
            }
        });

        // Notify Button
        notifyButton = v.findViewById(R.id.notify_date_button);
        notify = task.getNotify();
        if (notify != 0)
            notifyButton.setIconTintResource(R.color.purple_200);
        notifyButton.setOnTouchListener(new View.OnTouchListener() {
            Calendar calendar = Calendar.getInstance();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    calendar = Calendar.getInstance();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis() > 500) {
                        notify = 0;
                        notifyButton.setIconTintResource(R.color.grey);
                    } else {
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

                                        if (notify == 0)
                                            notifyButton.setIconTintResource(R.color.grey);
                                        else
                                            notifyButton.setIconTintResource(R.color.purple_200);
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
                }
                return false;
            }
        });

        // Repeat Button
        repeatButton = v.findViewById(R.id.repeat_button);
        repeatTaskDialog = new RepeatTaskDialog(repeatButton, date);
        repeatTaskDialog.setRepeat(task.getRepeat());
        repeatButton.setOnTouchListener(new View.OnTouchListener() {
            Calendar calendar = Calendar.getInstance();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    calendar = Calendar.getInstance();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis() > 500) {
                        repeatTaskDialog.setRepeat(0);
                    } else {
                        repeat = repeatTaskDialog.getRepeat();
                        repeatTaskDialog = new RepeatTaskDialog(repeatButton, date);
                        repeatTaskDialog.setRepeat(repeat);
                        repeatTaskDialog.show(getFragmentManager(), "Repeat Task Dialog");
                    }
                }
                return false;
            }
        });

        // Add Button
        editButton = v.findViewById(R.id.add_button);
        editButton.setText("Изменить");
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
                        task.setRepeat(repeatTaskDialog.getRepeat());
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