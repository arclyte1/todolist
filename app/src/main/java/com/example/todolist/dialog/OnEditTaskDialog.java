package com.example.todolist.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.todolist.OnTaskListener;
import com.example.todolist.R;
import com.example.todolist.task.Task;

public class OnEditTaskDialog extends DialogFragment {

    private TextView taskName;
    private Button editButton;
    private OnTaskListener onTaskListener;
    private Task task;

    public OnEditTaskDialog(OnTaskListener onTaskListener, Task task) {
        this.onTaskListener = onTaskListener;
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
        editButton = (Button)v.findViewById(R.id.edit_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setTask(taskName.getText().toString());
                onTaskListener.onUpdateTask(task);
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
