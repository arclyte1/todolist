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

public class OnClickTaskDialog extends DialogFragment{

    private Button editButton;
    private Button deleteButton;
    private OnTaskListener onTaskListener;
    private Task task;

    public OnClickTaskDialog(OnTaskListener onTaskListener, Task task) {
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
        View v = inflater.inflate(R.layout.on_click_task_dialog, container, false);
        editButton = (Button)v.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnEditTaskDialog onEditTaskDialog = new OnEditTaskDialog(onTaskListener, task);
                onEditTaskDialog.show(getFragmentManager(), "Edit task dialog");
                onDestroyView();
            }
        });
        deleteButton = (Button)v.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTaskListener.onDeleteTask(task);
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
