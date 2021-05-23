package com.example.todolist.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.todolist.R;
import com.example.todolist.TaskListener;
import com.example.todolist.task.Task;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RepeatTaskDialog extends DialogFragment {

    private int repeat = 0;
    private long date;
    private MaterialButton repeatButton;
    private MaterialButton everyDayButton;
    private MaterialButton every2DaysButton;
    private MaterialButton everyWeekButton;
    private MaterialButton neverButton;

    public RepeatTaskDialog(MaterialButton repeatButton, long date) {
        this.repeatButton = repeatButton;
        this.date = date;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.repeat_task_dialog, container, false);

        everyDayButton = v.findViewById(R.id.every_day);
        everyDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date != 0) {
                    repeatButton.setIconTintResource(R.color.purple_200);
                    repeat = 1;
                } else {
                    Toast.makeText(getContext(), "Сначала нужно выбрать дату", Toast.LENGTH_LONG).show();
                }
                onDestroyView();
            }
        });

        every2DaysButton = v.findViewById(R.id.in_2_days);
        every2DaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date != 0) {
                    repeatButton.setIconTintResource(R.color.purple_200);
                    repeat = 2;
                } else {
                    Toast.makeText(getContext(), "Сначала нужно выбрать дату", Toast.LENGTH_LONG).show();
                }
                onDestroyView();
            }
        });

        everyWeekButton = v.findViewById(R.id.every_week);
        everyWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date != 0) {
                    repeatButton.setIconTintResource(R.color.purple_200);
                    repeat = 7;
                } else {
                    Toast.makeText(getContext(), "Сначала нужно выбрать дату", Toast.LENGTH_LONG).show();
                }
                onDestroyView();
            }
        });

        neverButton = v.findViewById(R.id.never);
        neverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatButton.setIconTintResource(R.color.grey);
                repeat = 0;
                onDestroyView();
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
        if (repeat == 0)
            repeatButton.setIconTintResource(R.color.grey);
        else
            repeatButton.setIconTintResource(R.color.purple_200);
    }
}
